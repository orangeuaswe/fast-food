package com.example.cs213project4.model;

public abstract class MenuItem 
{
	protected int quantity; 

	public abstract double cost(); 
	public int getQuantity()
	{
		return quantity; 
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity; 
	}
	public abstract String toString(); 
	

}
