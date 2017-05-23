package edu.miracosta.cs113;

import java.util.HashSet;
import java.util.Iterator;

public class DijkstrasAlgorithm
{
	public static int[] dijkstrasAlgorithm(Graph graph, int start, int[] pred, double[] dist)
	{
		int numV = graph.getNumV();
		
		HashSet<Integer> vMinusS = new HashSet<Integer>(numV);
		
		for (int i = 0; i < numV; i++)
		{
			if (i != start)
			{
				vMinusS.add(i);
			}
		}
		
		for (int v : vMinusS)
		{
			if (v != start)
			{
				pred[v] = start;
				dist[v] = graph.getEdge(start, v).getWeight();
			}
		}
		
		while (vMinusS.size() != 0)
		{
			double minDist = Double.POSITIVE_INFINITY;
			int u = -1;
			for (int v : vMinusS)
			{
				if (v != start)
				{
					if (dist[v] < minDist)
					{
						minDist = dist[v];
						u = v;
					}
				}
			}
			vMinusS.remove(u);
			Iterator<Edge> edgeIter = graph.edgeIterator(u);
			while (edgeIter.hasNext())
			{
				Edge edge = edgeIter.next();
				int v = edge.getDest();
				if (vMinusS.contains(new Integer(v)))
				{
					double weight = edge.getWeight();
					if (dist[u] + weight < dist[v])
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
