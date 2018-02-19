package com.yamamz;

/**
 * Created by admin on 2/15/2018.
 */
import javax.persistence.*;

import java.util.Set;

/**
 *
 * @author admin
 */
@Entity
@Table(name="product")
public class ProductDAO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;

    @Column(name="date_created")
    private String date;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_description")
    private String productDesc;

    @Column(name="price")
    private double price;

    @Column(name="unit")
    private String unit;

    @Column(name="remaining_balance")
    private Double remaining_bal;
    @OneToMany(mappedBy = "product")
    private Set<Audit> audits;

    public ProductDAO(){
    }

    public ProductDAO(String date, String productName, String productDesc, double price, String unit, Double remaining_bal) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.price = price;
        this.unit = unit;
        this.remaining_bal = remaining_bal;
        this.date=date;
    }

    public ProductDAO(String date, String productName, String productDesc, double price, String unit, Double remaining_bal,int id) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.price = price;
        this.unit = unit;
        this.remaining_bal = remaining_bal;
        this.date=date;
        this.id=id;
    }

    public Set<Audit> getAudits() {
        return audits;
    }

    public void setAudits(Set<Audit> audits) {
        this.audits = audits;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getRemaining_bal() {
        return remaining_bal;
    }

    public void setRemaining_bal(Double remaining_bal) {
        this.remaining_bal = remaining_bal;
    }











}
