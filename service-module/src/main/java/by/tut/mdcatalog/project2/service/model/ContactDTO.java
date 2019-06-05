package by.tut.mdcatalog.project2.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactDTO {

    private Long id;
    @NotNull
    @Size(min = 1, max = 80)
    private String Address;
    @NotNull
    @Size(min = 1, max = 20)
    private String Phone;
    private UserDTO userDTO;

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

}
