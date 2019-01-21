package view.util;

import java.time.LocalDate;

public class ViewOrderTM {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private String customerName;

    public ViewOrderTM() {
    }

    public ViewOrderTM(String orderId, LocalDate date, String customerId, String customerName) {
        this.setOrderId(orderId);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "ViewOrderTM{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
