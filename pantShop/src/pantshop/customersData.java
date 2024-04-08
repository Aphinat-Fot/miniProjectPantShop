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
public class customersData {

    private Integer id;
    private Integer customerID;
    private Double total;
    private Date date;
    private String guest;
    private String numberphone;
    private String adress;
    private String zipcode;
    private String payment;

    public customersData(Integer id, Integer customerID, Double total,
            Date date, String guest, String numberphome, String adress, String zipcode, String payment) {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.date = date;
        this.guest = guest;
        this.numberphone = numberphome;
        this.adress =adress;
        this.zipcode = zipcode;
        this.payment = payment;
        

    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public String getGuest() {
        return guest;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public String getAdress() {
        return adress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPayment() {
        return payment;
    }

}
