package edu.miracosta.cs113;

import javax.swing.ImageIcon;

public abstract class Entity
{
	private int rowPosition;
	private int columnPosition;
	private String name;
	
	public Entity()
	{
		name = "UNDEFINED";
		rowPosition = 1;
		columnPosition = 1;
	}
	public Entity(String name, int rowTarget, int columnTarget)
	{
		this.name = name;
		rowPosition = rowTarget;
		columnPosition = columnTarget;
	}
	public void setPosition(int row, int column)
	{
		rowPosition = row;
		columnPosition = column;
	}
	public int getRow()
	{
		return rowPosition;
	}
	public int getColumn()
	{
		return columnPosition;
	}
}
