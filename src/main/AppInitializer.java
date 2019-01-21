package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root= FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene rootScene=new Scene(root);
        primaryStage.setScene(rootScene);
        primaryStage.show();
        primaryStage.setTitle("POS");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);

    }
}
