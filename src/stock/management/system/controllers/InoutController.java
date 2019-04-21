/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.management.system.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import stock.management.system.dao.ProductDAO;
import stock.management.system.dao.TransactionDAO;
import stock.management.system.model.Product;
import stock.management.system.model.Transaction;
import stock.management.system.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class InoutController implements Initializable {

    @FXML
    private ToggleGroup transactionTypeGroup;
    @FXML
    private JFXTextField productIdField;
    @FXML
    private JFXTextField quantityField;
    @FXML
    private JFXTextField remarkField;
    @FXML
    private RadioButton inRadio;
    @FXML
    private RadioButton outRadio;
    @FXML
    private JFXButton saveBtn;
    
    private ProductDAO productDAO;
    private TransactionDAO transactionDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productDAO = new ProductDAO();
        transactionDAO = new TransactionDAO();
        inRadio.setSelected(true);
        inRadio.setUserData("IN");
        outRadio.setUserData("OUT");
    }

    @FXML
    private void saveTransaction(ActionEvent event) {

        // Get Data
        String productIdStr = productIdField.getText();
        String quantityStr = quantityField.getText();
        String remark = remarkField.getText();

        if (productIdStr.isEmpty() || quantityStr.isEmpty() || remark.isEmpty()) {
            MessageBox.showErrorMessage("Input Error", "Please fill out all field.");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            String type = (String)transactionTypeGroup.getSelectedToggle().getUserData();
            
            if(productDAO.isProductExist(productId)){
               
                
                Product product = productDAO.getProduct(productId);
                int stock = product.getStock();
                if(type.equals("IN")){
                    product.setStock(stock+quantity);
                    productDAO.updateProduct(product);
                }else{
                    if(quantity<=stock){
                        product.setStock(stock-quantity);
                        productDAO.updateProduct(product);
                    }else{
                        MessageBox.showErrorMessage("Input Error","Product Out quantity is greater than stock.");
                        return;
                    }
                }
                 // continue saving process.
                Transaction transaction = new Transaction(type, productId, quantity, remark);
                transactionDAO.saveTransaction(transaction);
                MessageBox.showInformationMessage("Success", "Successfully saved.");
                clearForm();
            }else{
                MessageBox.showErrorMessage("Input Error","Product does not exists.");
            }
            
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Input Error","Invalid Number.");
        } catch (SQLException ex) {
            Logger.getLogger(InoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearForm() {
        productIdField.clear();
        quantityField.clear();
        remarkField.clear();
        inRadio.setSelected(true);
    }

}
