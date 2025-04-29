package com.example.cs213project4.model;
public enum SideOption 
{
    CHIPS(1.99),
    FRIES(2.49),
    ONION_RINGS(3.29),
    APPLE_SLICES(1.29);
	private final double baseCost; 
	SideOption(double baseCost)
	{
		this.baseCost = baseCost;
	}
	public double getBaseCost()
	{
		return baseCost;
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
