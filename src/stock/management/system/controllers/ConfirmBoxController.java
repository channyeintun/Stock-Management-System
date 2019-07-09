/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.management.system.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CNT
 */
public class ConfirmBoxController implements Initializable {

    @FXML
    private JFXButton YesBtn;
    @FXML
    private JFXButton NoBtn;
    private String returnValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProductsController.injectConfirmController(this);
    }

    @FXML
    private void getConfrim(ActionEvent event) {
        Stage stage = (Stage) YesBtn.getScene().getWindow();
        returnValue = "Yes";
        ProductsController.StoreValue();
        stage.close();

    }

    @FXML
    private void CloseConfirm(ActionEvent event) {
        Stage stage = (Stage) NoBtn.getScene().getWindow();
        returnValue = "No";
        ProductsController.StoreValue();
        stage.close();
    }

    public String getReturnValue() {
        return returnValue;
    }

}
