package com.example.cs213project4.model;

public class Burger extends Sandwich 
{
	private boolean doubleOrNothing; //boolean if person wants double patty

	public Burger() 
	{
		super(Bread.BRIOCHE, Protein.BEEF_PATTY);
		this.doubleOrNothing = false; 
		
	}
	public Burger(Bread toast, boolean DON)
	{
		super(toast, Protein.BEEF_PATTY);
		this.doubleOrNothing = DON;
	}
	public boolean isDoublePatty()
	{
		return doubleOrNothing; 
	}
	public void setDoublePatty(boolean DON)
	{
		this.doubleOrNothing = DON; 
	}
	public double cost()
	{
		double baseCost = super.cost(); 
		if(doubleOrNothing)
		{
			baseCost+=2.50; 
		}
		return baseCost;
	}
	/*
	 * Insert toString method here 
	 */

}
