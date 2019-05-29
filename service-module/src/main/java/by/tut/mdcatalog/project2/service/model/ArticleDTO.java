package by.tut.mdcatalog.project2.service.model;

import java.util.Date;

public class ArticleDTO {

    private Long id;
    private Date date;
    private UserDTO userDTO;
    private String name;
    private String message;

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

    public UserDTO getUserDTO() { return userDTO; }

    public void setUserDTO(UserDTO userDTO) { this.userDTO = userDTO; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
