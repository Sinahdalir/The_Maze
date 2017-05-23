package edu.miracosta.cs113;
import java.util.HashSet;

public class DijkstrasAlgorithm 
{
	public static int[] dijkstrasAlgorithm(Graph graph, int start, int[] pred, double[] dist)
	{
		int numV = graph.getNumV();
		
		HashSet<Integer> vMinusS = new HashSet<Integer>(numV);
		
		pred[start] = start;
		dist[start] = 0;
		for(int i = 0; i < numV; i++ )
		{
			if(i != start)
			{
				vMinusS.add(i);
			}
		}
		
		for(int v : vMinusS)
		{
			pred[v] = start;
			if(graph.isEdge(start, v))
			{
				dist[v] = graph.getEdge(start, v).getWeight();
			}
			else
			{
				dist[v] = Double.POSITIVE_INFINITY;
			}
		}
		
		while(!vMinusS.isEmpty())
		{
			
			double minDist = Double.POSITIVE_INFINITY;
			int u = -1;
			for(int v : vMinusS)
			{
				
				if(dist[v] <= minDist)
				{
					minDist = dist[v];
					u = v;
				}
				//System.out.println(v);
				
			}
			
			vMinusS.remove(u);
			for(int v : vMinusS)
			{
				if(graph.isEdge(u, v))
				{
					double weight = graph.getEdge(u, v).getWeight();
					if((dist[u] + weight) < dist[v])
					{
						dist[v] = dist[u] + weight;
						pred[v] = u;
					}
				}
			}
			
			
		}
		return pred;	
	}

}
