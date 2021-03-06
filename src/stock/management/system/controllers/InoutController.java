package stock.management.system.controllers;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stock.management.system.StockManagementSystem;
import stock.management.system.dao.ProductDAO;
import stock.management.system.dao.TransactionDAO;
import stock.management.system.model.Product;
import stock.management.system.model.Transaction;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun
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
    @FXML
    private Button CloseApp;

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
    private void saveTransaction(ActionEvent event) throws ClassNotFoundException, IOException {

        String productIdStr = productIdField.getText();
        String quantityStr = quantityField.getText();
        String remark = remarkField.getText();

        if (productIdStr.isEmpty() || quantityStr.isEmpty() || remark.isEmpty()) {
            showErrorBox("Please fill all input fields");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            String type = (String) transactionTypeGroup.getSelectedToggle().getUserData();

            if (productDAO.isProductExist(productId)) {

                Product product = productDAO.getProduct(productId);
                int stock = product.getStock();
                if (type.equals("IN")) {
                    product.setStock(stock + quantity);
                    productDAO.updateProduct(product);
                } else {
                    if (quantity <= stock) {
                        product.setStock(stock - quantity);
                        productDAO.updateProduct(product);
                    } else {
                        showErrorBox("Product does not exist!");
                        return;
                    }
                }
                Transaction transaction = new Transaction(type, productId, quantity, remark);
                transactionDAO.saveTransaction(transaction);
                showSuccessBox();
                clearForm();
            } else {
                showErrorBox("Product does not exit!");
            }

        } catch (NumberFormatException e) {
            showErrorBox("Invalid Number!");
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

    @FXML
    private void closeApp(ActionEvent event) {
        Stage stage = (Stage) CloseApp.getScene().getWindow();
        stage.close();
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

    private void showSuccessBox() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/AlertBox.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(StockManagementSystem.class.getResourceAsStream("/stock/management/system/logo/logo.png")));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

}
