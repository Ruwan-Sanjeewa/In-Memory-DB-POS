package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import util.Users;

import java.io.IOException;

public class UserSettingsController {
    public AnchorPane UserSettings;
    public TextField txt_Username;
    public TextField txt_CurrentPassword;
    public TextField txt_NewPassword;
    public TextField txt_ConfirmNewPassword;

    public void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/CustomerDashBoard.fxml"));
        Scene dashboardScene = new Scene(dashboard);
        Stage primaryStage = (Stage) UserSettings.getScene().getWindow();
        primaryStage.setScene(dashboardScene);
    }

    public void btnChange_OnAction(ActionEvent actionEvent) {
        if(txt_Username.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Username field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_CurrentPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Current password field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_NewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"New password field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;
        }

        if(txt_ConfirmNewPassword.getText().trim().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Confirm password field cannot be empty  ", ButtonType.OK);
            alert.show();
            return;
        }

        for (int i = 0; i < LoginController.users.size(); i++) {
             if (LoginController.users.get(i).getUsername().equals(txt_Username.getText())){
                 if(!LoginController.users.get(i).getPassword().equals(txt_CurrentPassword.getText())) {
                     Alert alert = new Alert(Alert.AlertType.ERROR, "current password is not correct", ButtonType.OK);
                     alert.show();
                     return;
                 }
             }
        }

        if(txt_CurrentPassword.getText().equals(txt_ConfirmNewPassword.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Password confirmation is not correct", ButtonType.OK);
            alert.show();
            return;
        }


        for (Users user : LoginController.users) {
            if(user.getUsername().equals(txt_Username.getText())){
                user.setPassword(txt_NewPassword.getText());
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully changed the password", ButtonType.OK);
        alert.show();
        return;

    }
}
