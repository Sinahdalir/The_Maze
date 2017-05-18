package edu.miracosta.cs113;


public class AbstractGraph 
{
	private int numV;
	private boolean directed;
	
	public AbstractGraph()
	{
		this.numV = 0;
		this.directed = false;
	}
	
	public AbstractGraph(int numV, boolean directed)
	{
		this.numV = numV;
		this.directed = directed;
	}
	
	public int getNumV()
	{
		return numV;
	}
	
	public boolean isDirected()
	{
		return directed;
	}
	
	
	
	

}
