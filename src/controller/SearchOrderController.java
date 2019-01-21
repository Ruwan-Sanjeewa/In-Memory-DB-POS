package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ManageCustomers;
import model.ManageOrders;
import util.AdminUsers;
import util.Order;
import util.SystemUser;
import view.util.OrderDetailTM;
import view.util.ViewOrderTM;

import java.io.IOException;
import java.util.ArrayList;

public class SearchOrderController {
    @FXML
    private JFXTextField txt_Search;
    @FXML
    private TableView<ViewOrderTM> tbl_ViewOrders;
    ObservableList<ViewOrderTM>viewOrders=FXCollections.observableArrayList();
    @FXML
    private AnchorPane view;


    public  void initialize(){
        tbl_ViewOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tbl_ViewOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tbl_ViewOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tbl_ViewOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));


        ArrayList<Order> orders = ManageOrders.getOrders();
        ObservableList<Order>ordersList=FXCollections.observableArrayList(orders);

        for (Order order : ordersList) {
            viewOrders.add(new ViewOrderTM(order.getId(),order.getDate(),order.getCustomerId(),
                    ManageCustomers.Seacrh_Cusotmers(order.getCustomerId()).getName()));
        }

        tbl_ViewOrders.setItems(viewOrders);

    }

    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        if(LoginController.loggedUser instanceof SystemUser) {
            Parent dashBoard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene dashboardScene = new Scene(dashBoard);
            Stage primaryStage = (Stage) view.getScene().getWindow();
            primaryStage.setScene(dashboardScene);
        }

        else if(LoginController.loggedUser instanceof AdminUsers){
            Parent dashBoard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene dashboardScene = new Scene(dashBoard);
            Stage primaryStage = (Stage) view.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }

        else{
            Parent dashBoard = FXMLLoader.load(this.getClass().getResource("/view/CustomerDashBoard.fxml"));
            Scene dashboardScene = new Scene(dashBoard);
            Stage primaryStage = (Stage) view.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }
    }

    public void txtSearch_OnKeyReleased(KeyEvent keyEvent) {
        String search = txt_Search.getText();
        ObservableList<ViewOrderTM>templist=FXCollections.observableArrayList();
        for (ViewOrderTM viewOrder : viewOrders) {
            if(viewOrder.getOrderId().startsWith(search)){
                templist.add(viewOrder);

            }
        }
        tbl_ViewOrders.setItems(templist);

    }


    public void tblViewOrders_OnMouseCliked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2){

            ViewOrderTM selectedItem = tbl_ViewOrders.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ViewOrders.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ViewOrderController controller = fxmlLoader.getController();
            controller.setInitialData(selectedItem.getOrderId());
            Scene scene = new Scene(root);
            ((Stage)tbl_ViewOrders.getScene().getWindow()).setScene(scene);
        }
    }
}
