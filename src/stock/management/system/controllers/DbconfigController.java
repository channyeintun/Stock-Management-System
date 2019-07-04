package stock.management.system.controllers;

import stock.management.system.model.DbConfig;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stock.management.system.util.DbConfigLoader;
import stock.management.system.util.MessageBox;

public class DbconfigController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button CloseApp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DbConfig dbConfig = DbConfigLoader.loadDbConfig();

        if (dbConfig == null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, 3306);
            portSpinner.setValueFactory(valueFactory);
        } else {
            hostField.setText(dbConfig.getHost());
            usernameField.setText(dbConfig.getUser());
            passwordField.setText(dbConfig.getPassword());
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, dbConfig.getPort());
            portSpinner.setValueFactory(valueFactory);
        }

    }

    @FXML
    private void saveDatabaseConfig(ActionEvent event) {

        String host = hostField.getText();
        String port = portSpinner.getValue().toString();
        String user = usernameField.getText();
        String password = passwordField.getText();

        DbConfigLoader.saveDbConfig(new DbConfig(host, Integer.parseInt(port), user, password));
        MessageBox.showInformationMessage("Saved", "Changed Settings");
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Stage stage = (Stage) CloseApp.getScene().getWindow();
        stage.close();
    }
}
