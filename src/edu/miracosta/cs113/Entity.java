package edu.miracosta.cs113;

import javax.swing.ImageIcon;

public class Entity
{
	protected int row;
	protected int column;
	protected String name;
	
	/** Default constructor
	 * 
	 */
	public Entity()
	{
		name = "UNDEFINED";
		row = 1;
		column = 1;
	}
	
	/** constructor that takes in the name, row and column
	 * 
	 * @param name The name of entity
	 * @param row row-wise position
	 * @param column column-wise position
	 */
	public Entity(String name, int row, int column)
	{
		this.name = name;
		this.row = row;
		this.column = column;
	}
	
	/**Mutator Method
	 * 
	 * @param row int number of row
	 * @param column int number of column
	 */
	public void setPosition(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	/** Accessor method
	 * 
	 * @return int number of row
	 */
	public int getRow()
	{
		return row;
	}
	
	/** Accessor method
	 * 
	 * @return int number of column
	 */
	public int getColumn()
	{
		return column;
	}
}
