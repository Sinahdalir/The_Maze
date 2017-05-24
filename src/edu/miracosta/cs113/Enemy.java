package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity
{
	int		lastRow;
	int		lastColumn;
	int[]	coordinates;
	int		targetVertex;
	int		currentVertex;
	
	/** Full Constructor
	 * 
	 * @param name
	 * of Entity
	 * @param rowTarget
	 * , number of row
	 * @param columnTarget,
	 * number of column */
	public Enemy(String name, int row, int column)
	{
		super(name, row, column);
		lastRow = row;
		lastColumn = column;
		targetVertex = 0;
	}
	
	public void setPosition(int row, int column)
	{
		lastRow = this.row;
		lastColumn = this.column;
		this.row = row;
		this.column = column;
	}
	
	public void setCurrentVertex(int currentVertex)
	{
		this.currentVertex = currentVertex;
	}
	
	public int getCurrentVertex()
	{
		return currentVertex;
	}
	
	public void setTargetVertex(int targetVertex)
	{
		this.targetVertex = targetVertex;
	}
	
	public int getTargetVertex()
	{
		return targetVertex;
	}
	
	public int getLastRow()
	{
		return lastRow;
	}
	
	public int getLastColumn()
	{
		return lastColumn;
	}
	
	public void setCoordinates(int[] coordinates)
	{
		this.coordinates = coordinates;
	}
	
	public int nextVertex()
	{
		if (currentVertex != targetVertex)
		{
			int next = targetVertex;
			while (coordinates[next] != currentVertex)
			{
				next = coordinates[next];
			}
			return next;
		}
		else
		{
			return currentVertex;
		}
	}
}