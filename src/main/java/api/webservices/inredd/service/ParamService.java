package api.webservices.inredd.service;

import api.webservices.inredd.domain.model.TermsOfService;
import api.webservices.inredd.domain.model.PrivacyPolicy;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.TermsDTO;
import api.webservices.inredd.domain.model.dto.PrivacyPolicyDTO;
import api.webservices.inredd.repository.TermsOfServiceRepository;
import api.webservices.inredd.repository.PrivacyPolicyRepository;
import api.webservices.inredd.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
public class ParamService {

    private final TermsOfServiceRepository tosRepo;
    private final PrivacyPolicyRepository ppRepo;
    private final UserRepository userRepo;

    public ParamService(TermsOfServiceRepository tosRepo,
                        PrivacyPolicyRepository ppRepo,
                        UserRepository userRepo) {
        this.tosRepo  = tosRepo;
        this.ppRepo   = ppRepo;
        this.userRepo = userRepo;
    }

    public String getLatestTermsContent() {
        return tosRepo.findTopByOrderByCreatedAtDesc().getContent();
    }
    
    public String getLatestPrivacyPolicyContent() {
        return ppRepo.findTopByOrderByCreatedAtDesc().getContent();
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
}