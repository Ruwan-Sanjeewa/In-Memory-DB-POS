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
import util.AdminUsers;
import util.SystemUser;
import util.Users;

import java.io.IOException;
import java.util.ArrayList;


public class LoginController {
    public AnchorPane login;
    public TextField txt_UserName;
    public TextField txt_Password;
    static ArrayList<SystemUser> system=new ArrayList<>();
    static ArrayList<AdminUsers>admins=new ArrayList<>();
    static ArrayList<Users>users=new ArrayList<>();

    static Object loggedUser;
    static String loggedUsername;
    static {
        system.add(new SystemUser("1","1"));
    }
    public void btnLogin_OnAction(ActionEvent actionEvent) throws IOException {
        String username = txt_UserName.getText();
        String password = txt_Password.getText();

        if (username.trim().isEmpty() & password.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fields cannot be empty", ButtonType.OK);
            alert.show();
            return;
        }



        int count1 = 0;
        int count2=0;
        int count3=0;
        for (int i = 0; i < system.size(); i++) {
            if (username.equals(system.get(i).getUsername()) & password.equals(system.get(i).getPassword())) {
                loggedUser = system.get(i);
                loggedUsername=system.get(i).getUsername();
                count1++;

            }
        }

        for (int i = 0; i < admins.size(); i++) {
            if (username.equals(admins.get(i).getUsername()) & password.equals(admins.get(i).getPassword())) {
                loggedUser = admins.get(i);
                loggedUsername=admins.get(i).getUsername();
                count2++;

            }

        }

        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername()) & password.equals(users.get(i).getPassword())) {
                loggedUser = users.get(i);
                loggedUsername=users.get(i).getUsername();
                count3++;

            }

        }

        if (count1 > 0 || count2>0) {
            Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene dashboardScene = new Scene(dashboard);
            Stage primaryStage = (Stage) login.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }
        else if(count3>0){
            Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/CustomerDashBoard.fxml"));
            Scene dashboardScene = new Scene(dashboard);
            Stage primaryStage = (Stage) login.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
            alert.show();

        }


    }



}
