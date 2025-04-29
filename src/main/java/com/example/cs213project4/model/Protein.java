package com.example.cs213project4.model;
public enum Protein
{
    ROAST_BEEF(10.99),
    SALMON(9.99),
    CHICKEN(8.99),
    BEEF_PATTY(6.99);
    private final double cost;
    Protein(double cost)
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