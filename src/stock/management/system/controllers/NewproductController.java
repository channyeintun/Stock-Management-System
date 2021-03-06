package stock.management.system.controllers;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stock.management.system.dao.ProductDAO;
import stock.management.system.model.Product;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun
 */
public class NewproductController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField priceField;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;

    private ProductDAO productDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productDAO = new ProductDAO();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) saveBtn.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void saveNewProduct(ActionEvent event) throws ClassNotFoundException, IOException {

        String name = nameField.getText();
        String idStr = idField.getText();
        String priceStr = priceField.getText();

        if (name.isEmpty() || idStr.isEmpty() || priceStr.isEmpty()) {
            showErrorBox("Please fill all input fields");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            Product product = new Product(id, name, price, 0);
            productDAO.save(product);
            Stage currentStage = (Stage) saveBtn.getScene().getWindow();
            currentStage.close();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (SQLException ex) {
            System.out.println("Error,Duplicat ID.");
            Logger.getLogger(NewproductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void showErrorBox(String text) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stock/management/system/views/ErrorBox.fxml"));
        Parent root = loader.load();
        ErrorBoxController controller = loader.getController();
        controller.setErrorLBL(text);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
