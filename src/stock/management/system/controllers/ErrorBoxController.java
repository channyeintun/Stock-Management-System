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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CNT
 */
public class ErrorBoxController implements Initializable {

    @FXML
    private Button ExitBtn;
    @FXML
    private JFXButton OKBtn;
    @FXML
    public Label ErrorLBL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ExitApp(ActionEvent event) {
        Stage stage = (Stage) ExitBtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void CloseAlert(ActionEvent event) {
        Stage stage = (Stage) OKBtn.getScene().getWindow();
        stage.close();
    }

    public void setErrorLBL(String text) {
        ErrorLBL.setText(text);
    }

}
