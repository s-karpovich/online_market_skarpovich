package by.tut.mdcatalog.project2.repository.model;

public class Contact {

    private Long id;
    private String Address;
    private String Phone;
    private User user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) { isDeleted = deleted; }
}
