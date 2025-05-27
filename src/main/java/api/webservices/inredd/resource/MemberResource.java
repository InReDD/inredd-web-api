package api.webservices.inredd.web;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import api.webservices.inredd.domain.model.*;
import api.webservices.inredd.domain.model.dto.*;
import api.webservices.inredd.repository.UserRepository;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.repository.InviteRequestRepository;
import api.webservices.inredd.service.MemberService;


@RestController
@RequestMapping("/members")
public class MemberResource {

    @Autowired private MemberService memberService;
    @Autowired private UserRepository userRepo;
    @Autowired private GroupRepository groupRepo;

    @GetMapping("/list")
    public ResponseEntity<Page<MemberDTO>> listByGroups(
        @RequestParam("group") String[] groupParams,
        @RequestParam(value="page",  defaultValue="0")   int page,
        @RequestParam(value="limit",defaultValue="10")  int limit
    ) {
        List<Long> groupIds = Stream.of(groupParams)
            .flatMap(p -> Arrays.stream(p.split(",")))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(Long::valueOf)
            .distinct()
            .collect(Collectors.toList());

        PageRequest pr = PageRequest.of(page, limit);
        Page<MemberDTO> result = memberService.listByGroupIds(groupIds, pr);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Listar membros (filtro por grupos e/ou nome opcional)")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_LIST_MEMBER') and #oauth2.hasScope('read')")
    public ResponseEntity<Page<MemberViewDTO>> list(
            @RequestParam(value = "group", required = false) String[] groupParams,
            @RequestParam(value = "name",  required = false) String name,
            @RequestParam(value = "page",  defaultValue = "0")   int page,
            @RequestParam(value = "limit", defaultValue = "10")  int limit
    ) {
        List<Long> groupIds = Optional.ofNullable(groupParams)
            .map(params -> Stream.of(params)
                .flatMap(p -> Arrays.stream(p.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList()))
            .orElse(Collections.emptyList());

        Pageable pageable = PageRequest.of(page, limit, Sort.by("firstName").ascending());
        Page<MemberViewDTO> result = memberService.listMembersByGroups(groupIds, name, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Detalhes de um membro")
    @GetMapping(
        value    = "/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAuthority('ROLE_LIST_MEMBER') and #oauth2.hasScope('read')")
    public ResponseEntity<MemberDetailDTO> getOne(@PathVariable("id") Long id) {
        MemberDetailDTO dto = memberService.getMemberDetail(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Atualizar acessos de um membro")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_EDIT_MEMBER') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> updateAccessAndGroup(
            @PathVariable Long id,
            @Valid @RequestBody MemberAccessUpdateDTO dto
    ) {
        memberService.updateMemberAccessAndGroup(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
      summary = "Remover um membro",
      description = "DELETE /members/{id} — apenas remove todas as associações de grupo deste usuário (não exclui o usuário)."
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE_MEMBER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("id") Long id) {
        memberService.removeMemberFromAllGroups(id);
    }

    @Operation(summary="Convidar alguém para um grupo")
    @PostMapping("/invite")
    @PreAuthorize("hasAuthority('ROLE_INVITE_MEMBER') and #oauth2.hasScope('write')")
    public ResponseEntity<Void> invite(@Valid @RequestBody InviteMemberDTO dto) {
        memberService.inviteMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
