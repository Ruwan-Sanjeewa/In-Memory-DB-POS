package view.util;

public class CustomersTM {
    private String id;
    private String name;
    private String Address;

    public CustomersTM() {
    }

    public CustomersTM(String id, String name, String address) {
        this.setId(id);
        this.setName(name);
        setAddress(address);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "CustomersTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
