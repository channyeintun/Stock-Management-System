
package stock.management.system.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.InputStream;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import stock.management.system.dao.TransactionDAO;
import stock.management.system.model.Transaction;
import stock.management.system.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Chan Nyein Tun   
 */
public class TransactionsController implements Initializable {

    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private JFXButton submitBtn;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, String> productNameColumn;
    @FXML
    private TableColumn<Transaction, Integer> quantityColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> remarkColumn;

    private TransactionDAO transactionDAO;
    @FXML
    private JFXButton reportBtn;
    @FXML
    private Button CloseApp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transactionDAO = new TransactionDAO();
        initColumns();
    }

    @FXML
    private void loadTransactions(ActionEvent event) throws ClassNotFoundException {
       
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        
        if (startDate == null || endDate == null) {
            MessageBox.showErrorMessage("Error", "Select the start date and end date.");
            return;
        }
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate.plusDays(1));
        try {
           
            List<Transaction> transactions = transactionDAO.getTransactions(startSqlDate,endSqlDate);
            transactionTable.getItems().setAll(transactions);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }

    @FXML
    private void getReport(ActionEvent event) throws SQLException, ClassNotFoundException, JRException, UnsupportedEncodingException {
         LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // validating
        if (startDate == null || endDate == null) {
            MessageBox.showErrorMessage("Error", "Select the start date and end date.");
            return;
        }
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate.plusDays(1));
        ResultSet resultSet=transactionDAO.getTransactionResultSet(startSqlDate, endSqlDate);
        JRResultSetDataSource reportSource=new JRResultSetDataSource(resultSet);
        InputStream is=this.getClass().getResourceAsStream("/stock/management/system/report/report1.jrxml");
     
        JasperReport jasperReport = JasperCompileManager.compileReport(is);
        JasperPrint jprint=JasperFillManager.fillReport(jasperReport,new HashMap(),reportSource);
        JasperViewer reportViewer=new JasperViewer(jprint,false);
        reportViewer.setVisible(true);
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Stage stage = (Stage) CloseApp.getScene().getWindow();
        stage.close();
    }

}
