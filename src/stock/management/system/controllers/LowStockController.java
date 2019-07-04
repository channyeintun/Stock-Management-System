package stock.management.system.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import stock.management.system.dao.ProductDAO;
import stock.management.system.model.Product;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun
 */
public class LowStockController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> stockColumn;

    private ProductDAO productDAO;
    @FXML
    private Button CloseApp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productDAO = new ProductDAO();
        initCOlumns();
        try {
            loadTableData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LowStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initCOlumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void loadTableData() throws ClassNotFoundException {
        try {
            List<Product> products = productDAO.getLowStockProducts();
            productTable.getItems().setAll(products);
        } catch (SQLException ex) {
            Logger.getLogger(LowStockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Stage stage = (Stage) CloseApp.getScene().getWindow();
        stage.close();
    }

}
