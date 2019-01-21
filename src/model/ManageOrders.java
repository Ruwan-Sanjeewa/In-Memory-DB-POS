package model;

import util.Order;
import util.OrderDetail;

import java.time.LocalDate;
import java.util.ArrayList;

public class ManageOrders {
     private static ArrayList<Order>orders=new ArrayList<>();

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    public static void setOrders(ArrayList<Order> orders) {
        ManageOrders.orders = orders;
    }

    public static String generateOrderId(){
        return getOrders().size()+1+"";

    }

    public static void createOrder(String orderID, LocalDate date, String customerId, ArrayList<OrderDetail>orderDetails){
        orders.add(new Order(orderID,date,customerId,orderDetails));


    }

}
