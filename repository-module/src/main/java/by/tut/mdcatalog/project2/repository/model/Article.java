package by.tut.mdcatalog.project2.repository.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table
@SQLDelete(sql = "UPDATE article SET deleted=true WHERE id=?")
@Where(clause = "deleted = 0")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String message;
    @Column(name = "deleted")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(date, article.date) &&
                Objects.equals(user, article.user) &&
                Objects.equals(name, article.name) &&
                Objects.equals(message, article.message) &&
                Objects.equals(isDeleted, article.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, user, name, message, isDeleted);
    }
}
