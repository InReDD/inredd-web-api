package api.webservices.inredd.domain.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
})
@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paper")
    private Long id;

    @Column(name = "url_doi")
    private String urlDoi;

    @Column(name = "publish_date")
    private String publishDate;

    private String title;

    @Type(type = "string-array")
    @Column(name = "authors", columnDefinition = "text[]")
    private String[] authors;

    private String doi;

    @Type(type = "string-array")
    @Column(name = "tags", columnDefinition = "text[]")
    private String[] tags;

    @Column(name = "abstract")
    private String abstractText;

    @ManyToMany
    @JoinTable(
        name = "paper_has_user",
        joinColumns = @JoinColumn(name = "id_paper"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false, columnDefinition = "paper_format")
    private PaperFormat format;

    @Column(columnDefinition = "TEXT")
    private String formattedText;

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

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paper)) return false;
        Paper paper = (Paper) o;
        return Objects.equals(id, paper.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
