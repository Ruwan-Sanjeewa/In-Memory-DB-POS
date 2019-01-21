package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.ManageItems;
import util.Items;
import view.util.ItemsTM;

import java.io.IOException;
import java.util.ArrayList;

public class ShowItemsController {

    public TableView<ItemsTM> tbl_Items;
    @FXML
    private AnchorPane manageItems;






    public void initialize() {


        tbl_Items.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tbl_Items.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_Items.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tbl_Items.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ArrayList<Items> itemsArraylist= ManageItems.getItemsArraylist();
        ObservableList<Items> itemsObservablelist= FXCollections.observableArrayList(itemsArraylist);
        ObservableList<ItemsTM>tblList=FXCollections.observableArrayList();

        for (Items items1 : itemsObservablelist) {
            tblList.add(new ItemsTM(items1.getCode(),items1.getName(),items1.getUnitPrice(),items1.getQuantity()));
        }

        tbl_Items.setItems(tblList);


    }


    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashboard = FXMLLoader.load(this.getClass().getResource("/view/CustomerDashBoard.fxml"));
        Scene dashboardScene = new Scene(dashboard);
        Stage primaryStage = (Stage) manageItems.getScene().getWindow();
        primaryStage.setScene(dashboardScene);
    }







}