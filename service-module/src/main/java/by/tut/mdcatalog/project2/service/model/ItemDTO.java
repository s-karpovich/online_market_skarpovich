package by.tut.mdcatalog.project2.service.model;

import java.math.BigDecimal;

public class ItemDTO {

    private Long id;
    private String name;
    private String uniqueNumber;
    private BigDecimal price;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
