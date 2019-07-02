package by.tut.mdcatalog.project2.service.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private Long id;
    private Date date;
    private UserDTO userDTO;
    private ItemDTO itemDTO;
    private StatusDTO statusDTO;
    private String uniqueNumber;
    private int count;
    private BigDecimal total;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusDTO getStatusDTO() { return statusDTO; }

    public void setStatusDTO(StatusDTO statusDTO) { this.statusDTO = statusDTO; }

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
