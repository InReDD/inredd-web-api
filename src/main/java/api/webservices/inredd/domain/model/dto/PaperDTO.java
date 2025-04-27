package api.webservices.inredd.domain.model.dto;

import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class PaperDTO {

    private Long id;
    private String urlDoi;
    private String publishDate;
    private String title;
    private String authors;
    private String doi;
    private List<String> tags;
    private List<String> users;

    public PaperDTO(Paper paper) {
        this.id = paper.getId();
        this.urlDoi = paper.getUrlDoi();
        this.publishDate = paper.getPublishDate();
        this.title = paper.getTitle();
        this.authors = paper.getAuthors();
        this.doi = paper.getDoi();
        this.tags = paper.getTags();
        this.users = paper.getUsers()
                      .stream()
                      .map(user -> user.getFirstName() + " " + user.getLastName())
                      .collect(Collectors.toList());
    }

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

    public List<String> getUsers() {
        return users;
    }
    
    public void setUsers(List<String> users) {
        this.users = users;
    }
}
