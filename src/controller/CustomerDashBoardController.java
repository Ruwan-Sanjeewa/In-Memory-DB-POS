package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class CustomerDashBoardController {

    @FXML
    private AnchorPane root;





    @FXML
    private void btnManageItems_OnAction(ActionEvent actionEvent) throws IOException {
        Parent items= FXMLLoader.load(this.getClass().getResource("/view/ShowItems.fxml"));
        Scene itemScene=new Scene(items);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(itemScene);
        primaryStage.setTitle("Show Items ");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }





    @FXML
    private void btnPlaceOrders__OnAction(ActionEvent actionEvent) throws IOException {
        Parent placeOrder= FXMLLoader.load(this.getClass().getResource("/view/PlaceOrder.fxml"));
        Scene placeOrderScene=new Scene(placeOrder);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(placeOrderScene);
        primaryStage.setTitle(" Place Order ");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();


    }



    @FXML
    private void btnSearchOders_OnAction(ActionEvent actionEvent) throws IOException {
        Parent view= FXMLLoader.load(this.getClass().getResource("/view/SearchOrders.fxml"));
        Scene viewScene=new Scene(view);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(viewScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Search Orders");
    }


    public void btnLogOut_OnAction(ActionEvent actionEvent) throws IOException {
        Parent login= FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene loginScene=new Scene(login);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(loginScene);

    }

    public void btnSettings_OnAction(ActionEvent actionEvent) throws IOException {
        Parent settings = FXMLLoader.load(this.getClass().getResource("/view/UserSettings.fxml"));
        Scene settingsScenes = new Scene(settings);
        Stage primaryStage = (Stage) root.getScene().getWindow();
        primaryStage.setScene(settingsScenes);
        primaryStage.setTitle(" User");

    }
}
