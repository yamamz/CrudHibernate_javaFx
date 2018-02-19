package com.yamamz.util;

import com.yamamz.ProductDAO;
import com.yamamz.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by admin on 2/18/2018.
 */
public class UserDAO {

    public static boolean authenticate(User theUser) throws Exception {
        boolean result = false;

        String plainTextPassword = theUser.getPassword();

        // get the encrypted password from database for this user
        String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());

        // compare the passwords
        result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);

        return result;
    }

    private static String getEncrpytedPassword(int id) throws Exception {
        String encryptedPassword = null;

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user= session.get(User.class,id);


            if (user!=null) {

                encryptedPassword = user.getPassword();

            }
            session.getTransaction().commit();

            return encryptedPassword;
        }
    }

    public static ArrayList<User> getUserNames(){

        ArrayList<User> userNames;

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Query query=session.createQuery("FROM User");

            ArrayList<User> userArrayList=(ArrayList<User>) query.getResultList();
            userNames=userArrayList;
            session.getTransaction().commit();
            return userNames;
        }

    }
}
