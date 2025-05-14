package api.webservices.inredd.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.webservices.inredd.domain.model.dto.MemberDTO;
import api.webservices.inredd.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/members")
public class MemberResource {

    @Autowired
    private MemberService memberService;

    /**
     * GET /members/list?group=1
     * @param groupId ID do grupo
     * @return lista de MemberDTO
     */
    @GetMapping("/list")
    public List<MemberDTO> list(@RequestParam("group") Long groupId) {
        return memberService.listMembersByGroup(groupId);
    }

    @Operation(
      summary = "Listar membros de múltiplos grupos",
      description = "GET /members?group=1&group=2&page=1&limit=10 — retorna membros únicos associados aos grupos informados, paginados."
    )
    @GetMapping
    public ResponseEntity<Page<MemberDTO>> listByGroups(
            @RequestParam("group") List<Long> groupIds,
            Pageable pageable) {

        Page<MemberDTO> page = memberService.listByGroupIds(groupIds, pageable);
        return ResponseEntity.ok(page);
    }
}
