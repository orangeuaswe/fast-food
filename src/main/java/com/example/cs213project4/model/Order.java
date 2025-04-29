package com.example.cs213project4.model;
import java.util.ArrayList;
public class Order 
{
	private int orderNum; 
	private ArrayList<MenuItem> items; 
	private static final double TAX = 0.06625;
	
	public Order(int num)
	{
		this.orderNum = num; 
		this.items = new ArrayList<>(); 
	}
	public int getNumber() {
		return orderNum; 
	}
	public ArrayList<MenuItem> getItems()
	{
		return items; 
	}
	public void addItem(MenuItem item)
	{
		items.add(item);
	}
	public boolean eradicateItem(MenuItem item)
	{
		return items.remove(item);
		
	}
	public void removeItem(MenuItem loc)
	{
		items.remove(loc);
	}
	public void eradicateAllItems()
	{
		items.clear();
	}
	public double getTotalCost()
	{
		double totalCost = 0.0; 
		for(MenuItem item : items)
		{
			totalCost+=item.cost(); 
		}
		return totalCost;
	}
	public double getTax()
	{
		return getTotalCost()*TAX;
	}
	public double getTotal()
	{
		return getTotalCost()+getTax();
	}
	/*
	 * Insert toString here
	 */
}
