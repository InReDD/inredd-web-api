package api.webservices.inredd.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.domain.model.dto.PaperDTO;
import api.webservices.inredd.repository.PaperRepository;
import api.webservices.inredd.service.PaperService;
import api.webservices.inredd.specification.PaperSpecification;
import org.springframework.data.jpa.domain.Specification;

@SecurityRequirement(name = "oauth2_scheme")
@RestController
@RequestMapping("/papers")
@Tag(name = "Papers", description = "Operações com papers científicos")
public class PaperResource {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private PaperService paperService;

    @Operation(
      summary = "Listar papers com limite, página e ordenação",
      description = "GET /papers?limit=4&page=1&sort=createdAt,DESC (ou ASC)"
    )
    @GetMapping
    public Page<PaperDTO> list(Pageable pageable) {
        return paperRepository.findAll(pageable)
                            .map(PaperDTO::new);
    }

    @Operation(
        summary = "Buscar papers por filtros",
        description = "Permite buscar artigos científicos com base em múltiplos filtros opcionais, como autor, publicador, tipo do artigo e intervalo de anos de publicação (fromYear/toYear). A busca é case-insensitive e pode ser combinada com paginação."
    )
    @GetMapping("/search")
    public Page<PaperDTO> searchPapers(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String fromYear,
            @RequestParam(required = false) String toYear,
            @RequestParam(required = false) String title,
            Pageable pageable) {

        Specification<Paper> spec = Specification
            .where(PaperSpecification.hasAuthor(author))
            .and(PaperSpecification.hasPublisher(publisher))
            .and(PaperSpecification.hasType(type))
            .and(PaperSpecification.publishedBetween(fromYear, toYear))
            .and(PaperSpecification.hasTitle(title)); 

        return paperRepository.findAll(spec, pageable).map(PaperDTO::new);
    }

    @Operation(
        summary = "Listar autores únicos de papers por intervalo de anos",
        description = "GET /papers/authors?fromYear=2018&toYear=2024"
    )
    @GetMapping("/authors")
    public List<String> listAuthors(
        @RequestParam("fromYear") String fromYear,
        @RequestParam("toYear")   String toYear
    ) {
        // monta spec usando o filtro de intervalo de anos
        Specification<Paper> spec = PaperSpecification.publishedBetween(fromYear, toYear);

        // busca todos os papers que satisfazem a spec
        List<Paper> papers = paperRepository.findAll(spec);

        // converte em DTO, extrai autores, desdobra, remove duplicados e coleta
        return papers.stream()
                     .map(PaperDTO::new)
                     .flatMap(dto -> dto.getAuthors().stream())
                     .distinct()
                     .collect(Collectors.toList());
    }

    @Operation(
        summary = "Criar um novo paper",
        description = "Cria um novo artigo científico com os dados fornecidos no corpo da requisição. É necessário ter permissão de escrita e escopo OAuth adequado. O artigo criado é retornado no corpo da resposta."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public Paper create(@Valid @RequestBody Paper paper,
                        HttpServletResponse response) {
        return paperService.save(paper);
    }

    @Operation(
        summary = "Buscar paper por ID",
        description = "Retorna os detalhes de um artigo científico específico a partir de seu identificador único (ID). Caso não encontrado, retorna HTTP 404."
    )
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<PaperDTO> findById(@PathVariable Long id) {
        return paperRepository.findById(id)
            .map(paper -> ResponseEntity.ok(new PaperDTO(paper)))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Atualizar um paper existente",
        description = "Atualiza os dados de um artigo científico com base no ID fornecido e no conteúdo enviado no corpo da requisição. É necessário ter permissão de escrita e escopo OAuth apropriado."
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_ACTIVITY') and #oauth2.hasScope('write')")
    public ResponseEntity<Paper> update(@PathVariable Long id,
                                        @Valid @RequestBody Paper paper) {
        Paper updated = paperService.update(id, paper);
        return ResponseEntity.ok(updated);
    }

    @Operation(
        summary = "Remover um paper",
        description = "Deleta um artigo científico do sistema com base no ID fornecido. Requer permissão de remoção e escopo OAuth válido. Retorna HTTP 204 em caso de sucesso."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVE_ACTIVITY') and #oauth2.hasScope('write')")
    public void delete(@PathVariable Long id) {
        paperRepository.deleteById(id);
    }
}
