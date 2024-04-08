/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantshop;

import java.sql.Date;

/**
 *
 * @author aphinat
 */
public class productData {

    private Integer id;
    private String productId;
    private String productName;
    private Integer stock;
    private String color;
    private Double price;
    private String status;
    private String image;
    private Date date;
    private Integer quantity;

    public productData(Integer id, String productId,
            String productName, Integer stock, String color,
            Double price, String status, String image, Date date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.color = color;
        this.price = price;
        this.status = status;
        this.image = image;
        this.date = date;
    }
    
    public productData(Integer id, String productId, String productName, 
            String color, Integer quantity, Double price, String image, Date date){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.color = color;
        this.price = price;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
    }
    
    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getColor() {
        return color;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity(){
        return quantity;
    }
    
}
