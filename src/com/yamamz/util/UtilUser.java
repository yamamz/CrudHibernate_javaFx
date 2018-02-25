package com.yamamz.util;

import com.yamamz.ProductDAO;
import com.yamamz.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by admin on 2/21/2018.
 */
public class UtilUser {
    private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    public static boolean authenticate(User theUser) throws Exception {
        boolean result = false;
        String plainTextPassword = theUser.getPassword();
        //get the encrypted password from database for this user
        String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());
        //compare the passwords
        result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);
        return result;
    }
    public static User getUserAuthenticate(String username){

       // String encryptPassword = passwordEncryptor.encryptPassword(password);
      //  System.out.println(encryptPassword);
        User user;

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try (Session session = factory.getCurrentSession()) {

            session.beginTransaction();
            Query query=session.createQuery("FROM User WHERE username=?1");
            query.setParameter(1,username);
            //query.setParameter(2,encryptPassword);

         //query.executeUpdate();
       int result =query.getResultList().size();

            System.out.println("sfsc"+result);
            user=(User)query.getResultList().get(0);

            session.getTransaction().commit();
        }
        return user;

    }

    private static String getEncrpytedPassword(int id) throws Exception {
        String encryptedPassword = null;

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
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
