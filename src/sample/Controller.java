package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.yamamz.Product;
import com.yamamz.ProductDAO;
import com.yamamz.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML JFXTextField txt_ser;
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

        clear_allValue();
    }

    private void getTableSelected(){
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if(tableView.getSelectionModel().getSelectedItem() != null)
            {
                Product product=tableView.getSelectionModel().getSelectedItem();
                txt_id.setText(product.getId().toString());
                txt_name.setText(product.getProductName());
                txt_desc.setText(product.getProductDesc());
                txt_balance.setText(product.getRemaining_bal().toString());
                txt_price.setText(product.getPrice().toString());
                combo_unit.setValue(product.getUnit());


                //System.out.println("Selected Value" + selectedCells.getClass().ge);
            }
        });
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
       if(btn_save.getText()=="Save"){
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



        try{

            session.beginTransaction();
            Query query = session.createQuery("Delete ProductDAO where id = :id");
            query.setParameter("id",id);
            int result = query.executeUpdate();

            if (result > 0) {
                System.out.println("product was removed");
                Product product=tableView.getSelectionModel().getSelectedItem();
             tableView.getItems().remove(product);
                clear_allValue();
            }


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

            session.beginTransaction();
            session.update(productDAO);
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
            session.beginTransaction();
            session.save(productDAO);
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
            System.out.println(e.getMessage());

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