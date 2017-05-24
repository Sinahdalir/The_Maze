package edu.miracosta.cs113;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/** Contains a char[][], int[][] and ListGraph representing a maze, as well as
 * reference to a GUI program in order to represent a maze
 * 
 * @author Matt Saucedo <saucedomatthew@gmail.com>
 * @version 1.0 */
public class Map
{
	/** file to read from in the default constructor */
	public static final String	DEF_MAPFILE	= "def_map2.txt";
	
	private String				mapFile;
	private char[][]			map;
	private int[][]				graphMap;
	private Player				player;
	private ArrayList<Enemy>	enemies;
	private ListGraph			graph;
	private Entity				exit;
	MazeGUI						gui;
	private int					numRows		= -1;
	private int					numColumns	= -1;
	
	/** Driver Method, running this runs the entire project
	 * 
	 * @param args */
	public static void main(String[] args)
	{
		Map map = new Map();
	}
	
	/** Default Constructor */
	public Map()
	{
		mapFile = DEF_MAPFILE;
		enemies = new ArrayList<Enemy>();
		load(DEF_MAPFILE);
		newEnemy();
		newEnemy();
		newEnemy();
	}
	
	/** Constructor
	 * 
	 * @param fileName
	 * Uses this file as the data for the layout of the Maze */
	public Map(String fileName)
	{
		mapFile = fileName;
		enemies = new ArrayList<Enemy>();
		load(fileName);
		newEnemy();
		newEnemy();
		newEnemy();
	}
	
