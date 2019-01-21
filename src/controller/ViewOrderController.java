package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ManageCustomers;
import model.ManageOrders;
import util.Order;
import util.OrderDetail;
import view.util.OrderDetailTM;
import view.util.ViewOrderTM;

import java.io.IOException;
import java.util.ArrayList;

public class ViewOrderController {
    @FXML
    private AnchorPane ViewOrder;
    @FXML
    private JFXTextField txt_OrderId;
    @FXML
    private JFXTextField txt_CustomerName;
    @FXML
    private JFXTextField txt_CustomerId;
    @FXML
    private JFXTextField txt_OrderDate;
    @FXML
    private TableView <OrderDetailTM>tbl_ViewOrderDetails;
    @FXML
    private Label lbl_Total;

    public  void initialize(){
        txt_OrderId.setDisable(false);
        txt_OrderDate.setEditable(false);
        txt_CustomerId.setEditable(false);
        txt_CustomerName.setEditable(false);

        tbl_ViewOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tbl_ViewOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tbl_ViewOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tbl_ViewOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbl_ViewOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));


    }


    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashBoard= FXMLLoader.load(this.getClass().getResource("/view/SearchOrders.fxml"));
        Scene dashBoardScene=new Scene(dashBoard);
        Stage primaryStage= (Stage) ViewOrder.getScene().getWindow();
        primaryStage.setScene(dashBoardScene);
    }


    public void setInitialData(String orderId){
        ArrayList<Order> orders = ManageOrders.getOrders();
        for (Order order : orders) {
            if(order.getId().equals(orderId)){
                txt_OrderId.setText(order.getId());
                txt_OrderDate.setText(String.valueOf(order.getDate()));
                txt_CustomerId.setText(order.getCustomerId());
                txt_CustomerName.setText(ManageCustomers.Seacrh_Cusotmers(order.getId()).getName());
            }
        }

        ArrayList<OrderDetail> orderDetails =   Order.getOrderDetails();
        ObservableList<OrderDetailTM> details = FXCollections.observableArrayList();

        for (OrderDetail orderDetail : orderDetails) {
            details.add(new OrderDetailTM(orderDetail.getItemCode(),
                    orderDetail.getName(),
                    orderDetail.getQuantity(),
                    (int) orderDetail.getUnitPrice(),
                    orderDetail.getQuantity() * orderDetail.getUnitPrice()));
        }
        tbl_ViewOrderDetails.setItems(details);

        ObservableList<OrderDetailTM> items = tbl_ViewOrderDetails.getItems();
        double total=0;
        for (OrderDetailTM item : items) {
            total+=item.getTotal();
        }
        lbl_Total.setText(""+total);
    }
}
