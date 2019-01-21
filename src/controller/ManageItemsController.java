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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ManageCustomers;
import model.ManageItems;
import util.Customers;
import util.Items;
import view.util.CustomersTM;
import view.util.ItemsTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ManageItemsController {
    @FXML
    private JFXButton btn_Save;
    @FXML
    private JFXButton btn_Delete;
    @FXML
    private JFXTextField txt_ItemCode;
    @FXML
    private JFXTextField txt_ItemName;
    @FXML
    private JFXTextField txt_UnitPrice;
    @FXML
    private JFXTextField txt_Quantity;


    @FXML
    private TableView <ItemsTM>tbl_Items;
    @FXML
    private AnchorPane items;


    public void initialize(){



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



        tbl_Items.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemsTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemsTM> observable, ItemsTM oldValue, ItemsTM selectedItem) {
                if(selectedItem==null){
                    return;
                }

                txt_ItemCode.setText(selectedItem.getCode());
                txt_ItemName.setText(selectedItem.getName());
                txt_UnitPrice.setText(""+selectedItem.getUnitPrice());
                txt_Quantity.setText(""+selectedItem.getQuantity());
                txt_ItemCode.setDisable(true);
                btn_Save.setDisable(false);
                btn_Delete.setDisable(false);

                btn_Save.setText("Edit");
            }
        });

        ObservableList<ItemsTM> items = tbl_Items.getItems();
        if(items.size()==0) {
            btn_Delete.setDisable(true);
        }
    }



    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashBoard= FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene dashBoardScene=new Scene(dashBoard);
        Stage primaryStage= (Stage) items.getScene().getWindow();
        primaryStage.setScene(dashBoardScene);
    }

    @FXML
    private void btnSave_OnAction(ActionEvent actionEvent) {
        String code=txt_ItemCode.getText();
        String name=txt_ItemName.getText();
        String unitPrice=txt_UnitPrice.getText();
        String quantity = txt_Quantity.getText();


        if(code.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Item Code cannot be empty", ButtonType.OK).show();
            tbl_Items.requestFocus();
            return;
        }

        if(name.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Item Name cannot be empty", ButtonType.OK).show();
            txt_ItemName.requestFocus();
            return;
        }

        if(unitPrice.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Item Unit price cannot be empty", ButtonType.OK).show();
            txt_UnitPrice.requestFocus();
            return;
        }

        if(quantity.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Item Quantity cannot be empty", ButtonType.OK).show();
            txt_Quantity.requestFocus();
            return;
        }

        if(!isDouble(txt_UnitPrice.getText()) || Double.parseDouble(txt_UnitPrice.getText())<0){
            new Alert(Alert.AlertType.ERROR,"Only numbers can input for unit price", ButtonType.OK).show();
            txt_UnitPrice.requestFocus();
            return;
        }

        if(!isInt(txt_Quantity.getText()) || Double.parseDouble(txt_Quantity.getText())<0){
            new Alert(Alert.AlertType.ERROR,"Only numbers can input for quantity", ButtonType.OK).show();
            txt_Quantity.requestFocus();
            return;
        }

        if(tbl_Items.getSelectionModel().isEmpty()) {
            ObservableList<ItemsTM> tblItems = tbl_Items.getItems();

            for (ItemsTM tblItem : tblItems) {
                if (tblItem.getCode().equals(code)) {
                    new Alert(Alert.AlertType.ERROR, "Item Code is already exist", ButtonType.OK).show();
                    txt_ItemCode.setText("");
                    txt_ItemCode.requestFocus();
                    return;

                }
            }


            Items items = new Items(code, name, Double.parseDouble(unitPrice),Integer.parseInt(quantity));
            ManageItems.Add_items(items);

            ItemsTM itemsTM= new ItemsTM(code, name, Double.parseDouble(unitPrice),Integer.parseInt(quantity));
            tbl_Items.getItems().add(itemsTM);

            txt_ItemCode.setText("");

            txt_ItemCode.requestFocus();
            txt_ItemName.setText("");
            txt_UnitPrice.setText("");
            txt_Quantity.setText("");
            new Alert(Alert.AlertType.CONFIRMATION,"Item has been saved successfully",ButtonType.OK).show();
        }

        else{


            ItemsTM selectedItem = tbl_Items.getSelectionModel().getSelectedItem();

            selectedItem.setName(name);
            selectedItem.setUnitPrice(Double.parseDouble(unitPrice));
            selectedItem.setQuantity(Integer.parseInt(quantity));
            tbl_Items.refresh();

            ManageItems.Edit_items(code,name,unitPrice,quantity);

            btn_Save.setText("Save");
            txt_ItemCode.setText("");
            txt_ItemCode.setDisable(false);
            txt_ItemCode.requestFocus();
            txt_ItemName.setText("");
            txt_UnitPrice.setText("");
            txt_Quantity.setText("");
            tbl_Items.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION,"Item has been updated successfully",ButtonType.OK).show();
        }

    }

    @FXML
    private void btnDelete_OnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            int selectedIndex = tbl_Items.getSelectionModel().getSelectedIndex();
            ManageItems.Delete_items(selectedIndex);
            tbl_Items.getItems().remove(selectedIndex);
        }

        txt_ItemCode.setText("");
        txt_ItemName.setText("");
        txt_UnitPrice.setText("");
        txt_Quantity.setText("");

        txt_ItemCode.setDisable(false);
        tbl_Items.getSelectionModel().clearSelection();
        btn_Save.setText("Save");
        btn_Delete.setDisable(true);

        ObservableList<ItemsTM> items = tbl_Items.getItems();
        if(items.size()==0){
            btn_Delete.setDisable(false);
        }

        Platform.runLater(() -> {
            txt_ItemCode.requestFocus();
        });


    }




    private boolean isDouble(String number){
        try{
            Double.parseDouble(number);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }

    private boolean isInt(String number){
        try{
            Integer.parseInt(number);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }


}
