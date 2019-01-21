package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.AdminUsers;
import util.SystemUser;


import java.io.IOException;

public class DashBoardController {
    @FXML
    private AnchorPane root;

    @FXML
    private void btnMangeCustomers_OnAction(ActionEvent actionEvent) throws IOException {
        Parent customers= FXMLLoader.load(this.getClass().getResource("/view/ManageCustomers.fxml"));
        Scene customersScene=new Scene(customers);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(customersScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Customers");
    }

    @FXML
    private void btnManageItems_OnAction(ActionEvent actionEvent) throws IOException {
        Parent items= FXMLLoader.load(this.getClass().getResource("/view/ManageItems.fxml"));
        Scene itemsScene=new Scene(items);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(itemsScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Items");
    }

    @FXML
    private void btnPlaceOrders__OnAction(ActionEvent actionEvent) throws IOException {
        Parent order= FXMLLoader.load(this.getClass().getResource("/view/PlaceOrder.fxml"));
        Scene orderScene=new Scene(order);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(orderScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Place Order");
    }

    @FXML
    private void btnSearch_OnAction(ActionEvent actionEvent) throws IOException {
        Parent view= FXMLLoader.load(this.getClass().getResource("/view/SearchOrders.fxml"));
        Scene viewScene=new Scene(view);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(viewScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Search Orders ");
    }

    @FXML
    private void btnLogOut_OnAction(ActionEvent actionEvent) throws IOException {
        Parent login= FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        Scene loginScene=new Scene(login);
        Stage primaryStage= (Stage) root.getScene().getWindow();
        primaryStage.setScene(loginScene);

    }

    @FXML
    private void btnSettings_OnAction(ActionEvent actionEvent) throws IOException {
        if (LoginController.loggedUser instanceof SystemUser){
            Parent systemSettings = FXMLLoader.load(this.getClass().getResource("/view/SystemUserSettings.fxml"));
            Scene systemSettingsScene = new Scene(systemSettings);
            Stage primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.setScene(systemSettingsScene);
            primaryStage.setTitle("System");
            return;
        }else if(LoginController.loggedUser instanceof AdminUsers){
            Parent adminSettings = FXMLLoader.load(this.getClass().getResource("/view/AdminUserSettings.fxml"));
            Scene adminSettingsScene = new Scene(adminSettings);
            Stage primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.setScene(adminSettingsScene);
            primaryStage.setTitle("Admin");
            return;
        }

    }
}
