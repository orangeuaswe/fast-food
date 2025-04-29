package com.example.cs213project4.model;
public enum Flavor 
{
    COLA,
    DIET_COLA,
    LEMON_LIME,
    ROOT_BEER,
    ORANGE_JUICE,
    GRAPE,
    STRAWBERRY,
    CHERRY,
    LEMONADE,
    ICED_TEA,
    GREEN_TEA,
    PEACH_TEA,
    COFFEE,
    CHOCOLATE_MILK,
    WATER;
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
