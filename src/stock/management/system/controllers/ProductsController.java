
package stock.management.system.controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import stock.management.system.dao.ProductDAO;
import stock.management.system.model.Product;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun
 */
public class ProductsController implements Initializable {

    @FXML
    private Button addBtn;
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
    private MenuItem deleteItem;
    @FXML
    private JFXTextField nameSearchField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        productDAO = new ProductDAO();
        productTable.setEditable(true);
       
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        
        initColumns();
        
        
        try {
            loadTableData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void loadNewProductWindow(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/newproduct.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initOwner(addBtn.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        loadTableData();
    }

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void loadTableData() throws ClassNotFoundException {
        try {
            List<Product> products = productDAO.getProducts();
            productTable.getItems().setAll(products);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) throws ClassNotFoundException {

      
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

    
        if (selectedProduct == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select the item you want to delete.");
            alert.show();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            try {
                // Delete Product
                productDAO.deleteProduct(selectedProduct.getId());
                productTable.getItems().remove(selectedProduct);
            } catch (SQLException ex) {
                Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void updateProductName(TableColumn.CellEditEvent<Product, String> event) throws ClassNotFoundException {
       
        Product product = event.getRowValue();
        product.setName(event.getNewValue());
        
        try {
            productDAO.updateProduct(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void updateProductPrice(TableColumn.CellEditEvent<Product, Double> event) throws ClassNotFoundException {
       
         Product product = event.getRowValue();
         product.setPrice(event.getNewValue());
         
        try {
            productDAO.updateProduct(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void searchProductByName(ActionEvent event) throws ClassNotFoundException {
       String query = nameSearchField.getText();
       
        try {
            List<Product> products  = productDAO.getProductsByName(query);
            productTable.getItems().setAll(products);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    

    

}
