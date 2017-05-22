package edu.miracosta.cs113;

import javax.swing.ImageIcon;

public abstract class Entity
{
	private int rowPosition;
	private int columnPosition;
	private String name;
	
	/** Default constructor
	 * 
	 */
	public Entity()
	{
		name = "UNDEFINED";
		rowPosition = 1;
		columnPosition = 1;
	}
	
	/** constructor that takes int the name, rowTarget and columnTarget
	 * 
	 * @param name The name of entity
	 * @param rowTarget int number of row
	 * @param columnTarget int number of column
	 */
	public Entity(String name, int rowTarget, int columnTarget)
	{
		this.name = name;
		rowPosition = rowTarget;
		columnPosition = columnTarget;
	}
	
	/**Mutator Method
	 * 
	 * @param row int number of row
	 * @param column int number of column
	 */
	public void setPosition(int row, int column)
	{
		rowPosition = row;
		columnPosition = column;
	}
	
	/** Accessor method
	 * 
	 * @return int number of row
	 */
	public int getRow()
	{
		return rowPosition;
	}
	
	/** Accessor method
	 * 
	 * @return int number of column
	 */
	public int getColumn()
	{
		return columnPosition;
	}
}
