package com.yamamz.util;

import com.yamamz.Product;
import com.yamamz.ProductDAO;
import com.yamamz.User;
import com.yamamz.util.PasswordUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import main.MainControler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static void loadWindowLogin(URL loc, String title, Stage parentStage) {
        try {
            Parent parent = FXMLLoader.load(loc);
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);


            stage.setScene(new Scene(parent));
            stage.showAndWait();
           //
            //setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadWindowMain(URL loc, String title, Stage parentStage) {
        try {
            Parent parent = FXMLLoader.load(loc);
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);


            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.show();
            //
            //setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}