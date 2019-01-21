package model;


import util.Customers;
import util.Items;

import java.util.ArrayList;

public class ManageItems {
    private static ArrayList<Items> itemsArraylist=new ArrayList<>();

    static{
        itemsArraylist.add(new Items("1","book",10,100));
        itemsArraylist.add(new Items("2","pencil",50,50));
        itemsArraylist.add(new Items("3","pen",30,200));
    }

    public static ArrayList<Items> getItemsArraylist() {
        return itemsArraylist;
    }

    public static void setItemsArraylist(ArrayList<Items> itemsArraylist) {
        ManageItems.itemsArraylist = itemsArraylist;
    }

    public static void Add_items(Items items){
        getItemsArraylist().add(items);

    }

    public static void Edit_items(String code,String name,String unitPrice,String quantity){
        for (Items items : itemsArraylist) {
            if(items.getCode().equals(code)){
                items.setName(name);
                items.setUnitPrice(Double.parseDouble(unitPrice));
                items.setQuantity(Integer.parseInt(quantity));

            }
        }

    }

    public static void Delete_items(int selectedindex){
        itemsArraylist.remove(selectedindex);
    }

    public static Items searchItem(String itemCode){
        for (Items items : itemsArraylist) {
            if(items.getCode().equals(itemCode)){
                return items;

            }
        }
        return null;
    }
}
