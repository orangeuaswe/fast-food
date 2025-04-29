package com.example.cs213project4.model;
public enum Bread
{
    BRIOCHE(0.0),
    WHEAT(0.0),
    PRETZEL(0.0),
    BAGEL(0.0),
    SOURDOUGH(0.0);

    private final double cost;
    Bread(double cost)
    {
        this.cost = cost;
    }
    public double getCost()
    {
        return cost;
    }
    @Override
    public String toString()
	{
		return name().charAt(0)+name().substring(1).toLowerCase();
	}
}