package model;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import util.Customers;

import java.util.ArrayList;

public class ManageCustomers {
    private static ArrayList<Customers> cusotmersArraylist=new ArrayList<>();

    static{
        cusotmersArraylist.add(new Customers("1","Kasun","Galle"));
        cusotmersArraylist.add(new Customers("2","Nuwan","Galle"));
        cusotmersArraylist.add(new Customers("3","Ranga","Galle"));
    }



    public static ArrayList<Customers> getCusotmersArraylist() {
        return cusotmersArraylist;
    }

    public static void setCusotmersArraylist(ArrayList<Customers> cusotmersArraylist) {
        ManageCustomers.cusotmersArraylist = cusotmersArraylist;
    }

    public static void Add_customers(Customers customers){
        getCusotmersArraylist().add(customers);

    }

    public  static Customers Seacrh_Cusotmers(String customerId){
        for (Customers customers : cusotmersArraylist) {
            if(customers.getId().equals(customerId)){
                return customers;
            }
        }
        return null;
    }

    public static void Edit_Customers(String id,String name,String address){
        for (Customers customers : cusotmersArraylist) {
            if(customers.getId().equals(id)){
                customers.setName(name);
                customers.setAddress(address);
                System.out.println(customers.getName());
                System.out.printf(customers.getAddress());
            }
        }

    }

    public static void Delete_Customers(int selectedindex){
        cusotmersArraylist.remove(selectedindex);


    }



}
