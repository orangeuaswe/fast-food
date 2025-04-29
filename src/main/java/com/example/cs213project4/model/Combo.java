package com.example.cs213project4.model;

public class Combo extends MenuItem
{
	private Sandwich earlOfSandwich;
	private Beverage bigGulp; 
	private Side side; 

	public Combo() 
	{
		this.quantity = 1; 
		this.earlOfSandwich = new Sandwich(); 
		this.bigGulp = new Beverage(Size.MEDIUM, Flavor.COLA);
		this.side = new Side(Size.SMALL, SideOption.CHIPS);
	}
	public Combo(Sandwich sandwich, Beverage drink, Side side)
	{
		this.quantity = 1; 
		this.earlOfSandwich = sandwich; 
		this.bigGulp = drink;
		this.side = side; 
		bigGulp.setSize(Size.MEDIUM);
		side.setSize(Size.SMALL);
	}
	public Sandwich getSandwich()
	{
		return earlOfSandwich;
	}
	public Beverage getDrink()
	{
		return bigGulp;
	}
	public void setDrink(Beverage drink)
	{
		this.bigGulp = drink; 
		bigGulp.setSize(Size.MEDIUM);
	}
	public Side getSide()
	{
		return side; 
	}
	public void setSide(Side side)
	{
		this.side = side; 
		side.setSize(Size.SMALL);
	}
	public double cost()
	{
		double comboCost = earlOfSandwich.cost()+2.00; 
		return comboCost * quantity; 
	}
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();

		string.append("Combo: ").append(earlOfSandwich.toString().split(" \\$")[0]);
		string.append(", ").append(side.getSide());
		string.append(", ").append(bigGulp.getFlavor());

		if (quantity > 1) {
			string.append(" (").append(quantity).append(")");
		}

		string.append(" $").append(String.format("%.2f", cost()));

		return string.toString();
	}

}
