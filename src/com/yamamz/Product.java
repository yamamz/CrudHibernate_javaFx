package com.yamamz;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by admin on 2/16/2018.
 */
public class Product {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty date;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty productDesc;
    private final SimpleDoubleProperty price;
    private  final  SimpleStringProperty unit;
    private final SimpleDoubleProperty remaining_bal;

    public Product(Integer id, String date, String productName, String productDesc, Double price, Double remaining_bal,String unit) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.productName = new SimpleStringProperty(productName);
        this.productDesc = new SimpleStringProperty(productDesc);
        this.price = new SimpleDoubleProperty(price);
        this.remaining_bal = new SimpleDoubleProperty(remaining_bal);
        this.unit= new SimpleStringProperty(unit);
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public Integer getId() {
        return id.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public String getProductDesc() {
        return productDesc.get();
    }

    public Double getPrice() {
        return price.get();
    }
    public Double getRemaining_bal() {
        return remaining_bal.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setProductDesc(String productDesc) {
        this.productDesc.set(productDesc);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setRemaining_bal(double remaining_bal) {
        this.remaining_bal.set(remaining_bal);
    }
}
