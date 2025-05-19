package api.webservices.inredd.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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
}
