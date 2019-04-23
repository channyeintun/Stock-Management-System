
package stock.management.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import stock.management.system.database.Database;

/**
 *
 * @author Chan Nyein Tun
 */
public class StockManagementSystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Stock Management System");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
