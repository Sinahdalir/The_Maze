package edu.miracosta.cs113;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayList;

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
	
	public static int[] shortestPath(Graph graph, int start, int des)
	{

		ArrayList<Integer> path = new ArrayList<Integer>();
		Graph theMaze = null;
		
		//Add graph vertices and edges
		
		int parent[] = BreadthFirstSearch.breadthFirstSearch(theMaze, start);
		Stack<Integer> thePath = new Stack<Integer>();
		int dest = 10;
		
		while(parent[dest] != -1)
		{
			thePath.push(new Integer(dest));
		}
		
		System.out.println("The Shortest Path is :");
		while(!thePath.empty())
		{
			path.add(thePath.pop());
		}
		int[] shortestPath = new int[path.size()];
		for(int i = 0; i  < path.size() ; i++)
		{
			shortestPath[i] = path.get(i);
		}
		return shortestPath;
	}

}
