package api.webservices.inredd.resource;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.domain.model.dto.PaperDTO;
import api.webservices.inredd.repository.PaperRepository;
import api.webservices.inredd.service.PaperService;

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
      description = "GET /papers?page=0&size=10&sort=title,asc"
    )
    @GetMapping
    public Page<PaperDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

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
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String fromYear,
            @RequestParam(required = false) String toYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id_paper") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return paperRepository.searchPapers(
                author,
                title,
                type,
                fromYear,
                toYear,
                pageable
        ).map(PaperDTO::new);
    }

    @Operation(
        summary = "Listar autores únicos de papers por intervalo de anos",
        description = "GET /papers/authors?fromYear=2018&toYear=2024"
    )
    @GetMapping("/authors")
    public List<String> listAuthors(
            @RequestParam(required = false) String fromYear,
            @RequestParam(required = false) String toYear) {

        if (fromYear != null || toYear != null) {
            return paperRepository.findUniqueAuthorsByYearRange(fromYear, toYear);
        } else {
            return paperRepository.findAllUniqueAuthors();
        }
    }

    @Operation(
        summary = "Criar um novo paper",
        description = "Cria um novo artigo científico com os dados fornecidos no corpo da requisição. É necessário ter permissão de escrita e escopo OAuth adequado. O artigo criado é retornado no corpo da resposta."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_UPLOAD_PAPER') and #oauth2.hasScope('write')")
    public PaperDTO create(@Valid @RequestBody Paper paper) {
        Paper savedPaper = paperService.save(paper);
        return new PaperDTO(savedPaper);
    }

    @Operation(
        summary = "Buscar paper por ID",
        description = "Retorna os detalhes de um artigo científico específico a partir de seu identificador único (ID). Caso não encontrado, retorna HTTP 404."
    )
    @GetMapping("/{id}")
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
    @PreAuthorize("hasAuthority('ROLE_MODERATE_PAPER') and #oauth2.hasScope('write')")
    public ResponseEntity<PaperDTO> update(@PathVariable Long id,
                                           @Valid @RequestBody Paper paper) {
        Paper updated = paperService.update(id, paper);
        return ResponseEntity.ok(new PaperDTO(updated));
    }

    @Operation(
        summary = "Remover um paper",
        description = "Deleta um artigo científico do sistema com base no ID fornecido. Requer permissão de remoção e escopo OAuth válido. Retorna HTTP 204 em caso de sucesso."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_MODERATE_PAPER') and #oauth2.hasScope('write')")
    public void delete(@PathVariable Long id) {
        paperService.delete(id);
    }
}