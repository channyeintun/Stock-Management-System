package stock.management.system.controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;
import stock.management.system.dao.ProductDAO;
import stock.management.system.model.Product;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun
 */
public class ProductsController implements Initializable {
    
    public static ConfirmBoxController confirmController;
    
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
    @FXML
    private Button CloseApp;
    private static String storedValue;

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
    
    public static void injectConfirmController(ConfirmBoxController confirmContr) {
        confirmController = confirmContr; //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    private void loadNewProductWindow(ActionEvent event) throws IOException, ClassNotFoundException {
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/newproduct.fxml"));
        Stage stage = new Stage();
        
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        
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
    private void deleteProduct(ActionEvent event) throws ClassNotFoundException, IOException {
        
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        
        if (selectedProduct == null) {
            showErrorBox("Select the item you want to delete");
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/ConfirmBox.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
        if (storedValue.equals("Yes")) {
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
            List<Product> products = productDAO.getProductsByName(query);
            productTable.getItems().setAll(products);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    public static void StoreValue() {
        storedValue = confirmController.getReturnValue();
    }
}
