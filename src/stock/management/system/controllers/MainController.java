package stock.management.system.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import stock.management.system.dao.ProductDAO;

/**
 *
 * @author Chan Nyein Tun
 */
public class MainController implements Initializable {

    @FXML
    private Button dashboradBtn;
    @FXML
    private Button productsBtn;

    @FXML
    private HBox dashboardView;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button inoutBtn;
    @FXML
    private Button lowStockBtn;
    @FXML
    private Button transactionBtn;
    @FXML
    private Label productLabel;
    @FXML
    private Label lowStockLabel;

    private ProductDAO productDAO;
    @FXML
    private Button settingsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboradBtn.setStyle("-fx-background-color:#00acc1");
        productDAO = new ProductDAO();
        try {
            loadDashboardData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showDashboardView(ActionEvent event) throws ClassNotFoundException {

        dashboradBtn.setStyle("-fx-background-color:#00acc1");
        inoutBtn.setStyle("-fx-background-color:transparent");
        productsBtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        settingsBtn.setStyle("-fx-background-color:transparent");

        borderPane.setCenter(dashboardView);
        loadDashboardData();
    }

    @FXML
    private void showProductsView(ActionEvent event) throws IOException {
        dashboradBtn.setStyle("-fx-background-color:transparent");
        inoutBtn.setStyle("-fx-background-color:transparent");
        productsBtn.setStyle("-fx-background-color:#00acc1");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        settingsBtn.setStyle("-fx-background-color:transparent");
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/products.fxml"));
        borderPane.setCenter(root);

    }

    @FXML
    private void showInoutView(ActionEvent event) throws IOException {
        dashboradBtn.setStyle("-fx-background-color:transparent");
        inoutBtn.setStyle("-fx-background-color:#00acc1");
        productsBtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        settingsBtn.setStyle("-fx-background-color:transparent");
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/inout.fxml"));
        borderPane.setCenter(root);

    }

    @FXML
    private void showLowStockView(ActionEvent event) throws IOException {
        dashboradBtn.setStyle("-fx-background-color:transparent");
        inoutBtn.setStyle("-fx-background-color:transparent");
        productsBtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:#00acc1");
        transactionBtn.setStyle("-fx-background-color:transparent");
        settingsBtn.setStyle("-fx-background-color:transparent");
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/lowstock.fxml"));
        borderPane.setCenter(root);
    }

    @FXML
    private void showTransactionView(ActionEvent event) throws IOException {
        dashboradBtn.setStyle("-fx-background-color:transparent");
        inoutBtn.setStyle("-fx-background-color:transparent");
        productsBtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:#00acc1");
        settingsBtn.setStyle("-fx-background-color:transparent");
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/transactions.fxml"));
        borderPane.setCenter(root);
    }

    private void loadDashboardData() throws ClassNotFoundException {
        try {
            int productCount = productDAO.countProducts();
            int lowStockCount = productDAO.countLowStockProducts();
            productLabel.setText(String.valueOf(productCount));
            lowStockLabel.setText(String.valueOf(lowStockCount));
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showSettingView(ActionEvent event) throws IOException {
        dashboradBtn.setStyle("-fx-background-color:transparent");
        inoutBtn.setStyle("-fx-background-color:transparent");
        productsBtn.setStyle("-fx-background-color:transparent");
        lowStockBtn.setStyle("-fx-background-color:transparent");
        transactionBtn.setStyle("-fx-background-color:transparent");
        settingsBtn.setStyle("-fx-background-color:#00acc1");
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/dbconfig.fxml"));
        borderPane.setCenter(root);
    }

}
