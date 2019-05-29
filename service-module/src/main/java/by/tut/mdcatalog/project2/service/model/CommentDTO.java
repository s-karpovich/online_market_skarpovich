package by.tut.mdcatalog.project2.service.model;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private Date date;
    private UserDTO userDTO;
    private ArticleDTO articleDTO;
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ArticleDTO getArticleDTO() { return articleDTO; }

    public void setArticleDTO(ArticleDTO articleDTO) { this.articleDTO = articleDTO; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

}
