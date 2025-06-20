package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.TermsOfService;
import api.webservices.inredd.domain.model.PrivacyPolicy;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.TermsDTO;
import api.webservices.inredd.domain.model.dto.PrivacyPolicyDTO;
import api.webservices.inredd.repository.TermsOfServiceRepository;
import api.webservices.inredd.repository.PrivacyPolicyRepository;
import api.webservices.inredd.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.time.Instant;

@Service
public class ParamService {

    private final TermsOfServiceRepository tosRepo;
    private final PrivacyPolicyRepository ppRepo;
    private final UserRepository userRepo;
    @Autowired private TokenStore tokenStore;

    public ParamService(TermsOfServiceRepository tosRepo,
                        PrivacyPolicyRepository ppRepo,
                        UserRepository userRepo) {
        this.tosRepo  = tosRepo;
        this.ppRepo   = ppRepo;
        this.userRepo = userRepo;
    }

    public String getLatestTermsContent() {
        return tosRepo.findTopByOrderByCreatedAtDesc()
            .map(TermsOfService::getContent)
            .orElseThrow(() -> new EntityNotFoundException("Nenhum conteúdo para Termos de Serviço foi encontrado."));
    }
    
    public String getLatestPrivacyPolicyContent() {
        return ppRepo.findTopByOrderByCreatedAtDesc()
            .map(PrivacyPolicy::getContent)
            .orElseThrow(() -> new EntityNotFoundException("Nenhum conteúdo para Política de Privacidade foi encontrado."));
    }

    @Transactional
    public void acceptTerms(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        user.setUserHasAcceptedTerms(true);
        user.setAcceptedTermsAt(Instant.now());
        userRepo.save(user);
    }

    @Transactional
    public void acceptPrivacyPolicy(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        user.setUserHasAcceptedPrivacyPolicy(true);
        user.setAcceptedPrivacyPolicyAt(Instant.now());
        userRepo.save(user);
    }

    /**
     * Extrai o user_id que foi adicionado no token pelo seu CustomTokenEnhancer.
     */
    public Long extractUserId(Authentication authentication) {
        if (!(authentication instanceof OAuth2Authentication)) {
            throw new AccessDeniedException("Token inválido");
        }
        OAuth2AuthenticationDetails details =
            (OAuth2AuthenticationDetails) authentication.getDetails();

        OAuth2AccessToken accessToken =
            tokenStore.readAccessToken(details.getTokenValue());

        Number userIdNum = (Number) accessToken
            .getAdditionalInformation()
            .get("user_id");

        if (userIdNum == null) {
            throw new AccessDeniedException("Usuário não localizado no token");
        }
        return userIdNum.longValue();
    }

    @Transactional
    public void refuseTerms(Long userId) {
        User user = userRepo.findByIdUser(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));
        user.setUserHasAcceptedTerms(false);
        user.setAcceptedTermsAt(null);
        userRepo.save(user);
    }

    @Transactional
    public void refusePrivacyPolicy(Long userId) {
        User user = userRepo.findByIdUser(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + userId));
        user.setUserHasAcceptedPrivacyPolicy(false);
        user.setAcceptedPrivacyPolicyAt(null);
        userRepo.save(user);
    }

    @Transactional
    public void updateTermsText(String content) {
        Optional<TermsOfService> existingTosOpt = tosRepo.findTopByOrderByCreatedAtDesc();
        TermsOfService tosToSave;

        if (existingTosOpt.isPresent()) {
            tosToSave = existingTosOpt.get();
            tosToSave.setContent(content);
            tosToSave.setUpdatedAt(Instant.now());
        } else {
            tosToSave = new TermsOfService();
            tosToSave.setContent(content);
            tosToSave.setCreatedAt(Instant.now());
        }
        
        tosRepo.save(tosToSave);
    }

    @Transactional
    public void updatePrivacyText(String content) {
        Optional<PrivacyPolicy> existingPpOpt = ppRepo.findTopByOrderByCreatedAtDesc();
        PrivacyPolicy ppToSave;
    
        if (existingPpOpt.isPresent()) {
            ppToSave = existingPpOpt.get();
            ppToSave.setContent(content);
            ppToSave.setUpdatedAt(Instant.now());
    
        } else {
            ppToSave = new PrivacyPolicy();
            ppToSave.setContent(content);
            ppToSave.setCreatedAt(Instant.now()); 
        }
    
        ppRepo.save(ppToSave);
    }
}