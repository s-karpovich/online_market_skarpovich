package by.tut.mdcatalog.project2.service.model;

public class ContactDTO {

    private Long id;
    private String Address;
    private String Phone;
    private UserDTO userDTO;
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getPhone() { return Phone; }

    public void setPhone(String phone) { Phone = phone; }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) { this.userDTO = userDTO; }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }
}
