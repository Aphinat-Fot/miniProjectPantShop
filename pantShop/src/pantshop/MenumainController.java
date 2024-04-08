/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantshop;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author aphinat
 */
public class MenumainController implements Initializable {

    @FXML
    private GridPane menu_gridPane1;

    @FXML
    private TableColumn<productData, String> menu_col_price1;

    @FXML
    private TableColumn<productData, String> menu_col_productName1;

    @FXML
    private TableColumn<productData, String> menu_col_quantity1;

    @FXML
    private TableView<productData> menu_tableView1;

    @FXML
    private Label menu_total1;

    @FXML
    private Button menu_next;

    @FXML
    private AnchorPane menu_toStep2;

    @FXML
    private ScrollPane menu_toStep1;

    @FXML
    private Button menu_return;

     @FXML
    private Label menu_payment;

    @FXML
    private Button menu_remove;

    @FXML
    private TextArea input_adress;

    @FXML
    private TextField input_fullname;

    @FXML
    private TextField input_numberphone;

    @FXML
    private TextField input_zipcode;

    @FXML
    private Button menu_system;

    @FXML
    private AnchorPane menu_nomal;

    @FXML
    private Button receipt_Btn;

    @FXML
    private Button Confirm_Btn;

      @FXML
    private Button refresh_Btn;
    
    @FXML
    private AnchorPane menu_thank;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private int getid;

    @FXML
    void refresh(ActionEvent event) {
        System.out.print("test");
        menuDisplayCard1();
        menuDisplayTotal();
        menuShowOrderData();
    }

    @FXML
    public void switchPage(ActionEvent event) {

        if (event.getSource() == menu_next) {
            menu_toStep1.setVisible(false);
            menu_thank.setVisible(false);
            menu_toStep2.setVisible(true);
            menu_next.setVisible(false);
            menu_return.setVisible(true);
            menu_remove.setVisible(false);
            menu_system.setVisible(false);
            refresh_Btn.setVisible(false);

            
            menuDisplayCard1();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == menu_return) {
            menu_toStep1.setVisible(true);
            menu_thank.setVisible(false);
            menu_toStep2.setVisible(false);
            menu_next.setVisible(true);
            menu_return.setVisible(false);
            menu_remove.setVisible(true);
            menu_system.setVisible(true);
            refresh_Btn.setVisible(true);
            menuDisplayCard1();
            menuDisplayTotal();
            menuShowOrderData();
        }

    }

    public void ConfirmBtn() {

        if (input_fullname.getText().isEmpty()
                || input_numberphone.getText().isEmpty()
                || input_adress.getText().isEmpty()
                || input_zipcode.getText().isEmpty()
               ) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            String insertPay = "INSERT INTO receipt (customer_id, total, date, guest, numberphone, adress, zipcode, payment ) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            connect = database.connectDB();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    customerID1();
                    menuGetTotal1();
                    prepare = connect.prepareStatement(insertPay);
                    prepare.setString(1, String.valueOf(cID));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.setString(4, input_fullname.getText());
                    prepare.setString(5, input_numberphone.getText());
                    prepare.setString(6, input_adress.getText());
                    prepare.setString(7, input_zipcode.getText());
                    prepare.setString(8, menu_payment.getText().substring(2));
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Infomation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful.");
                    alert.showAndWait();
                    Confirm_Btn.setVisible(true);
                    menu_next.setVisible(false);
                    menu_thank.setVisible(true);
                    menu_return.setVisible(false);
                    menuShowOrderData();

//                        menuRestart();
                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Infomation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuRestart() {
        totalP = 0;
        menu_total1.setText("$0.0");
        input_fullname.setText("");
        input_numberphone.setText("");
        input_adress.setText("");
        input_zipcode.setText("");
       

    }

    

    public ObservableList<productData> menuGetOrder() {
        customerID1();
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("color"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> menuOrderListData;

    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_productName1.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price1.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView1.setItems(menuOrderListData);
    }

    public void menuSelectOrder1() {
        productData prod = menu_tableView1.getSelectionModel().getSelectedItem();
        int num = menu_tableView1.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();
        System.out.print(getid);
    }
    private double totalP;

    private int cID;

    public void customerID1() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<productData> cardListData1 = FXCollections.observableArrayList();

    public ObservableList<productData> menuGetData1() {

        String sql = "SELECT * FROM product";

        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("color"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard1() {

        cardListData1.clear();
        cardListData1.addAll(menuGetData1());

        int row = 0;
        int column = 0;

        menu_gridPane1.getChildren().clear();
        menu_gridPane1.getRowConstraints().clear();
        menu_gridPane1.getColumnConstraints().clear();

        for (int q = 0; q < cardListData1.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData1.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane1.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void menuDisplayTotal() {
        menuGetTotal1();
        menu_total1.setText("$" + totalP);
    }

    public void menuGetTotal1() {
        customerID1();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuRemoveBtn1() {
        System.out.print(getid);
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDB();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuReceiptBtn() {

        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            System.out.println(cID - 1);
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));

            try {

                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\aphinat\\Desktop\\miniProjectOOSE\\miniProjectOOSE\\pantShop\\src\\pantshop\\report1.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);

                JasperViewer.viewReport(jPrint, false);

                menuRestart();
                Confirm_Btn.setVisible(true);
                menu_thank.setVisible(false);
                receipt_Btn.setVisible(false);
                menu_toStep1.setVisible(true);
                menu_toStep2.setVisible(false);
                menu_next.setVisible(true);
                menu_return.setVisible(false);
                menu_remove.setVisible(true);
                menu_system.setVisible(true);
                refresh_Btn.setVisible(true);
                menuDisplayCard1();
                menuDisplayTotal();
                menuShowOrderData();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void menusystemeBtn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("ระบบการจัดการ กางเกง ช็อป");
//                    stage.setMinWidth(1150);
//                    stage.setMinHeight(600);

        stage.setScene(scene);
        stage.show();

        menu_nomal.getScene().getWindow().hide();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        menuDisplayCard1();
        menuDisplayTotal();
        // TODO
    }

}
