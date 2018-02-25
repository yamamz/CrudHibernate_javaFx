package com.yamamz;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by admin on 2/19/2018.
 */

@Entity
@Table(name="product_audit_history")
public class Audit {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="id") private int id;
    @Column(name="user_id") private int userId;

    @Column(name="action") private String action;
    @Column(name="action_date") private Date actionDate;

    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private ProductDAO product;

public Audit(){

}

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
        this.product = product;
    }

    public Audit(int userId, ProductDAO product, String action, Date actionDate) {
        this.userId = userId;
  this.product=product;
        this.action = action;
        this.actionDate = actionDate;
    }

    public Audit(int id, int userId, ProductDAO product, String action, Date actionDate) {
        this.id = id;
        this.userId = userId;
        this.product=product;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return actionDate;
    }

    public void setTimestamp(Date actionDate) {
        this.actionDate = actionDate;
    }
}
