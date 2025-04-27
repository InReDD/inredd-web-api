package api.webservices.inredd.domain.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.Type;

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

    @ElementCollection
    @CollectionTable(name = "paper_tags", joinColumns = @JoinColumn(name = "id_paper"))
    @Column(name = "tag")
    private List<String> tags;

    @ManyToMany
    @JoinTable(
        name = "paper_has_user",
        joinColumns = @JoinColumn(name = "id_paper"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users;

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
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
}
