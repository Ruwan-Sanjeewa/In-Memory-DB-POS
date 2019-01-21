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

import util.AdminUsers;
import util.Users;

import java.io.IOException;

public class SystemUserSettingsController {

    public AnchorPane SystemSettings;
    public TextField txt_SystemCurrentPassword;
    public TextField txt_SystemNewPassword;
    public TextField txt_ConfirmNewPassword;
    public TextField txt_AdminUsername;
    public TextField txt_AdminPassword;
    public TableView<AdminUsers>tbl_Admins;
    public TableView<Users> tbl_Users;
    public TextField txt_UserUsername;
    public TextField txt_UserPassword;

    static ObservableList<AdminUsers>Admins= FXCollections.observableArrayList();
    static ObservableList<Users>Users=FXCollections.observableArrayList();






    public void initialize(){
        tbl_Admins.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tbl_Admins.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("delete"));
        tbl_Admins.setItems(Admins);

        tbl_Users.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tbl_Users.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("delete"));
        tbl_Users.setItems(Users);

        txt_AdminUsername.setDisable(true);
        txt_AdminPassword.setDisable(true);
        txt_UserUsername.setDisable(true);
        txt_UserPassword.setDisable(true);
    }

    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene dashboardScene = new Scene(dashboard);
        Stage primaryStage = (Stage) SystemSettings.getScene().getWindow();
        primaryStage.setScene(dashboardScene);
    }

    public void btnChange_OnAction(ActionEvent actionEvent) {
        if(txt_SystemCurrentPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Current password cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_SystemNewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"New password cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_ConfirmNewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password confirmation field cannot be empty",ButtonType.OK);
            alert.show();
            return;
        }

        for (int i = 0; i < LoginController.system.size(); i++) {
            if(!LoginController.system.get(i).getPassword().equals(txt_SystemCurrentPassword.getText())){
                Alert alert=new Alert(Alert.AlertType.ERROR,"Current password is not correct",ButtonType.OK);
                alert.show();
                return;
            }

        }

        if(!txt_SystemNewPassword.getText().equals(txt_ConfirmNewPassword.getText())){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password confirmation is not correct",ButtonType.OK);
            alert.show();
            return;

        }
        LoginController.system.get(0).setPassword(txt_SystemNewPassword.getText());
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Successfully changed the password", ButtonType.OK);
        alert.show();
    }

    public void btnAdminNew_onAction(ActionEvent actionEvent) {
        txt_AdminUsername.setDisable(false);
        txt_AdminPassword.setDisable(false);
        txt_AdminUsername.requestFocus();
    }

    public void btnAdminSave_OnAction(ActionEvent actionEvent) {
        String username=txt_AdminUsername.getText();
        String password=txt_AdminPassword.getText();

        Button delete =new Button();

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                AdminUsers selectedItem = tbl_Admins.getSelectionModel().getSelectedItem();
                Admins.remove(selectedItem);
                txt_AdminUsername.setText("");
                txt_AdminPassword.setText("");

            }
        });


        int count=0;
        for (int i = 0; i < LoginController.admins.size(); i++) {
            if(username.equals(LoginController.admins.get(i).getUsername())){
                count++;
            }
        }

        for (int i = 0; i < LoginController.users.size(); i++) {
             if(username.equals(LoginController.users.get(i).getUsername())){
                 count++;
             }
        }

        for (int i = 0; i < LoginController.system.size(); i++) {
             if(username.equals(LoginController.system.get(i).getUsername())){
                 count++;
             }
        }
        if(count>0){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username is already exist", ButtonType.OK);
            alert.show();
            txt_AdminUsername.setText("");
            txt_AdminUsername.requestFocus();
            return;
        }

        if(username.trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username cannot be empty", ButtonType.OK);
            alert.show();
            return;
        }

        if(password.trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password cannot be empty", ButtonType.OK);
            alert.show();
            return;
        }

        Image deleteimg=new Image(getClass().getResourceAsStream("/view/img/delete.png"));
        delete.setGraphic(new ImageView(deleteimg));
        if(LoginController.admins.size()<2) {
            LoginController.admins.add(new AdminUsers(username, password, delete));

        }
        else{
            Alert alert1=new Alert(Alert.AlertType.ERROR,"You can add maximum 2 Admins", ButtonType.OK);
            alert1.show();
            return;
        }





        Alert alert1=new Alert(Alert.AlertType.CONFIRMATION,"Successfully added new Admin", ButtonType.OK);
        alert1.show();
        Admins=FXCollections.observableList(LoginController.admins);
        tbl_Admins.setItems(Admins);

        txt_AdminUsername.setText("");
        txt_AdminPassword.setText("");
        txt_AdminUsername.setDisable(true);
        txt_AdminPassword.setDisable(true);


    }

    public void AdminTableSelect(){
        txt_AdminUsername.setDisable(false);
        txt_AdminPassword.setDisable(false);
        txt_AdminUsername.setText(tbl_Admins.getSelectionModel().getSelectedItem().getUsername());
        for (int i = 0; i < LoginController.admins.size(); i++) {
             if(LoginController.admins.get(i).getUsername().equals(tbl_Admins.getSelectionModel().getSelectedItem().getUsername())){
                 txt_AdminPassword.setText(LoginController.admins.get(i).getPassword());
             }

        }
    }

    public void btnUserNew_OnAction(ActionEvent actionEvent) {
        txt_UserUsername.setDisable(false);
        txt_UserPassword.setDisable(false);
        txt_UserUsername.requestFocus();

    }

    public void btnUserSave_OnAction(ActionEvent actionEvent) {
        String username=txt_UserUsername.getText();
        String password=txt_UserPassword.getText();

        Button delete =new Button();
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Users selectedItem = tbl_Users.getSelectionModel().getSelectedItem();
                Users.remove(selectedItem);
                txt_UserUsername.setText("");
                txt_UserPassword.setText("");
            }
        });


        int count=0;
        for (int i = 0; i < LoginController.users.size(); i++) {
            if(username.equals(LoginController.users.get(i).getUsername())){
                count++;
            }
        }

        for (int i = 0; i < LoginController.admins.size(); i++) {
             if(username.equals(LoginController.admins.get(i).getUsername())){
                 count++;
             }

        }
        for (int i = 0; i < LoginController.system.size(); i++) {
             if(username.equals(LoginController.system.get(i).getUsername())){
                 count++;
             }

        }
        if(count>0){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username is already exist ", ButtonType.OK);
            alert.show();
            txt_UserUsername.setText("");
            txt_UserUsername.requestFocus();
            return;
        }


        if(username.trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username cannot be empty", ButtonType.OK);
            alert.show();
            return;

        }

        if(password.trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Password cannot be empty", ButtonType.OK);
            alert.show();
            return;

        }

        Image deleteimg=new Image(getClass().getResourceAsStream("/view/img/delete.png"));
        delete.setGraphic(new ImageView(deleteimg));

        LoginController.users.add(new Users(username,password,delete));

        Users=FXCollections.observableList(LoginController.users);
        tbl_Users.setItems(Users);

        txt_UserUsername.setText("");
        txt_UserPassword.setText("");
        txt_UserUsername.setDisable(true);
        txt_UserPassword.setDisable(true);
    }

    public void UserTableSelect(){
        txt_UserUsername.setDisable(false);
        txt_UserPassword.setDisable(false);
        txt_UserUsername.setText(tbl_Users.getSelectionModel().getSelectedItem().getUsername());

        for (int i = 0; i < LoginController.users.size(); i++) {
             if(tbl_Users.getSelectionModel().getSelectedItem().getUsername().equals(LoginController.users.get(i).getUsername())){
                 txt_UserPassword.setText(LoginController.users.get(i).getPassword());
             }

        }

    }



   public void btnAdminEdit_onAction(ActionEvent actionEvent) {
        for (int i = 0; i < LoginController.admins.size(); i++) {
            if(LoginController.admins.get(i).getUsername().equals(tbl_Admins.getSelectionModel().getSelectedItem().getUsername())){
                 LoginController.admins.get(i).setUsername(txt_AdminUsername.getText());
                 LoginController.admins.get(i).setPassword(txt_AdminPassword.getText());

             }

        }

        tbl_Admins.refresh();
   }

   public void btnUserEdit_onAction(ActionEvent actionEvent) {
       for (int i = 0; i < LoginController.users.size(); i++) {
           if(LoginController.users.get(i).getUsername().equals(tbl_Users.getSelectionModel().getSelectedItem().getUsername())){
                LoginController.users.get(i).setUsername(txt_UserUsername.getText());
                LoginController.users.get(i).setPassword(txt_UserPassword.getText());

            }

        }

        tbl_Users.refresh();
   }




}

