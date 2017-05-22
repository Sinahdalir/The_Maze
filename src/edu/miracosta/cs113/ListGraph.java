package edu.miracosta.cs113;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class ListGraph extends AbstractGraph implements Graph
{
	private List<Edge>[] edges;
	
	/** Construct a graph with the specified number of vertices and directionality
	 * 
	 * @param numV the number of vertices
	 * @param directed The directionality flag
	 */
	@SuppressWarnings("unchecked")
	public ListGraph(int numV, boolean directed)
	{
		super(numV, directed);
		edges = new List[numV];
		for(int i = 0; i < numV; i++)
		{
			edges[i] = new LinkedList<Edge>();
		}
	}
	
	 /** Determine whether an edge exists
	  * 
	  * @param source the source vertex
	  * @param dest the destination vertex
	  * @return true if there is an edge from source to dest
	  */
	public boolean isEdge(int source, int dest)
	{
		return edges[source].contains(new Edge(source, dest));
	}
	
	/**Insert a new edge into the graph
	 * @param edge the new edge
	 */
	public void insert(Edge edge)
	{
		edges[edge.getSource()].add(edge);
		if(!isDirected())
		{
			edges[edge.getDest()].add(new Edge(edge.getDest(), edge.getSource(), edge.getWeight()));
		}
	}
	
	
	public Iterator<Edge> edgeIterator(int source)
	{
		return edges[source].iterator();
	}
	
	/** Get edge between two vertices, If an edge does not exist, an Edge with weight 
	 * of Double.POSITIVE_INFINITY is returned
	 * @param source the source
	 * @param dest the destination
	 * @return the edge between these two vertices
	 * 
	 */
	public Edge getEdge(int source, int dest)
	{
		Edge target = new Edge(source, dest, Double.POSITIVE_INFINITY);
		for(Edge edge : edges[source])
		{
			if(edge.equals(target))
			{
				return edge;
			}
		}
		
		return target;
	}
	
	public int getNumV()
	{
		return edges.length;
	}
	

}
