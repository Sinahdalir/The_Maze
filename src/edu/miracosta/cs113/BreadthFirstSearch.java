package edu.miracosta.cs113;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearch 
{
	public static int[] breadthFirstSearch(Graph graph, int start)
	{
		Queue<Integer> theQueue = new LinkedList<Integer>();
		
		int[] parent = new int[graph.getNumV()];
		for(int i = 0; i < graph.getNumV(); i++)
		{
			parent[i] = -1;
		}
		
		boolean[] identified = new boolean[graph.getNumV()];
		identified[start] = true;
		theQueue.offer(start);
		
		while(!theQueue.isEmpty())
		{
			int current = theQueue.remove();
			Iterator<Edge> itr = graph.edgeIterator(current);
			while(itr.hasNext())
			{
				Edge edge = itr.next();
				int neighbor = edge.getDest();
				if(!identified[neighbor])
				{
					identified[neighbor] = true;
					theQueue.offer(neighbor);
					parent[neighbor] = current;
				}
			}
		}
		return parent;
		
	}

}
