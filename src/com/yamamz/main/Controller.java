package com.yamamz.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.yamamz.Audit;
import com.yamamz.Product;
import com.yamamz.ProductDAO;
import com.yamamz.User;
import com.yamamz.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.swing.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class Controller implements Initializable {



    @FXML JFXTextField txt_ser;
    @FXML Text text;
    @FXML JFXTextField txt_id;
    @FXML JFXTextField txt_name;
    @FXML JFXTextField txt_desc;
    @FXML JFXTextField txt_price;
    @FXML JFXComboBox<String> combo_unit;
    @FXML JFXTextField txt_balance;
    @FXML JFXButton btn_save;
    @FXML Label lbl_close;

    @FXML  TableView<Product> tableView;
    @FXML  TableColumn<Product, Integer> id;
    @FXML  TableColumn<Product, String> name;
    @FXML  TableColumn<Product, String> description;
    @FXML  TableColumn<Product, Double> price;
    @FXML  TableColumn<Product, Double> balance;
    @FXML  TableColumn<Product, String> date;
    private User user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("productName"));
            description.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            balance.setCellValueFactory(new PropertyValueFactory<>("remaining_bal"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            tableView.getItems().setAll(Util.getProducts());
            txt_ser.textProperty().addListener((observable, oldValue, newValue) -> {
                tableView.getItems().setAll(Util.getProductsLikeName(newValue));
            });
            getTableSelected();
            idListener();
            table_rightClick();

            tableView.setColumnResizePolicy(p -> true);


            Platform.runLater(() -> customResize(tableView));

    }

    public void initData(User user) {
        this.user=user;
        text.setText("USER Login: "+user.getFirstName()+" "+user.getLastName());
    }

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
private void table_rightClick(){

    ContextMenu cm = new ContextMenu();
    MenuItem mi1 = new MenuItem("Delete item");
    cm.getItems().add(mi1);

    cm.setOnAction(event -> {
        Product product=tableView.getSelectionModel().getSelectedItem();

        deleteData(product.getId());

    });

    tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
        if(t.getButton() == MouseButton.SECONDARY) {
            cm.show(tableView, t.getScreenX(), t.getScreenY());
        }
        else{

            cm.hide();
        }
    });
}
private void idListener(){

       txt_id.textProperty().addListener((observable,oldValue, newValue)->{

           if(newValue.isEmpty()){
               btn_save.setText("Save");
           }
           else{
               btn_save.setText("Update");
           }
       });
}
    public void btn_clear_click(ActionEvent actionEvent){
enableAllField();
        clear_allValue();
    }

    private void getTableSelected(){
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(Audit.class)
                .buildSessionFactory();
            tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                //Check whether item is selected and set value of selected item to Label
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    Product product = tableView.getSelectionModel().getSelectedItem();
                    ProductDAO productDAO=new ProductDAO(product.getDate(),
                            product.getProductName(),product.getProductDesc(),
                            product.getPrice(),product.getUnit(),product.getRemaining_bal(),product.getId());
                    boolean isCreate=isUserIsCreated(user,productDAO,factory);
                    if (isCreate) {
                        enableAllField();
                        txt_id.setText(product.getId().toString());
                        txt_name.setText(product.getProductName());
                        txt_desc.setText(product.getProductDesc());
                        txt_balance.setText(product.getRemaining_bal().toString());
                        txt_price.setText(product.getPrice().toString());
                        combo_unit.setValue(product.getUnit());
                    }
                    else{
                     disAbleAllField();
                        txt_id.setText(product.getId().toString());
                        txt_name.setText(product.getProductName());
                        txt_desc.setText(product.getProductDesc());
                        txt_balance.setText(product.getRemaining_bal().toString());
                        txt_price.setText(product.getPrice().toString());
                        combo_unit.setValue(product.getUnit());
                    }
                    //System.out.println("Selected Value" + selectedCells.getClass().ge);
                }
            });


    }

    private void disAbleAllField(){
        txt_id.setDisable(true);
        txt_name.setDisable(true);
        txt_desc.setDisable(true);
        txt_balance.setDisable(true);
        txt_price.setDisable(true);
        combo_unit.setDisable(true);
    }

    private void enableAllField(){
        txt_id.setDisable(false);
        txt_name.setDisable(false);
        txt_desc.setDisable(false);
        txt_balance.setDisable(false);
        txt_price.setDisable(false);
        combo_unit.setDisable(false);
    }


    public void search_btnClick(ActionEvent event){
   String searchKey=txt_ser.getText();
tableView.getItems().setAll(Util.getProductsLikeName(searchKey));

    }

    public void close_btn(){
        Stage stage = (Stage)lbl_close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void txt_id_click(ActionEvent event){
    txt_name.requestFocus();
    }

    public void txt_name_click(ActionEvent event){
        txt_desc.requestFocus();
    }

    public void txt_desc_click(ActionEvent event){
        txt_price.requestFocus();
    }
    public void txt_price_click(ActionEvent event){
        txt_balance.requestFocus();
    }
    public void combo_ubit_click(ActionEvent event){
        //txt_balance.requestFocus();
    }
    public void txt_bal_click(ActionEvent event){
    btn_save.requestFocus();
    }

    public void btn_save_click(ActionEvent event){
       if(btn_save.getText().equals("Save")){

           //System.out.println("test");
           saveData();
       }
       else{
        updateData();
       }

    }

    private void deleteData(int id){
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();
        Session session =factory.getCurrentSession();


        Product product = tableView.getSelectionModel().getSelectedItem();
        try{

            session.beginTransaction();
            Query q = session.createQuery("from ProductDAO where id = :id ");
            q.setParameter("id", id);
            ProductDAO productDAO1= (ProductDAO) q.getResultList().get(0);

            for (Audit sdr : productDAO1.getAudits()){
                session.delete(sdr);
            }
            session.delete(productDAO1);



            System.out.println("product was removed");
            tableView.getItems().remove(product);
            clear_allValue();


            session.getTransaction().commit();

            txt_name.requestFocus();


            //JOptionPane.showMessageDialog(null, "Date persist successfully");

        }
        catch(Exception e){
            System.out.println(e.getMessage());

        }

        finally{
            factory.close();
        }

    }

private boolean isUserIsCreated(User user,ProductDAO product,SessionFactory factory){

    Session session = factory.getCurrentSession();
    try  {
        session.beginTransaction();
        Query query = session.createQuery("FROM Audit where userId = ?1 AND action= ?2 AND product=?3");
        query.setParameter(1, user.getId());
        query.setParameter(2, "Created");
        query.setParameter(3, product);
        int result = query.getResultList().size();
        session.getTransaction().commit();


        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
    catch (Exception e){
        System.out.println("fsfg"+e.getMessage());
    }
    finally {
        session.close();
    }
    return false;
    }



    private void updateData(){
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();
        Session session =factory.getCurrentSession();

        DateFormat df=new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String outputText = df.format(new Date());
        try{
            ProductDAO productDAO =new ProductDAO(outputText,txt_name.getText(),txt_desc.getText(),Double.parseDouble(txt_price.getText()), combo_unit.getValue(),Double.parseDouble(txt_balance.getText()),Integer.valueOf(txt_id.getText()));
            Audit audit=new Audit(user.getId(),productDAO,"Updated",new Date());
            Set<Audit> itemsSet = new HashSet<>();
            itemsSet.add(audit);
            productDAO.setAudits(itemsSet);

            session.beginTransaction();
            session.update(productDAO);
            session.save(audit);
            session.getTransaction().commit();
            System.out.println("Save successfully");
            tableView.getItems().setAll(Util.getProducts());
            clear_allValue();
            txt_name.requestFocus();


            //JOptionPane.showMessageDialog(null, "Date persist successfully");

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally{
            factory.close();
        }

    }

    private void saveData(){

        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(ProductDAO.class)
                .buildSessionFactory();
        Session session =factory.getCurrentSession();

        try{
            DateFormat df=new SimpleDateFormat("MM-dd-yyyy", Locale.US);
            String outputText = df.format(new Date());
            ProductDAO productDAO =new ProductDAO(outputText,txt_name.getText(),txt_desc.getText(),Double.parseDouble(txt_price.getText()), combo_unit.getValue(),Double.parseDouble(txt_balance.getText()));
            Audit audit=new Audit(user.getId(),productDAO,"Created",new Date());
            Set<Audit> itemsSet = new HashSet<>();
            itemsSet.add(audit);

            productDAO.setAudits(itemsSet);
            session.beginTransaction();

            session.save(productDAO);
            session.save(audit);
            Product product=new Product(productDAO.getId(),productDAO.getDate(),productDAO.getProductName(),
                    productDAO.getProductDesc(),productDAO.getPrice(),productDAO.getRemaining_bal(),productDAO.getUnit());
            System.out.println("Save successfully");
            tableView.getItems().add(product);
            clear_allValue();
            txt_name.requestFocus();
            session.getTransaction().commit();



            //JOptionPane.showMessageDialog(null, "Date persist successfully");

        }
        catch(Exception e){


        }

        finally{
            factory.close();
        }


    }

    private void clear_allValue(){

        txt_name.setText("");
        txt_desc.setText("");
        txt_price.setText("");
        combo_unit.setValue("");
        txt_balance.setText("");
        txt_id.setText("");
    }



}