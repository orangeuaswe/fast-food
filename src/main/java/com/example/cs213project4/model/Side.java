package com.example.cs213project4.model;

public class Side extends MenuItem
{
	private Size size; 
	private SideOption side; 

	public Side() 
	{
		this.quantity = 1; 
		this.size = Size.SMALL;
		this.side = SideOption.CHIPS;
	}
	public Side(Size size, SideOption side)
	{
		this.quantity = 1; 
		this.size = size; 
		this.side = side; 
	}
	public Size getSize()
	{
		return size; 
	}
	public void setSize(Size size) 
	{
		this.size = size; 
	}
	public SideOption getSide()
	{
		return side; 
	}
	public void setSide(SideOption side)
	{
		this.side = side; 
	}
	public double cost()
	{
		double baseCost = side.getBaseCost(); 
		if(size == Size.MEDIUM)
		{
			baseCost+=0.50; 
		}
		if(size == Size.LARGE)
		{
			baseCost+=1.50; 
		}
		return baseCost*quantity; 
	}
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		string.append(size).append(" ").append(side);

		if (quantity > 1) {
			string.append(" (").append(quantity).append(")");
		}

		string.append(" $").append(String.format("%.2f", cost()));

		return string.toString();
	}

}
