package edu.miracosta.cs113;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Map
{
	public static final String	DEF_MAPFILE	= "def_map2.txt";
	
	private String				mapFile;
	private char[][]			map;
	private int[][]				graphMap;
	private Player				player;
	private ArrayList<Enemy>	enemies;
	private ListGraph			graph;
	private Entity				exit;
	private boolean				win			= false;
	MazeGUI						gui;
	private int					numRows		= -1;
	private int					numColumns	= -1;
	
	public static void main(String[] args)
	{
		Map map = new Map();
		System.out.print(map);
	}
	
	public Map()
	{
		mapFile = DEF_MAPFILE;
		enemies = new ArrayList<Enemy>();
		graph = new ListGraph(27, false);
		load(DEF_MAPFILE);
		
	}
	
	public Map(String fileName)
	{
		mapFile = fileName;
		enemies = new ArrayList<Enemy>();
		graph = new ListGraph(27, false);
		load(fileName);
	}
	
	public void load(String fileName)
	{
		try
		{
			Scanner inputStream = new Scanner(new FileInputStream(fileName));
			int nextTile; // tile integer last received
			String nextLine; // line from file last received
			char[] nextMapRow; // row of tiles last received, for map
			int[] nextGraphMapRow; // row of tiles last received, for graphMap
			ArrayList<char[]> mapArrayList = new ArrayList<char[]>(); // temporary storage for the map
			ArrayList<int[]> graphMapArrayList = new ArrayList<int[]>(); // temporary storage for the map
			for (int row = 0; inputStream.hasNextLine(); row++)
			{
				nextLine = inputStream.nextLine();
				if (numColumns < 0)
				{
					numColumns = (nextLine.length() + 1) / 4;
				}
				nextMapRow = new char[numColumns];
				nextGraphMapRow = new int[numColumns];
				for (int column = 0; column < numColumns; column++)
				{
					nextTile = Integer.parseInt(nextLine.substring(0, 3));
					if (nextLine.length() > 3)
					{
						nextLine = nextLine.substring(4);
					}
					else
					{
						nextLine = "";
					}
					if (nextTile / 100 == 0)
					{
						nextMapRow[column] = 'x';
					}
					else if (nextTile / 100 == 1 || nextTile / 100 == 2)
					{
						nextMapRow[column] = 'o';
					}
					nextGraphMapRow[column] = nextTile;
				}
				mapArrayList.add(row, nextMapRow);
				graphMapArrayList.add(row, nextGraphMapRow);
			}
			inputStream.close();
			if (numRows < 0)
			{
				numRows = mapArrayList.size();
			}
			map = new char[numRows][numColumns];
			graphMap = new int[numRows][numColumns];
			mapArrayList.toArray(map);
			graphMapArrayList.toArray(graphMap);
			if (player == null)
			{
				player = new Player(1, 1);
				map[1][1] = 'p';
			}
			if (exit == null)
			{
				exit = new Entity("exit", numRows - 2, numColumns - 2);
				map[numRows - 2][numColumns - 2] = 'e';
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("Cannot find the file " + fileName);
			System.exit(0);
		}
		gui = new MazeGUI(this);
		loadGraph();
	}
	
	private void loadGraph()
	{
		if (graphMap == null)
		{
			load(mapFile);
		}
		graph = new ListGraph(getNumVertices(), false);
		Edge[] edges = new Edge[getNumEdges()];
		int nextTile;
		for (int row = 0; row < numRows; row++)
		{
			for (int column = 0; column < numColumns; column++)
			{
				nextTile = graphMap[row][column];
				if (nextTile / 100 == 1)
				{
					if (row > 0 && graphMap[row - 1][column] / 100 == 2)
					{
						//if edge doesn't exists
						if (edges[graphMap[row - 1][column] % 100] == null)
						{
							//new edge with start on vertex
							System.out.println(graphMap[row - 1][column] % 100);
							edges[graphMap[row - 1][column] % 100] = new Edge();
							edges[graphMap[row - 1][column] % 100].setWeight(0.00);
							edges[graphMap[row - 1][column] % 100].setSource(graphMap[row][column] % 100);
						}
						else if (edges[graphMap[row - 1][column] % 100].getSource() < 0)
						{
							edges[graphMap[row - 1][column] % 100].setSource(graphMap[row][column] % 100);
						}
						else
						{
							//add destination to edge
							edges[graphMap[row - 1][column] % 100].setDest(graphMap[row][column] % 100);
						}
					}
					if (row < numRows - 1 && graphMap[row + 1][column] / 100 == 2)
					{
						//if edge doesn't exists
						if (edges[graphMap[row + 1][column] % 100] == null)
						{
							//new edge with start on vertex
							System.out.println(graphMap[row + 1][column] % 100);
							edges[graphMap[row + 1][column] % 100] = new Edge();
							edges[graphMap[row + 1][column] % 100].setWeight(0.00);
							edges[graphMap[row + 1][column] % 100].setSource(graphMap[row][column] % 100);
						}
						else if (edges[graphMap[row + 1][column] % 100].getSource() < 0)
						{
							edges[graphMap[row + 1][column] % 100].setSource(graphMap[row][column] % 100);
						}
						else
						{
							//add destination to edge
							edges[graphMap[row + 1][column] % 100].setDest(graphMap[row][column] % 100);
						}
					}
					if (column > 0 && graphMap[row][column - 1] / 100 == 2)
					{
						//if edge doesn't exists
						if (edges[graphMap[row][column - 1] % 100] == null)
						{
							//new edge with start on vertex
							System.out.println(graphMap[row][column - 1] % 100);
							edges[graphMap[row][column - 1] % 100] = new Edge();
							edges[graphMap[row][column - 1] % 100].setWeight(0.00);
							edges[graphMap[row][column - 1] % 100].setSource(graphMap[row][column] % 100);
						}
						else if (edges[graphMap[row][column - 1] % 100].getSource() < 0)
						{
							edges[graphMap[row][column - 1] % 100].setSource(graphMap[row][column] % 100);
						}
						else
						{
							//add destination to edge
							edges[graphMap[row][column - 1] % 100].setDest(graphMap[row][column] % 100);
						}
					}
					if (column < numColumns - 1 && graphMap[row][column + 1] / 100 == 2)
					{
						//if edge doesn't exists
						if (edges[graphMap[row][column + 1] % 100] == null)
						{
							//new edge with start on vertex
							System.out.println(graphMap[row][column + 1] % 100);
							edges[graphMap[row][column + 1] % 100] = new Edge();
							edges[graphMap[row][column + 1] % 100].setWeight(0.00);
							edges[graphMap[row][column + 1] % 100].setSource(graphMap[row][column] % 100);
						}
						else if (edges[graphMap[row][column + 1] % 100].getSource() < 0)
						{
							edges[graphMap[row][column + 1] % 100].setSource(graphMap[row][column] % 100);
						}
						else
						{
							//add destination to edge
							edges[graphMap[row][column + 1] % 100].setDest(graphMap[row][column] % 100);
						}
					}
				}
				else if (nextTile / 100 == 2)
				{
					if (edges[nextTile % 100] == null)
					{
						edges[nextTile % 100] = new Edge();
						edges[nextTile % 100].setId(nextTile % 100);
						edges[nextTile % 100].setWeight(0.00);
					}
					if (edges[nextTile % 100].getId() < 0)
					{
						edges[nextTile % 100].setId(nextTile % 100);
					}
					edges[nextTile % 100].setWeight(edges[nextTile % 100].getWeight() + 0.01);
				}
			}
		}
		for (int numEdge = 0; numEdge < edges.length; numEdge++)
		{
			graph.insert(edges[numEdge]);
		}
	}
	
	private int getNumVertices()
	{
		int numVertices = 0;
		int numAdjacent;
		for (int row = 0; row < numRows; row++)
		{
			for (int column = 0; column < numColumns; column++)
			{
				numAdjacent = 0;
				if (map[row][column] == 'o')
				{
					if (row > 0 && map[row - 1][column] == 'o')
					{
						numAdjacent++;
					}
					if (row < numRows - 1 && map[row + 1][column] == 'o')
					{
						numAdjacent++;
					}
					if (column > 0 && map[row][column - 1] == 'o')
					{
						numAdjacent++;
					}
					if (column < numColumns - 1 && map[row][column + 1] == 'o')
					{
						numAdjacent++;
					}
					if (numAdjacent != 2)
					{
						numVertices++;
					}
				}
			}
		}
		System.out.println("Vertices: " + numVertices);
		return numVertices;
	}
	
	private int getNumEdges()
	{
		int numEdges = 0;
		int numAdjacent;
		for (int row = 0; row < numRows; row++)
		{
			for (int column = 0; column < numColumns; column++)
			{
				numAdjacent = 0;
				if (map[row][column] == 'o')
				{
					if (row > 0 && map[row - 1][column] == 'o')
					{
						numAdjacent++;
					}
					if (row < numRows - 1 && map[row + 1][column] == 'o')
					{
						numAdjacent++;
					}
					if (column > 0 && map[row][column - 1] == 'o')
					{
						numAdjacent++;
					}
					if (column < numColumns - 1 && map[row][column + 1] == 'o')
					{
						numAdjacent++;
					}
					if (numAdjacent == 2)
					{
						numEdges++;
					}
				}
			}
		}
		System.out.println("Edges: " + numEdges);
		return 33;
	}
	
	public char getChar(int row, int column)
	{
		char letter = 'n';
		if (row > map.length || row < 0)
		{
			System.out.println("Row out of Boundary");
		}
		else if (column > map[column].length || column < 0)
		{
			System.out.println("Column out of Boundary");
		}
		else
		{
			letter = map[row][column];
		}
		return letter;
		
	}
	
	public boolean movePlayer(String direction)
	{
		boolean returnBoolean = move(player, direction);
		doEnemyTurns();
		return returnBoolean;
	}
	
	public void doEnemyTurns()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy nextEnemy = enemies.get(i);
			/*
			 * // fresh start if (nextEnemy.getLastRow() == nextEnemy.getRow()
			 * && nextEnemy.getColumn() == nextEnemy.getLastColumn()) {
			 * 
			 * } // on a vertex else
			 */if (graphMap[nextEnemy.getRow()][nextEnemy.getColumn()] / 100 == 2)
			{
				if ((graphMap[nextEnemy.getRow() - 1][nextEnemy.getColumn()] / 100 == 1
						|| graphMap[nextEnemy.getRow() - 1][nextEnemy.getColumn()] / 100 == 2)
						&& (nextEnemy.getRow() - 1 != nextEnemy.getLastRow()
								|| nextEnemy.getColumn() != nextEnemy.getLastColumn()))
				{
					move(nextEnemy, "UP");
				}
				else if ((graphMap[nextEnemy.getRow() + 1][nextEnemy.getColumn()] / 100 == 1
						|| graphMap[nextEnemy.getRow() + 1][nextEnemy.getColumn()] / 100 == 2)
						&& (nextEnemy.getRow() + 1 != nextEnemy.getLastRow()
								|| nextEnemy.getColumn() != nextEnemy.getLastColumn()))
				{
					move(nextEnemy, "DOWN");
				}
				else if ((graphMap[nextEnemy.getRow()][nextEnemy.getColumn() - 1] / 100 == 1
						|| graphMap[nextEnemy.getRow()][nextEnemy.getColumn() - 1] / 100 == 2)
						&& (nextEnemy.getRow() != nextEnemy.getLastRow()
								|| nextEnemy.getColumn() - 1 != nextEnemy.getLastColumn()))
				{
					move(nextEnemy, "LEFT");
				}
				else if ((graphMap[nextEnemy.getRow()][nextEnemy.getColumn() + 1] / 100 == 1
						|| graphMap[nextEnemy.getRow()][nextEnemy.getColumn() + 1] / 100 == 2)
						&& (nextEnemy.getRow() != nextEnemy.getLastRow()
								|| nextEnemy.getColumn() + 1 != nextEnemy.getLastColumn()))
				{
					move(nextEnemy, "RIGHT");
				}
				else
				{
					randomMove(nextEnemy);
				}
			}
			else
			{
				randomMove(nextEnemy);
			}
		}
	}
	
	private void randomMove(Entity entity)
	{
		Random random = new Random();
		ArrayList<String> possiblePaths = new ArrayList<String>();
		if (graphMap[entity.getRow() - 1][entity.getColumn()] / 100 == 1
				|| graphMap[entity.getRow() - 1][entity.getColumn()] / 100 == 2)
		{
			possiblePaths.add("UP");
		}
		else if (graphMap[entity.getRow() + 1][entity.getColumn()] / 100 == 1
				|| graphMap[entity.getRow() + 1][entity.getColumn()] / 100 == 2)
		{
			possiblePaths.add("DOWN");
		}
		else if (graphMap[entity.getRow()][entity.getColumn() - 1] / 100 == 1
				|| graphMap[entity.getRow()][entity.getColumn() - 1] / 100 == 2)
		{
			possiblePaths.add("LEFT");
		}
		else if (graphMap[entity.getRow()][entity.getColumn() + 1] / 100 == 1
				|| graphMap[entity.getRow()][entity.getColumn() + 1] / 100 == 2)
		{
			possiblePaths.add("RIGHT");
		}
		move(entity, possiblePaths.get(random.nextInt(possiblePaths.size())));
	}
	
	private boolean move(Entity entity, String direction)
	{
		int row = entity.getRow();
		int column = entity.getColumn();
		int rowTarget = row;
		int columnTarget = column;
		if (direction.equalsIgnoreCase("UP"))
		{
			if (row == 0)
			{
				System.out.println("Cannot move out of boundary");
				return false;
			}
			rowTarget--;
		}
		else if (direction.equalsIgnoreCase("DOWN"))
		{
			if (row == rows())
			{
				System.out.println("Cannot move out of boundary");
				return false;
			}
			rowTarget++;
		}
		else if (direction.equalsIgnoreCase("LEFT"))
		{
			if (column == 0)
			{
				System.out.println("Cannot move out of boundary");
				return false;
			}
			columnTarget--;
		}
		else if (direction.equalsIgnoreCase("RIGHT"))
		{
			if (column == columns())
			{
				System.out.println("Cannot move out of boundary");
				return false;
			}
			columnTarget++;
		}
		if (map[rowTarget][columnTarget] == 'x')
		{
			System.out.println("Cannot move to the wall");
			return false;
		}
		if (entity != player && map[rowTarget][columnTarget] == 'p')
		{
			player.setLives(player.getLives() - 1);
			removeEnemy((Enemy) entity);
			System.out.println("Current lives: " + player.getLives());
		}
		else
		{
			if (map[rowTarget][columnTarget] != 'o' && map[rowTarget][columnTarget] != 'e')
			{
				player.setLives(player.getLives() - 1);
				removeEnemy(getEnemy(rowTarget, columnTarget));
				System.out.println("Current lives: " + player.getLives());
			}
			if (graphMap[rowTarget][columnTarget] / 100 == 1)
			{
				player.setLastVertex(graphMap[rowTarget][columnTarget] % 100);
			}
			if (map[rowTarget][columnTarget] == 'e')
			{
				System.out.println("YOU WIN");
				win = true;
			}
			else
			{
				char temp = map[row][column];
				map[row][column] = map[rowTarget][columnTarget];
				map[rowTarget][columnTarget] = temp;
				entity.setPosition(rowTarget, columnTarget);
				gui.swapLabels(row, column, rowTarget, columnTarget);
			}
		}
		return true;
	}
	
	/*
	 * public void loadOld(String fileName){ try{ Scanner inputStream = new
	 * Scanner(new FileInputStream(fileName)); int row = 0; int column = 0; char
	 * temp= ' '; String tempString = ""; while(inputStream.hasNextLine()){
	 * tempString = inputStream.nextLine(); while( !tempString.isEmpty()) { temp
	 * = tempString.charAt(0); tempString = tempString.substring(1); if(temp ==
	 * 'p'){ newPlayer(row, column); } else if(temp == 'e'){ exit = ""+row+" "
	 * +column; } else if(temp == 's'){ newEnemy('s', row, column); } else
	 * if(temp == 'a'){ newEnemy('a', row, column); } else if(temp == 'c'){
	 * newEnemy('c', row, column); } map[row][column] = temp; column++; } row++;
	 * column = 0; } numRows = map[0].length; numColumns = map.length;
	 * inputStream.close(); }catch(FileNotFoundException fnfe){
	 * System.out.println("Cannot find the file " + fileName); System.exit(0); }
	 * 
	 * }
	 * 
	 * public void loadJunction(String fileName) { try{ Scanner inputStream =
	 * new Scanner(new FileInputStream(fileName)); int row = 0; int column = 0;
	 * char temp= ' '; String tempString = ""; while(inputStream.hasNextInt()){
	 * tempString = inputStream.nextLine(); while( !tempString.isEmpty()) { temp
	 * = tempString.charAt(0); tempString = tempString.substring(1); if(temp ==
	 * 'p'){ newPlayer(row, column); } else if(temp == 'e'){ exit = ""+row+" "
	 * +column; } else if(temp == 's'){ newEnemy('s', row, column); } else
	 * if(temp == 'a'){ newEnemy('a', row, column); } else if(temp == 'c'){
	 * newEnemy('c', row, column); } map[row][column] = temp; column++; } row++;
	 * column = 0; } numRows = map[0].length; numColumns = map.length;
	 * inputStream.close(); }catch(FileNotFoundException fnfe){
	 * System.out.println("Cannot find the file " + fileName); System.exit(0); }
	 * 
	 * }
	 */
	
	public void setExit(int row, int column)
	{
		if (exit != null)
		{
			map[exit.getRow()][exit.getColumn()] = 'o';
			gui.changeLabel('o', exit.getRow(), exit.getColumn());
		}
		exit = new Entity("exit", row, column);
		map[row][column] = 'e';
		gui.changeLabel('e', row, column);
	}
	
	public void setPlayer(int row, int column)
	{
		if (player != null)
		{
			map[player.getRow()][player.getColumn()] = 'o';
			gui.changeLabel('o', player.getRow(), player.getColumn());
		}
		player = new Player(row, column);
		map[row][column] = 'p';
		gui.changeLabel('p', row, column);
	}
	
	public char newEnemy(int row, int column)
	{
		Random random = new Random();
		Enemy newEnemy = null;
		int type = random.nextInt(3);
		if (type == 0)
		{
			newEnemy = new Enemy("Snake", row, column);
			map[row][column] = 's';
			gui.changeLabel('s', row, column);
		}
		else if (type == 1)
		{
			newEnemy = new Enemy("Spider", row, column);
			map[row][column] = 'a';
			gui.changeLabel('a', row, column);
		}
		else if (type == 2)
		{
			newEnemy = new Enemy("Cat", row, column);
			map[row][column] = 'c';
			gui.changeLabel('c', row, column);
		}
		enemies.add(newEnemy);
		return map[row][column];
	}
	
	public Enemy getEnemy(int row, int column)
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			if (enemies.get(i).getRow() == row && enemies.get(i).getColumn() == column)
			{
				return enemies.get(i);
			}
		}
		return null;
	}
	
	public void removeEnemy(Enemy enemy)
	{
		map[enemy.getRow()][enemy.getColumn()] = 'o';
		gui.changeLabel('o', enemy.getRow(), enemy.getColumn());
		enemies.remove(enemy);
	}
	
	public int getRowPosition()
	{
		return player.getRow();
	}
	
	public int getColumnPosition()
	{
		return player.getColumn();
	}
	
	public int rows()
	{
		return (map.length);
	}
	
	public int columns()
	{
		return (map[0].length);
	}
	
	public String toString()
	{
		String returnString = "";
		if (map == null)
		{
			returnString = "null";
		}
		else
		{
			for (int i = 0; i < 20; i++)
			{
				returnString += "\n";
				for (int z = 0; z < 20; z++)
				{
					returnString += map[i][z];
				}
			}
		}
		return returnString;
	}
}
