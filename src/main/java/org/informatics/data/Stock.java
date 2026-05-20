package org.informatics.data;

import java.math.BigDecimal;

import static org.informatics.data.StockType.FOOD;
import static org.informatics.data.StockType.NONFOOD;


public class Stock {
    private final long ID;
    private String name;
    private BigDecimal deliveryPrice;
    private StockType category;
    private String bestBefore;
    private long quantity;
    private BigDecimal price;

    public Stock(long ID, String name, BigDecimal deliveryPrice, StockType category, String bestBefore, long quantity, BigDecimal price) {
        this.ID = ID;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.category = category;
        this.bestBefore = bestBefore;
        this.quantity = quantity;
        this.price = price;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public StockType getCategory() {
        return category;
    }

    public void setCategory(StockType category) {
        this.category = category;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        if(getCategory(FOOD)){

        }
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", category=" + category +
                ", bestBefore='" + bestBefore + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
