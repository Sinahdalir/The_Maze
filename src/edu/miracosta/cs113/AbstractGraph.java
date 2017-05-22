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
	
	/**
	 * Construct a graph with the specified number of vertices and the direcetd flag
	 * 
	 * @param numV the number of verticies
	 * @param directed the directed flag
	 */
	public AbstractGraph(int numV, boolean directed)
	{
		this.numV = numV;
		this.directed = directed;
	}
	
	/** Accessor Methods
	 * 
	 * @return the number of vertices
	 */
	public int getNumV()
	{
		return numV;
	}
	
	/**
	 * Return whether this is a directed graph
	 * @return true if this is a directed graph
	 */
	public boolean isDirected()
	{
		return directed;
	}
	
	
	
	

}
