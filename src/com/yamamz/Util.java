package com.yamamz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by admin on 2/16/2018.
 */
public class Util {

    public static ObservableList<Product> getProducts(){

        ObservableList<Product> productObservableList=FXCollections.observableArrayList();
        ArrayList<ProductDAO> productList;
        ArrayList<Product> productArrayList=new ArrayList<>();
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();
        Session session =factory.getCurrentSession();



        try{

            session.beginTransaction();


            Query query = session.createQuery("FROM ProductDAO");


            productList = (ArrayList<ProductDAO>) query.getResultList();


            session.getTransaction().commit();

            for (ProductDAO aProductList : productList) {
                Product product = new Product(aProductList.getId()
                        , aProductList.getDate(),
                        aProductList.getProductName(), aProductList.getProductDesc(),
                        aProductList.getPrice()
                       , aProductList.getRemaining_bal(),aProductList.getUnit());
                productArrayList.add(product);

            }

            productObservableList=FXCollections.observableArrayList(productArrayList);

            //JOptionPane.showMessageDialog(null, "Date persist successfully");

        }
        catch(HeadlessException ignored){

        }

        finally{
            factory.close();
        }



        return productObservableList;


    }


    public static ObservableList<Product> getProductsLikeName(String name){

        ObservableList<Product> productObservableList=FXCollections.observableArrayList();
        ArrayList<ProductDAO> productList;
        ArrayList<Product> productArrayList=new ArrayList<>();
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();
        Session session =factory.getCurrentSession();



        try{

            session.beginTransaction();


            Query query = session.createQuery("FROM ProductDAO where productName like ?1");
            query.setParameter(1,"%"+name+"%");


            productList = (ArrayList<ProductDAO>) query.getResultList();


            session.getTransaction().commit();

            for (ProductDAO aProductList : productList) {
                Product product = new Product(aProductList.getId()
                        , aProductList.getDate(),
                        aProductList.getProductName(), aProductList.getProductDesc(),
                        aProductList.getPrice()
                        , aProductList.getRemaining_bal(),aProductList.getUnit());
                productArrayList.add(product);

            }

            productObservableList=FXCollections.observableArrayList(productArrayList);

            //JOptionPane.showMessageDialog(null, "Date persist successfully");

        }
        catch(HeadlessException ignored){

        }

        finally{
            factory.close();
        }



        return productObservableList;


    }


}