	/** Reads in the file, filling out the char[][] and int[][] accordingly. If
	 * there is not a player, or there is not an exit, that variable will be
	 * generated at (1,1) or (numRows-2,numColumns-2) respectively
	 * 
	 * @param fileName
	 * the file to be used for the maze structure */
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
				player.setLastVertex(0);
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
						}
						if (edges[graphMap[row - 1][column] % 100].getSource() < 0)
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
						}
						if (edges[graphMap[row + 1][column] % 100].getSource() < 0)
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
						}
						if (edges[graphMap[row][column - 1] % 100].getSource() < 0)
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
						}
						if (edges[graphMap[row][column + 1] % 100].getSource() < 0)
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
			System.out.println(edges[numEdge]);
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
	
	/** accessor method
	 * 
	 * @param row
	 * row position of char to get
	 * @param column
	 * column position of char to get
	 * @return returns char representing tile at given coordinates */
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
	
	/** executes the move function, with the player and the given direction as
	 * the input, and executes the turns of all enemies afterward
	 * 
	 * @param direction
	 * the direction for the player to move in
	 * @return returns true if the move was successful, false otherwise */
	public boolean movePlayer(String direction)
	{
		boolean returnBoolean = move(player, direction);
		doEnemyTurns();
		return returnBoolean;
	}
	
	/** loops through the ArrayList of Enemies, executing the doEnemyTurn
	 * function for each */
	public void doEnemyTurns()
	{
		for (int enemyNum = 0; enemyNum < enemies.size(); enemyNum++)
		{
			doEnemyTurn(enemies.get(enemyNum));
		}
	}
	
	private void doEnemyTurn(Enemy enemy)
	{
		//just spawned
		if (enemy.getRow() == enemy.getLastRow() && enemy.getColumn() == enemy.getLastColumn())
		{
			enemy.setTargetVertex(player.getLastVertex());
			enemy.setCoordinates(DijkstrasAlgorithm.dijkstrasAlgorithm(graph, enemy.getCurrentVertex(),
					new int[graph.getNumV()], new double[graph.getNumV()]));
			if (enemy.getRow() > 0 && graphMap[enemy.getRow() - 1][enemy.getColumn()] % 100 == graph
					.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
			{
				if (!move(enemy, "UP"))
				{
					randomMove(enemy);
				}
			}
			else if (enemy.getRow() < numRows - 1 && graphMap[enemy.getRow() + 1][enemy.getColumn()] % 100 == graph
					.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
			{
				if (!move(enemy, "DOWN"))
				{
					randomMove(enemy);
				}
			}
			else if (enemy.getColumn() > 0 && graphMap[enemy.getRow()][enemy.getColumn() - 1] % 100 == graph
					.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
			{
				if (!move(enemy, "LEFT"))
				{
					randomMove(enemy);
				}
			}
			else if (enemy.getColumn() < numColumns - 1 && graphMap[enemy.getRow()][enemy.getColumn() + 1]
					% 100 == graph.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
			{
				if (!move(enemy, "RIGHT"))
				{
					randomMove(enemy);
				}
			}
		}
		//hit a vertex
		else if (graphMap[enemy.getRow()][enemy.getColumn()] / 100 == 1)
		{
			enemy.setCurrentVertex(graphMap[enemy.getRow()][enemy.getColumn()] % 100);
			if (enemy.coordinates == null || player.getLastVertex() != enemy.getTargetVertex())
			{
				enemy.setTargetVertex(player.getLastVertex());
				enemy.setCoordinates(
						DijkstrasAlgorithm.dijkstrasAlgorithm(graph, graphMap[enemy.getRow()][enemy.getColumn()] % 100,
								new int[graph.getNumV()], new double[graph.getNumV()]));
			}
			if (enemy.getCurrentVertex() != enemy.getTargetVertex())
			{
				if (enemy.getRow() > 0 && graphMap[enemy.getRow() - 1][enemy.getColumn()] % 100 == graph
						.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
				{
					move(enemy, "UP");
				}
				else if (enemy.getRow() < numRows - 1 && graphMap[enemy.getRow() + 1][enemy.getColumn()] % 100 == graph
						.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
				{
					move(enemy, "DOWN");
				}
				else if (enemy.getColumn() > 0 && graphMap[enemy.getRow()][enemy.getColumn() - 1] % 100 == graph
						.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
				{
					move(enemy, "LEFT");
				}
				else if (enemy.getColumn() < numColumns - 1 && graphMap[enemy.getRow()][enemy.getColumn() + 1]
						% 100 == graph.getEdge(enemy.getCurrentVertex(), enemy.nextVertex()).getId())
				{
					move(enemy, "RIGHT");
				}
			}
		}
		//going through an edge
		else
		{
			if ((map[enemy.getRow() - 1][enemy.getColumn()] == 'o' || map[enemy.getRow() - 1][enemy.getColumn()] == 'p')
					&& (enemy.getRow() - 1 != enemy.getLastRow() || enemy.getColumn() != enemy.getLastColumn()))
			{
				move(enemy, "UP");
			}
			else if ((map[enemy.getRow() + 1][enemy.getColumn()] == 'o'
					|| map[enemy.getRow() + 1][enemy.getColumn()] == 'p')
					&& (enemy.getRow() + 1 != enemy.getLastRow() || enemy.getColumn() != enemy.getLastColumn()))
			{
				move(enemy, "DOWN");
			}
			else if ((map[enemy.getRow()][enemy.getColumn() - 1] == 'o'
					|| map[enemy.getRow()][enemy.getColumn() - 1] == 'p')
					&& (enemy.getRow() != enemy.getLastRow() || enemy.getColumn() - 1 != enemy.getLastColumn()))
			{
				move(enemy, "LEFT");
			}
			else if ((map[enemy.getRow()][enemy.getColumn() + 1] == 'o'
					|| map[enemy.getRow()][enemy.getColumn() + 1] == 'p')
					&& (enemy.getRow() != enemy.getLastRow() || enemy.getColumn() + 1 != enemy.getLastColumn()))
			{
				move(enemy, "RIGHT");
			}
			else
			{
				randomMove(enemy);
			}
		}
	}
	
	private void randomMove(Entity entity)
	{
		Random random = new Random();
		ArrayList<String> possiblePaths = new ArrayList<String>();
		if (map[entity.getRow() - 1][entity.getColumn()] == 'o' || map[entity.getRow() - 1][entity.getColumn()] == 'p')
		{
			possiblePaths.add("UP");
		}
		else if (map[entity.getRow() + 1][entity.getColumn()] == 'o'
				|| map[entity.getRow() + 1][entity.getColumn()] == 'p')
		{
			possiblePaths.add("DOWN");
		}
		else if (map[entity.getRow()][entity.getColumn() - 1] == 'o'
				|| map[entity.getRow()][entity.getColumn() - 1] == 'p')
		{
			possiblePaths.add("LEFT");
		}
		else if (map[entity.getRow()][entity.getColumn() + 1] == 'o'
				|| map[entity.getRow()][entity.getColumn() + 1] == 'p')
		{
			possiblePaths.add("RIGHT");
		}
		if (possiblePaths.size() > 0)
		{
			move(entity, possiblePaths.get(random.nextInt(possiblePaths.size())));
		}
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
		if (entity == player)
		{
			if (map[rowTarget][columnTarget] != 'o' && map[rowTarget][columnTarget] != 'e')
			{
				player.setLives(player.getLives() - 1);
				removeEnemy(getEnemy(rowTarget, columnTarget));
				System.out.println("Current lives: " + player.getLives());
				if (enemies.size() < 10)
				{
					newEnemy();
					newEnemy();
				}
				if (player.getLives() <= 0)
				{
					gui.lose();
				}
			}
			if (graphMap[rowTarget][columnTarget] / 100 == 1)
			{
				player.setLastVertex(graphMap[rowTarget][columnTarget] % 100);
			}
			if (map[rowTarget][columnTarget] == 'e')
			{
				gui.win();
			}
			else
			{
				char temp = map[row][column];
				map[row][column] = map[rowTarget][columnTarget];
				map[rowTarget][columnTarget] = temp;
				entity.setPosition(rowTarget, columnTarget);
				gui.swapLabels(row, column, rowTarget, columnTarget);
			}
			return true;
		}
		else if (map[rowTarget][columnTarget] == 'p')
		{
			player.setLives(player.getLives() - 1);
			removeEnemy((Enemy) entity);
			System.out.println("Current lives: " + player.getLives());
			if (enemies.size() < 10)
			{
				newEnemy();
				newEnemy();
			}
			if (player.getLives() <= 0)
			{
				gui.lose();
			}
			return true;
		}
		else if (map[rowTarget][columnTarget] == 'o')
		{
			char temp = map[row][column];
			map[row][column] = map[rowTarget][columnTarget];
			map[rowTarget][columnTarget] = temp;
			entity.setPosition(rowTarget, columnTarget);
			gui.swapLabels(row, column, rowTarget, columnTarget);
			return true;
		}
		return false;
	}
	
	/* public void loadOld(String fileName){ try{ Scanner inputStream = new
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
	 * } */
	
	/** mutator method, alters the position of the exit, altering the Map and
	 * GUI accordingly. if there is no exit, creates a new exit at the position
	 * 
	 * @param row
	 * row position for exit
	 * @param column
	 * column position for exit */
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
	
	/** mutator method, alters the position of the player, altering the Map and
	 * GUI accordingly. if there is no player, creates a new player at the
	 * position
	 * 
	 * @param row
	 * row position for player
	 * @param column
	 * column position for player */
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
	
	/** creates a new enemy of a random type at a random open position, and
	 * alters the GUI accordingly
	 * 
	 * @return returns Enemy created */
	public Enemy newEnemy()
	{
		Random random = new Random();
		Enemy newEnemy = null;
		int row = random.nextInt(numRows);
		int column = random.nextInt(numColumns);
		while (graphMap[row][column] / 100 != 1)
		{
			row = random.nextInt(numRows);
			column = random.nextInt(numColumns);
		}
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
		newEnemy.setCurrentVertex(graphMap[newEnemy.getRow()][newEnemy.getColumn()] % 100);
		return newEnemy;
	}
	
	/** accessor method
	 * 
	 * @param row
	 * row position of Enemy to find
	 * @param column
	 * column position of Enemy to find
	 * @return returns the Enemy at the position, if there is no Enemy there,
	 * returns null */
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
	
	/** removes an Enemy from the map, the gui, and the ArrayList of enemies
	 * 
	 * @param enemy
	 * Enemy to remove */
	public void removeEnemy(Enemy enemy)
	{
		map[enemy.getRow()][enemy.getColumn()] = 'o';
		gui.changeLabel('o', enemy.getRow(), enemy.getColumn());
		enemies.remove(enemy);
	}
	
	/** accessor method
	 * 
	 * @return returns row position of the player */
	public int getRowPosition()
	{
		return player.getRow();
	}
	
	/** accessor method
	 * 
	 * @return returns column position of the player */
	public int getColumnPosition()
	{
		return player.getColumn();
	}
	
	/** accessor method
	 * 
	 * @return returns number of rows in the map */
	public int rows()
	{
		return (map.length);
	}
	
	/** accessor method
	 * 
	 * @return returns number of columns in the map */
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
