package by.tut.mdcatalog.project2.service.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class OrderDTO {
    private Long id;
    private Date date;
    private UserDTO userDTO;
    private ItemDTO itemDTO;
    private String status;
    private String uniqueNumber;
    private int count;
    private BigDecimal total;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

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

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
