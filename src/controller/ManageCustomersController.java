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
import util.Customers;
import view.util.CustomersTM;
import view.util.ItemsTM;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ManageCustomersController {


    @FXML
    private JFXButton btn_Delete;
    @FXML
    private JFXButton btn_Save;
    @FXML
    private TableView<CustomersTM> tbl_Customers;
    @FXML
    private JFXTextField txt_CustomerId;
    @FXML
    private JFXTextField txt_CustomerName;
    @FXML
    private JFXTextField txt_CustomerAddress;
    @FXML
    private AnchorPane customers;



    public void initialize(){


        tbl_Customers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_Customers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_Customers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Address"));

        ArrayList<Customers>cusotmersArraylist=ManageCustomers.getCusotmersArraylist();
        ObservableList<Customers> customersObservablelist=FXCollections.observableArrayList(cusotmersArraylist);
        ObservableList<CustomersTM>tblList=FXCollections.observableArrayList();

        for (Customers customers1 : customersObservablelist) {
            tblList.add(new CustomersTM(customers1.getId(),customers1.getName(),customers1.getAddress()));
        }

        tbl_Customers.setItems(tblList);



        tbl_Customers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomersTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomersTM> observable, CustomersTM oldValue, CustomersTM selectedItem) {
                if(selectedItem==null){
                    return;
                }

                txt_CustomerId.setText(selectedItem.getId());
                txt_CustomerName.setText(selectedItem.getName());
                txt_CustomerAddress.setText(selectedItem.getAddress());
                txt_CustomerId.setDisable(true);
                btn_Save.setDisable(false);
                btn_Delete.setDisable(false);

                btn_Save.setText("Edit");
            }
        });

        ObservableList<CustomersTM> items = tbl_Customers.getItems();
        if(items.size()==0) {
            btn_Delete.setDisable(true);
        }


    }

    @FXML
    private void btnBack_OnAction(ActionEvent actionEvent) throws IOException {
        Parent dashBoard= FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene dashBoardScene=new Scene(dashBoard);
        Stage primaryStage= (Stage) customers.getScene().getWindow();
        primaryStage.setScene(dashBoardScene);

    }


    @FXML
    private void btnSave_OnAction(ActionEvent actionEvent) {

         String id=txt_CustomerId.getText();
         String name=txt_CustomerName.getText();
         String address=txt_CustomerAddress.getText();

         if(id.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Customer ID cannot be empty", ButtonType.OK).show();
            txt_CustomerId.requestFocus();
            return;
        }

        if(name.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Customer Name cannot be empty", ButtonType.OK).show();
            txt_CustomerName.requestFocus();
            return;
        }

        if(address.trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Customer Address cannot be empty", ButtonType.OK).show();
            txt_CustomerAddress.requestFocus();
            return;
        }


        if(tbl_Customers.getSelectionModel().isEmpty()) {
            ObservableList<CustomersTM> tblItems = tbl_Customers.getItems();

            for (CustomersTM tblItem : tblItems) {
                if (tblItem.getId().equals(id)) {
                    new Alert(Alert.AlertType.ERROR, "Customer ID is already exist", ButtonType.OK).show();
                    txt_CustomerId.setText("");
                    txt_CustomerId.requestFocus();
                    return;

                }
            }


            Customers customers = new Customers(id, name, address);
            ManageCustomers.Add_customers(customers);

            CustomersTM customersTM = new CustomersTM(id, name, address);
            tbl_Customers.getItems().add(customersTM);


            new Alert(Alert.AlertType.CONFIRMATION,"Customer has been saved successfully",ButtonType.OK).show();
        }

        else{


            CustomersTM selectedItem = tbl_Customers.getSelectionModel().getSelectedItem();

            selectedItem.setName(name);
            selectedItem.setAddress(address);
            tbl_Customers.refresh();

            ManageCustomers.Edit_Customers(id,name,address);

            txt_CustomerId.setDisable(false);
            txt_CustomerId.setText("");
            txt_CustomerId.requestFocus();
            txt_CustomerName.setText("");
            txt_CustomerAddress.setText("");
            btn_Save.setText("Save");
            tbl_Customers.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION,"Customer has been updated successfully",ButtonType.OK).show();
        }

    }



    @FXML
    private void btnDelete_OnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                int selectedIndex = tbl_Customers.getSelectionModel().getSelectedIndex();
                ManageCustomers.Delete_Customers(selectedIndex);
                tbl_Customers.getItems().remove(selectedIndex);
            }

            txt_CustomerId.setText("");
            txt_CustomerName.setText("");
            txt_CustomerAddress.setText("");

            txt_CustomerId.setDisable(false);
            tbl_Customers.getSelectionModel().clearSelection();
            btn_Save.setText("Save");

        ObservableList<CustomersTM> items = tbl_Customers.getItems();
        if(items.size()==0) {
            btn_Delete.setDisable(true);
        }

        Platform.runLater(() -> {
            txt_CustomerId.requestFocus();
        });


    }




//    @FXML
//    private void btnPrint_OnAction(ActionEvent actionEvent) throws JRException {
//        File file = new File("Reports/CustomerDetails.jasper");
//        JasperReport compiledReport= (JasperReport) JRLoader.loadObject(file);
//        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Customer ID", "Customer Name", "Address"}, 0);
//
//        ObservableList<CustomersTM> customers = tbl_Customers.getItems();
//
//        for (CustomersTM customer : customers) {
//            Object rawData []={customer.getId(),customer.getName(),customer.getAddress()};
//            dtm.addRow(rawData);
//        }
//        JasperPrint fillReport = JasperFillManager.fillReport(compiledReport, new HashMap<>(), new JRTableModelDataSource(dtm));
//        JasperViewer.viewReport(fillReport,false);
//
//    }




}
