package com.example.cs213project4.model;
public enum Size 
{
    SMALL(0.0),
    MEDIUM(0.50),
    LARGE(1.00);
	private final double extraCost; 
	Size(double extraCost)
	{
		this.extraCost = extraCost;
	}
	public double getExtraCost()
	{
		return extraCost;
	}
	@Override
	public String toString()
	{
		return name().charAt(0)+name().substring(1).toLowerCase();
	}
    
}
