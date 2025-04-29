package com.example.cs213project4.model;

public class Beverage extends MenuItem
{
	private Size size; 
	private Flavor taste; 

	public Beverage() 
	{
		this.quantity = 1; 
		this.size = size; 
		this.taste = Flavor.COLA;
	}
	public Beverage(Size size, Flavor flavor)
	{
		this.quantity = 1; 
		this.size = size; 
		this.taste = flavor; 
	}
	public Size getSize()
	{
		return size; 
	}
	public void setSize(Size size)
	{
		this.size = size; 
	}
	public Flavor getFlavor()
	{
		return taste; 
	}
	public void setFlavor(Flavor flavor)
	{
		this.taste = flavor;
	}
	public double cost()
	{
		double baseCost = 1.99; //cost for a small 
		if(size == size.MEDIUM)
		{
			baseCost = 2.49; 
		}
		else if(size == Size.LARGE)
		{
			baseCost = 2.99;
		}
		return baseCost*quantity; 
	}
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		string.append(size).append(" ").append(taste);

		if (quantity > 1) {
			string.append(" (").append(quantity).append(")");
		}

		string.append(" $").append(String.format("%.2f", cost()));

		return string.toString();
	}

}
