package by.tut.mdcatalog.project2.repository.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String middlename;
    private String surname;
    @Column(name = "deleted")
    private boolean isDeleted = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User() {
    }
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getMiddlename() { return middlename; }

    public void setMiddlename(String middlename) { this.middlename = middlename; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public Boolean getDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(middlename, user.middlename) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(isDeleted, user.isDeleted) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, middlename, surname, isDeleted, role);
    }
}
