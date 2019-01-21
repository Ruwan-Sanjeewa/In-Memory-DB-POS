package util;

import javafx.scene.control.Button;

public class AdminUsers {
    private String username;
    private String password;
    private Button delete;

    public AdminUsers() {

    }

    public AdminUsers(String username, String password, Button delete) {
        this.username = username;
        this.password = password;
        this.setDelete(delete);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
