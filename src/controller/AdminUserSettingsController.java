package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import util.Users;

import java.io.IOException;

public class AdminUserSettingsController {
    public AnchorPane AdminSettings;
    public TextField txt_AdminUsername;
    public TextField txt_AdminCurrentPassword;
    public TextField txt_AdminNewPassword;
    public TextField txt_AdminConfirmNewPassword;
    public TableView<Users> tbl_Users;
    public TextField txt_UserUsername;
    public TextField txt_UserPassword;

    static ObservableList<Users> users= FXCollections.observableArrayList();

    public void initialize(){
        txt_AdminUsername.setEditable(false);
        txt_AdminUsername.setText(LoginController.loggedUsername);
        txt_UserUsername.setDisable(true);
        txt_UserPassword.setDisable(true);

        tbl_Users.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tbl_Users.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("delete"));
        tbl_Users.setItems(users);

    }
    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene dashboardScene = new Scene(dashboard);
        Stage primaryStage = (Stage) AdminSettings.getScene().getWindow();
        primaryStage.setScene(dashboardScene);
    }

    public void btnAdminSave_OnAction(ActionEvent actionEvent) {

        if(txt_AdminCurrentPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Current password field cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_AdminNewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"New password field cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_AdminConfirmNewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password confirmation field cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        for (int i = 0; i < LoginController.admins.size(); i++) {
             if(!txt_AdminCurrentPassword.getText().equals(LoginController.admins.get(i).getPassword())){
                 Alert alert=new Alert(Alert.AlertType.ERROR,"Current password is not matched",ButtonType.OK);
                 alert.show();
                 return;

             }
        }

        if(!txt_AdminNewPassword.getText().equals(txt_AdminConfirmNewPassword.getText())){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password confirmation is not matched",ButtonType.OK);
            alert.show();
            return;
        }

        for (int i = 0; i < LoginController.admins.size(); i++) {
             if(LoginController.admins.get(i).getUsername().equals(txt_AdminUsername.getText())){
                 LoginController.admins.get(i).setPassword(txt_AdminNewPassword.getText());
             }

        }

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Successfully changed the password", ButtonType.OK);
        alert.show();


    }

    public void btnUserNew_OnAction(ActionEvent actionEvent) {
        txt_UserUsername.setDisable(false);
        txt_UserPassword.setDisable(false);
        txt_UserUsername.requestFocus();
    }

    public void btnUserSave_OnAction(ActionEvent actionEvent) {

        if(txt_UserUsername.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;

        }

        if(txt_UserPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;
        }

        for (int i = 0; i < LoginController.users.size(); i++) {
             if(LoginController.users.get(i).getUsername().equals(txt_UserUsername.getText())){
                 Alert alert=new Alert(Alert.AlertType.ERROR,"Username is already exist", ButtonType.OK);
                 alert.show();
                 return;
             }

        }

        for (int i = 0; i < LoginController.admins.size(); i++) {
            if(LoginController.admins.get(i).getUsername().equals(txt_UserUsername.getText())){
                Alert alert=new Alert(Alert.AlertType.ERROR,"Username is already exist", ButtonType.OK);
                alert.show();
                return;
            }

        }

        for (int i = 0; i < LoginController.system.size(); i++) {
            if(LoginController.system.get(i).getUsername().equals(txt_UserUsername.getText())){
                Alert alert=new Alert(Alert.AlertType.ERROR,"Username is already exist", ButtonType.OK);
                alert.show();
                return;
            }

        }

        Button delete=new Button();
        Image deleteimg=new Image(getClass().getResourceAsStream("/view/img/delete.png"));
        delete.setGraphic(new ImageView(deleteimg));

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Users selectedItem = tbl_Users.getSelectionModel().getSelectedItem();
                users.remove(selectedItem);
                for (int i = 0; i < LoginController.users.size(); i++) {
                     if(LoginController.users.get(i).getUsername().equals(selectedItem.getUsername())){
                         LoginController.users.remove(i);
                     }

                }


                txt_UserUsername.setText("");
                txt_UserPassword.setText("");
            }
        });

        LoginController.users.add(new Users(txt_UserUsername.getText(),txt_UserPassword.getText(),delete));

        users=FXCollections.observableArrayList(LoginController.users);
        tbl_Users.setItems(users);


    }

    public void btnUserEdit_OnAction(ActionEvent actionEvent) {
        for (int i = 0; i < LoginController.users.size(); i++) {
            if(LoginController.users.get(i).getUsername().equals(tbl_Users.getSelectionModel().getSelectedItem().getUsername())){
                LoginController.users.get(i).setUsername(txt_UserUsername.getText());
                LoginController.users.get(i).setPassword(txt_UserPassword.getText());

            }

        }

        tbl_Users.refresh();
    }
}
