package edu.miracosta.cs113;
import java.util.Stack;

public class Maze 
{
	public static void main(String[] args)
	{
		int numV = 0;
		Graph theMaze = null;
		
		int start = 0;
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
			System.out.println(thePath.pop());
		}

	}

}
