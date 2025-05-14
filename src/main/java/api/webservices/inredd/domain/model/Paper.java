package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.time.OffsetDateTime;
import org.hibernate.annotations.Type;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

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
    
    private String authors;

    private String doi;

    private String tags;

    @ManyToMany
    @JoinTable(
        name = "paper_has_user",
        joinColumns = @JoinColumn(name = "id_paper"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
