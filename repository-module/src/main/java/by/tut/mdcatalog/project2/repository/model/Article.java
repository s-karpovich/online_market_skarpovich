package by.tut.mdcatalog.project2.repository.model;

import java.util.Date;

public class Article {

    private Long id;
    private Date date;
    private User user;
    private Article article;
    private String name;
    private String message;
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() { return article; }

    public void setArticle(Article article) { this.article = article; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }
}
