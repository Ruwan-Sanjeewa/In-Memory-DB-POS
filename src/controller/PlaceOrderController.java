package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ManageCustomers;
import model.ManageItems;
import model.ManageOrders;
import util.*;
import view.util.OrderDetailTM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderController {

    @FXML
    private JFXButton btn_PlaceOrder;
    @FXML
    private JFXButton btn_Remove;
    @FXML
    private JFXTextField txt_OrderDate;
    @FXML
    private JFXButton btn_Add;
    @FXML
    private JFXTextField txt_OrderID;
    @FXML
    private JFXTextField txt_ItemName;
    @FXML
    private JFXTextField txt_ItemCode;
    @FXML
    private JFXTextField txt_CustomerName;
    @FXML
    private JFXTextField txt_CustomerID;
    @FXML
    private JFXTextField txt_QuantityOnHand;
    @FXML
    private JFXTextField txt_UnitPrice;
    @FXML
    private JFXTextField txt_Quantity;
    @FXML
    private TableView<OrderDetailTM> tbl_orders;
    @FXML
    private Label lbl_total;
    @FXML
    private AnchorPane orders;

    ArrayList<Items>tempItemsdb=new ArrayList<>();
    public void initialize(){
        txt_OrderID.setEditable(false);
        txt_OrderDate.setEditable(false);
        txt_CustomerName.setEditable(false);
        txt_ItemName.setEditable(false);
        txt_QuantityOnHand.setEditable(false);
        txt_UnitPrice.setEditable(false);

        Platform.runLater(() -> {
            txt_CustomerID.requestFocus();
        });


        btn_Remove.setDisable(true);

        btn_PlaceOrder.setDisable(true);

        txt_OrderID.setText(ManageOrders.generateOrderId());
        txt_OrderDate.setText(String.valueOf(LocalDate.now()));

        tbl_orders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tbl_orders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tbl_orders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tbl_orders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbl_orders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));


         ArrayList<Items> tempdb = ManageItems.getItemsArraylist();
        for (Items items : tempdb) {
            tempItemsdb.add(new Items(items.getCode(),items.getName(),items.getUnitPrice(),items.getQuantity()));
        }

        tbl_orders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderDetailTM>() {
            @Override
            public void changed(ObservableValue<? extends OrderDetailTM> observable, OrderDetailTM oldValue, OrderDetailTM selectedItem) {
                if(selectedItem==null){
                    return;
                }
                txt_ItemCode.setText(selectedItem.getItemCode());
                txt_ItemName.setText(selectedItem.getItemName());
                txt_UnitPrice.setText(""+selectedItem.getUnitPrice());
                txt_Quantity.setText(""+selectedItem.getQuantity());

                for (Items items : tempItemsdb) {
                    if(items.getCode().equals(selectedItem.getItemCode())){
                        txt_QuantityOnHand.setText(""+items.getQuantity());
                    }

                }


                Platform.runLater(() -> {
                    txt_Quantity.requestFocus();
                });


                txt_ItemCode.setEditable(false);
                txt_Quantity.setEditable(true);
                btn_Add.setText("Edit Item");
                btn_Remove.setDisable(false);


            }
        });


    }



    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        if(LoginController.loggedUser instanceof SystemUser) {
            Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene dashboardScene = new Scene(dashboard);
            Stage primaryStage = (Stage) orders.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }

        else if(LoginController.loggedUser instanceof AdminUsers){
            Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
            Scene dashboardScene = new Scene(dashboard);
            Stage primaryStage = (Stage) orders.getScene().getWindow();
            primaryStage.setScene(dashboardScene);

        }

        else{
            Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/CustomerDashBoard.fxml"));
            Scene dashboardScene = new Scene(dashboard);
            Stage primaryStage = (Stage) orders.getScene().getWindow();
            primaryStage.setScene(dashboardScene);
        }

    }

    @FXML
    private void btnAdd_OnAction(ActionEvent actionEvent) {
        String itemCode = txt_ItemCode.getText();
        String itemName = txt_ItemName.getText();
        String unitPrice = txt_UnitPrice.getText();
        String customerID = txt_CustomerID.getText();
        String quantity = txt_Quantity.getText();
        String quantityOnHand = txt_QuantityOnHand.getText();



        if(ManageItems.searchItem(itemCode)==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Item code", ButtonType.OK).show();
            txt_ItemCode.setText("");
            txt_ItemCode.requestFocus();
            return;
        }


        if(ManageCustomers.Seacrh_Cusotmers(customerID)==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer ID", ButtonType.OK).show();
            txt_CustomerID.setText("");
            txt_CustomerID.requestFocus();
            return;
        }

        if(quantity.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Quantity cannot be empty", ButtonType.OK).show();
            txt_Quantity.setText("");
            txt_Quantity.requestFocus();
            return;
        }
        if(Isint(quantity)) {
            if (Integer.parseInt(quantity) == 0) {
                new Alert(Alert.AlertType.ERROR, "Quantity cannot be Zero", ButtonType.OK).show();
                txt_Quantity.setText("");
                txt_Quantity.requestFocus();
                return;
            }


            if (Integer.parseInt(quantity) < 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid Quantity", ButtonType.OK).show();
                txt_Quantity.setText("");
                txt_Quantity.requestFocus();
                return;
            }

            if(Integer.parseInt(quantityOnHand)<Integer.parseInt(quantity)){
                new Alert(Alert.AlertType.ERROR, "Invalid Quantity", ButtonType.OK).show();
                txt_Quantity.setText("");
                txt_Quantity.requestFocus();
                return;
            }
        }

        if(!Isint(quantity)){
            new Alert(Alert.AlertType.ERROR,"Please Enter only numbers for quantity", ButtonType.OK).show();
            txt_Quantity.setText("");
            txt_Quantity.requestFocus();
            return;

        }

        Double total=Integer.parseInt(quantity)*Double.parseDouble(unitPrice);

        if(tbl_orders.getSelectionModel().isEmpty()){

            //New
            OrderDetailTM itemExist = isItemExist(itemCode);
            if(isItemExist(itemCode)==null){
                OrderDetailTM orderDetailTM=new OrderDetailTM(itemCode,itemName,Double.parseDouble(unitPrice),Integer.parseInt(quantity),total);

                tbl_orders.getItems().add(orderDetailTM);

                settempQuantity(itemCode,quantity);

                btn_PlaceOrder.setDisable(false);
                txt_ItemName.setText("");
                txt_ItemCode.setText("");
                txt_QuantityOnHand.setText("");
                txt_UnitPrice.setText("");
                txt_Quantity.setText("");
                txt_ItemCode.requestFocus();



                calculateTotal();



           }
           else{

                itemExist.setQuantity(itemExist.getQuantity()+Integer.parseInt(quantity));
                itemExist.setTotal(itemExist.getQuantity()*itemExist.getUnitPrice());
                tbl_orders.refresh();
                txt_ItemName.setText("");
                txt_ItemCode.setText("");
                txt_QuantityOnHand.setText("");
                txt_UnitPrice.setText("");
                txt_Quantity.setText("");
                txt_ItemCode.requestFocus();


            }

        }
        else{

            //Edit
            OrderDetailTM selectedItem = tbl_orders.getSelectionModel().getSelectedItem();
            selectedItem.setQuantity(Integer.parseInt(quantity));
            selectedItem.setTotal(selectedItem.getQuantity()*selectedItem.getUnitPrice());
            tbl_orders.refresh();
            txt_ItemCode.setText("");
            txt_ItemName.setText("");
            txt_QuantityOnHand.setText("");
            txt_UnitPrice.setText("");
            txt_Quantity.setText("");
            txt_ItemCode.setEditable(true);
            txt_ItemCode.requestFocus();
            btn_Add.setText("Add Item");
            tbl_orders.getSelectionModel().clearSelection();
            synchronizeQuantity(selectedItem.getItemCode(),quantity);


        }

        calculateTotal();
    }



    @FXML
    private void btnRemove_OnAction(ActionEvent actionEvent) {
        OrderDetailTM selectedItem = tbl_orders.getSelectionModel().getSelectedItem();
       if(selectedItem==null){
           new Alert(Alert.AlertType.ERROR,"No item selected",ButtonType.OK).show();
           return;
       }

        quantitySetBack(selectedItem);
        tbl_orders.getItems().remove(selectedItem);
        calculateTotal();

        txt_ItemCode.setText("");
        txt_ItemName.setText("");
        txt_QuantityOnHand.setText("");
        txt_UnitPrice.setText("");
        txt_Quantity.setText("");
        txt_ItemCode.setEditable(true);


        Platform.runLater(() -> {
            txt_ItemCode.requestFocus();
        });




        btn_Add.setText("Add Item");

        tbl_orders.getSelectionModel().clearSelection();

        ObservableList<OrderDetailTM> items = tbl_orders.getItems();

        if(items.size()==0){
            btn_Remove.setDisable(true);
            btn_PlaceOrder.setDisable(true);
        }

    }

    @FXML
    private void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        String customerId = txt_CustomerID.getText();
        if(customerId.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Without a customer id cannot place a order",ButtonType.OK).show();
            txt_CustomerID.requestFocus();
            return;
        }

        ObservableList<OrderDetailTM> items = tbl_orders.getItems();
        ArrayList<OrderDetail>orderDetails=new ArrayList<>();
        for (OrderDetailTM item : items) {
            orderDetails.add(new OrderDetail(item.getItemCode(),item.getItemName(),item.getQuantity(),item.getUnitPrice()));
        }

        ManageOrders.createOrder(txt_OrderID.getText(), LocalDate.parse(txt_OrderDate.getText()),customerId,orderDetails);

        txt_CustomerID.setText("");
        txt_CustomerName.setText("");
        txt_ItemCode.setText("");
        txt_ItemName.setText("");
        txt_QuantityOnHand.setText("");
        txt_UnitPrice.setText("");
        txt_Quantity.setText("");
        txt_OrderID.setText(ManageOrders.generateOrderId());
        txt_CustomerID.requestFocus();

        tbl_orders.getItems().removeAll(tbl_orders.getItems());

        new Alert(Alert.AlertType.INFORMATION,"Your Order has been placed successfully",ButtonType.OK).show();

    }

    @FXML
    private void txtCustomerID_onAction(ActionEvent actionEvent) {
        String customerId = txt_CustomerID.getText();
        if(customerId.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Customer ID cannot be empty",ButtonType.OK).show();
            return;
        }

        Customers customers = ManageCustomers.Seacrh_Cusotmers(customerId);


        if(customers==null) {
            new Alert(Alert.AlertType.ERROR,"Invalid Customer ID", ButtonType.OK).show();
            txt_CustomerID.setText("");
            txt_CustomerName.setText("");
            txt_ItemCode.setText("");
            txt_ItemName.setText("");
            txt_QuantityOnHand.setText("");
            txt_UnitPrice.setText("");
            txt_Quantity.setText("");
            txt_CustomerID.requestFocus();
            return;

        }
        else {
            txt_CustomerName.setText(customers.getName());
            txt_ItemCode.requestFocus();
        }


    }
    @FXML
    private void txtItemCode_onAction(ActionEvent actionEvent) {
        String itemCode = txt_ItemCode.getText();
        String customerId = txt_CustomerID.getText();
        if(customerId.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please Enter Customer ID first",ButtonType.OK).show();
            txt_ItemCode.setText("");
            txt_CustomerID.requestFocus();
            return;

        }

        if(itemCode.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Item code cannot be empty",ButtonType.OK).show();
            return;
        }

        Items items = ManageItems.searchItem(itemCode);

        if(items==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Item code", ButtonType.OK).show();
            txt_ItemCode.setText("");
            txt_ItemName.setText("");
            txt_QuantityOnHand.setText("");
            txt_UnitPrice.setText("");
            txt_Quantity.setText("");
            txt_ItemCode.requestFocus();

            return;
        }
        else{
            txt_ItemName.setText(items.getName());

            for (Items items1 : tempItemsdb) {
                if(items1.getCode().equals(itemCode)) {
                    txt_QuantityOnHand.setText(""+items1.getQuantity());
                }
            }

            txt_UnitPrice.setText(items.getUnitPrice()+"");
            txt_Quantity.requestFocus();
        }




    }
    private boolean Isint(String quantity){
                try {
                    Integer.parseInt(quantity);
                    return true;
                }
                catch (Exception ex){
                    return false;

                }
    }


    private OrderDetailTM isItemExist(String itemCode){
        ObservableList<OrderDetailTM> items = tbl_orders.getItems();
        for (OrderDetailTM item : items) {
            if(item.getItemCode().equals(itemCode)){
                return item;
            }

        }
        return null;
    }

    private void calculateTotal(){
        ObservableList<OrderDetailTM> items = tbl_orders.getItems();
        if(items.size()==0){
            lbl_total.setText("0.00");
        }
        double total=0;
        for (OrderDetailTM item : items) {
            total+=item.getTotal();
            lbl_total.setText(""+total);
        }
    }


    private void settempQuantity(String itemCode,String quantity){
        for (Items items : tempItemsdb) {
            if(items.getCode().equals(itemCode)){
                items.setQuantity(items.getQuantity()-Integer.parseInt(quantity));
            }
        }

    }

    private  void synchronizeQuantity(String itemCode,String quantity){
        int quantityOnHand = ManageItems.searchItem(itemCode).getQuantity();
        for (Items items : tempItemsdb) {
            if(items.getCode().equals(itemCode)){
                items.setQuantity(quantityOnHand-Integer.parseInt(quantity));
            }

        }

    }

    private  void quantitySetBack(OrderDetailTM selectedItem){
        for (Items items : tempItemsdb) {
            if(items.getCode().equals(selectedItem.getItemCode())){
                items.setQuantity(items.getQuantity()+selectedItem.getQuantity());
            }
        }

    }
}
