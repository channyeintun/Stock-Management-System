package stock.management.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockManagementSystem extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/stock/management/system/views/main.fxml"));

        root.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX()-xOffset);
            stage.setY(e.getScreenY()-yOffset);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Stock Management System");
        stage.getIcons().add(new Image(StockManagementSystem.class.getResourceAsStream("/stock/management/system/logo/logo.png")));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
