package by.tut.mdcatalog.project2.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Size(min = 1, max = 20)
    private String firstname;
    @Size(max = 40)
    private String middlename;
    @NotNull
    @Size(min = 1, max = 50)
    private String surname;

    private Boolean isDeleted;
    @NotNull
    private String role;

    public UserDTO() {
    }
    public UserDTO(
            Long id,
            String username,
            String password,
            String firstname,
            String middlename,
            String surname,
            Boolean isDeleted,
            String role)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.middlename = middlename;
        this.surname = surname;
        this.isDeleted = isDeleted;
        this.role = role;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Boolean getDeleted() { return isDeleted; }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
