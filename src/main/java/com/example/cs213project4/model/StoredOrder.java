package com.example.cs213project4.model;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class StoredOrder 
{
	private ArrayList<Order> listOrders; 
	private int nextOrderNum;
    private Order order;
	public StoredOrder()
	{
		this.listOrders = new ArrayList<>(); 
		this.nextOrderNum = 1; 
	}
    public Order createNewOrder()
    {
        order = new Order(nextOrderNum++);
        return order;
    }
    public void addOrder(Order order)
    {
        listOrders.add(order);
    }
    public ArrayList<Order> getOrders()
    {
        return listOrders;
    }
    public Order findOrder(int orderNum)
    {
        for(Order order : listOrders)
        {
            if(order.getNumber() == orderNum)
            {
                return order;
            }
        }
        return null;
    }
    public boolean cancelOrder(int orderNum)
    {
        Order order = findOrder(orderNum);
        if(order != null)
        {
            return listOrders.remove(order);
        }
        return false;
    }
    public boolean exportOrders(String filePath) {
        try (PrintWriter write = new PrintWriter(new FileWriter(filePath))) {
            for (Order order : listOrders) {
                write.println("Order #" + order.getNumber());
                write.println("----------------------------------------");

                ArrayList<MenuItem> items = order.getItems();
                if (items.isEmpty()) {
                    write.println("No items in order.");
                } else {
                    for (int i = 0; i < items.size(); i++) {
                        write.println((i + 1) + ". " + items.get(i));
                    }
                }

                write.println("Subtotal: $" + String.format("%.2f", order.getTotalCost()));
                write.println("Tax: $" + String.format("%.2f", order.getTax()));
                write.println("Total: $" + String.format("%.2f", order.getTotal()));
                write.println("========================================");
                write.println();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
