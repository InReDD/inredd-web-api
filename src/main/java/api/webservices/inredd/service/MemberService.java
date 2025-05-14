package api.webservices.inredd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import api.webservices.inredd.domain.model.dto.MemberDTO;
import api.webservices.inredd.repository.MemberRepository;
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> listMembersByGroup(Long groupId) {
        return memberRepository.findMembersByGroupId(groupId);
    }

    /**
     * Retorna todos os MemberDTO para os grupos informados,
     * incluindo dados de Academic.
     */
    public List<MemberDTO> findMembersByGroupIds(List<Long> groupIds) {
        return memberRepository.findMembersByGroupIds(groupIds);
    }

    /**
     * Mesma busca, mas já retorna página conforme Pageable.
     */
    public Page<MemberDTO> listByGroupIds(List<Long> groupIds, Pageable pageable) {
        List<MemberDTO> all = findMembersByGroupIds(groupIds);

        // Remove duplicados
        List<MemberDTO> unique = all.stream()
            .distinct()
            .collect(Collectors.toList());

        // Paginação manual
        int start = (int) Math.min(pageable.getOffset(), unique.size());
        int end   = (int) Math.min(start + pageable.getPageSize(), unique.size());
        List<MemberDTO> slice = unique.subList(start, end);

        return new PageImpl<>(slice, pageable, unique.size());
    }
}
