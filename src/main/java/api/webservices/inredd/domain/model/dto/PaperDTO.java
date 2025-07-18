package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.domain.model.PaperFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;


public class PaperDTO {

    private Long id;
    private String urlDoi;
    private String publishDate;
    private String title;
    private List<String> authors;
    private String doi;
    private List<String> tags;
    private String abstractText;
    private List<String> users;
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false, columnDefinition = "TEXT")
    private PaperFormat format;
    private String formattedText;
    private OffsetDateTime createdAt;

    public PaperDTO(Paper paper) {
        this.id          = paper.getId();
        this.urlDoi      = paper.getUrlDoi();
        this.publishDate = paper.getPublishDate();
        this.title       = paper.getTitle();
        this.authors = paper.getAuthors() != null ? Arrays.asList(paper.getAuthors()) : Collections.emptyList();
        this.doi         = paper.getDoi();
        this.tags = paper.getTags() != null ? Arrays.asList(paper.getTags()) : Collections.emptyList();
        this.abstractText = paper.getAbstractText();

        if (paper.getUsers() != null && !paper.getUsers().isEmpty()) {
            this.users = paper.getUsers()
                    .stream()
                    .map(u -> u.getFirstName() + " " + u.getLastName())
                    .collect(Collectors.toList());
        } else {
            this.users = Collections.emptyList();
        }

        this.format        = paper.getFormat();
        this.formattedText = paper.getFormattedText();

        this.createdAt   = paper.getCreatedAt();
    }

    @SuppressWarnings("unused")
    private List<String> parseAuthors(String raw) {
        if (raw == null || raw.isBlank()) {
            return Collections.emptyList();
        }
        // Remove apenas as chaves { } mas mantém as aspas
        String cleaned = raw.replaceAll("[\\{\\}]", "");
        // Regex para capturar tudo que está entre aspas
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(cleaned);
    
        List<String> authors = new ArrayList<>();
        while (matcher.find()) {
            authors.add(matcher.group(1).trim());
        }
        return authors;
    }

    @SuppressWarnings("unused")
    private List<String> parseTags(String raw) {
        if (raw == null || raw.isBlank()) {
            return Collections.emptyList();
        }
        // raw ex: {"computer vision",transformers}
        String cleaned = raw.replaceAll("[\"{}]", ""); // -> computer vision,transformers
        return Arrays.stream(cleaned.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlDoi() {
        return urlDoi;
    }

    public void setUrlDoi(String urlDoi) {
        this.urlDoi = urlDoi;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public PaperFormat getFormat() {
        return format;
    }

    public void setFormat(PaperFormat format) {
        this.format = format;
    }

    public String getFormattedText() {
        return formattedText;
    }

    public void setFormattedText(String formattedText) {
        this.formattedText = formattedText;
    }
}