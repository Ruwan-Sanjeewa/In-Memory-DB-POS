package util;

public class Items {
    private String code;
    private String name;
    private double unitPrice;
    private int quantity;

    public Items() {
    }

    public Items(String code, String name, double unitPrice, int quantity) {
        this.setCode(code);
        this.setName(name);
        this.setUnitPrice(unitPrice);
        this.setQuantity(quantity);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Items{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
