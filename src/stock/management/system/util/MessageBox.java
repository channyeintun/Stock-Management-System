
package stock.management.system.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Chan Nyein Tun
 */
public class MessageBox {
    
    public static void showInformationMessage(String title,String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    
    public static void showErrorMessage(String title,String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    
}
