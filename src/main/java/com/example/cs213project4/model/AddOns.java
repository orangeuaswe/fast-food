package com.example.cs213project4.model;
public enum AddOns
{
    LETTUCE(0.30),
    TOMATOES(0.30),
    ONIONS(0.30),
    AVOCADO(0.50),
    CHEESE(1.00);

    private final double cost;
    AddOns(double cost)
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
		String name = name().replace('_',' ');
		String[] word = name.split(" ");
		StringBuilder solution = new StringBuilder();
		for(String words: word)
		{
			solution.append(words.charAt(0)).append(words.substring(1).toLowerCase()).append(" ");
		}
		return solution.toString().trim();
	}
}