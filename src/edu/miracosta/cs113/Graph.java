package edu.miracosta.cs113;
import java.util.Iterator;

public interface Graph 
{
	/** Accessor Methods
	 * 
	 * @return the number of vertices
	 */
	int getNumV();
	
	/**Determine whether this is a directed graph
	 * 
	 * @return
	 */
	boolean isDirected();
	
	/** Insert a new edge into the graph
	 * 
	 * @param edge
	 */
	void insert(Edge edge);
	
	/** Determine whether an edge exists
	 * 
	 * @param source The source vertex
	 * @param dest the destination vertex
	 * @return true if there is an edge from the source
	 */
	boolean isEdge(int source, int dest);
	
	/** Get the edge between two vertices
	 * 
	 * @param source The source vertex
	 * @param dest The destination vertex
	 * @return The Edge between these two vertices or
	 * 			an Edge with a weight of Double.POSITIVE_INFINITY if there is no edge
	 */
	Edge getEdge(int source, int dest);
	
	/** Return an iterator to the edges connected to a given vertex
	 * 
	 * @param source The source vertex
	 * @return An Iterator<Edge> to the vertices connected to source
	 */
	Iterator<Edge> edgeIterator(int source);

}
