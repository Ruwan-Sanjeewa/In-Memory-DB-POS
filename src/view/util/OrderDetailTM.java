package view.util;

public class OrderDetailTM {
    private String itemCode;
    private String itemName;
    private double unitPrice;
    private int quantity;
    private double total;

    public OrderDetailTM() {
    }

    public OrderDetailTM(String itemCode, String itemName, double unitPrice, int quantity,double total) {
        this.setItemCode(itemCode);
        this.setItemName(itemName);
        this.setUnitPrice(unitPrice);
        this.setQuantity(quantity);
        this.setTotal(total);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
