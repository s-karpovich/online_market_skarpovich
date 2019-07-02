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

    private RoleDTO roleDTO;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public RoleDTO getRoleDTO() { return roleDTO; }

    public void setRoleDTO(RoleDTO roleDTO) { this.roleDTO = roleDTO; }

}
