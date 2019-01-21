package util;

public class OrderDetail {
    private String itemCode;
    private String name;
    private int quantity;
    private double unitPrice;



    public OrderDetail() {
    }

    public OrderDetail(String itemCode, String name, int quantity, double unitPrice) {
        this.setItemCode(itemCode);
        this.setName(name);
        this.setQuantity(quantity);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
