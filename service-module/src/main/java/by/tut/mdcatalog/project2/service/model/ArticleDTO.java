package by.tut.mdcatalog.project2.service.model;

public class ArticleDTO {

    private Long id;
    private String date;
    private UserDTO userDTO;
    private String name;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public UserDTO getUserDTO() { return userDTO; }

    public void setUserDTO(UserDTO userDTO) { this.userDTO = userDTO; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
