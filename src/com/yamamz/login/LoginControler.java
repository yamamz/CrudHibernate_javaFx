package com.yamamz.login;

import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXPasswordField;
import com.yamamz.ProductDAO;
import com.yamamz.User;
import com.yamamz.main.Controller;
import com.yamamz.util.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by admin on 2/18/2018.
 */
public class LoginControler implements Initializable {
    @FXML JFXComboBox<User> combo_user;
    @FXML JFXPasswordField password;
    ObservableList<User> usernames;
    private User theUser;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       ArrayList<User> userArrayList=UserDAO.getUserNames();
        usernames = FXCollections.observableArrayList(userArrayList);
        combo_user.getItems().addAll(usernames);


    }

    @FXML
    public void Login(ActionEvent event){
        theUser = combo_user.getValue();

        System.out.println(theUser);
        int userId = theUser.getId();
       boolean admin = theUser.isAdmin();

        String plainTextPassword = password.getText();
        theUser.setPassword(plainTextPassword);

        // check the user's password against the encrypted version in the database
        try {

            boolean isValidPassword = UserDAO.authenticate(theUser);

            if(isValidPassword){
                System.out.println("Success");
                ((Stage) combo_user.getScene().getWindow()).close();
               showMainDialog(theUser);
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }


    public Stage showMainDialog(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/com/yamamz/main/sample.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

       Controller controller =
                loader.<Controller>getController();
        controller.initData(user);
       stage.setResizable(false);
        stage.show();

        return stage;
    }


    @FXML
    public void addUser(ActionEvent actionEvent) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user=new User();
            user.setAdmin(true);
            user.setEmail("meleciort1@gmail.com");
            user.setFirstName("Cristian Ray");
            user.setLastName("Melecio");
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            String encryptedPassword = passwordEncryptor.encryptPassword("jumamz080913");
            user.setPassword(encryptedPassword);
            user.setUsername("jumamz");

            session.save(user);
            session.getTransaction().commit();
        }

    }
}
