package edu.miracosta.cs113;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Map{

    public static final int DEF_ROW = 20;
    public static final int DEF_COLUMN = 20;
    public static final String DEF_MAPFILE = "def_map2.txt";

    private char[][] map;
    private int[][] graphMap;
    private Player player;
    private ArrayList<Entity> entities;
    private ListGraph graph;
    private String exit;
    private int numRows;
    private int numColumns;

    public static void main(String[] args)
    {
    	Map map = new Map();
    	System.out.print(map);
    }
    
    public Map(){
        map = new char[DEF_ROW][DEF_COLUMN];
        entities = new ArrayList<Entity>();
        this.exit = "";
        graph = new ListGraph(27, false);
        load(DEF_MAPFILE);
        MazeGUI gui = new MazeGUI(this);
    }


    public Map(String fileName){
        map = new char[DEF_ROW][DEF_COLUMN];
        entities = new ArrayList<Entity>();
        this.exit = "";
        graph = new ListGraph(27, false);
        load(fileName);
    }

    public void setCurrentPosition(int row, int column){
        if(row < 0 || row >= numRows || column < 0 || column >= numColumns){
            System.out.println("String length is too long");
            System.exit(0);
        }else{
            player.setPosition(row, column);
        }
    }

    public void setExit(String position){
        if(position.length() > 5){
            System.out.println("String length is too long");
            System.exit(0);
        }else{
            exit = position;
        }
    }

    public int getRowPosition(){
        return player.getRow();
    }
    
    public int getColumnPosition(){
        return player.getColumn();
    }

    public String getExit(){
        return this.exit;
    }
    
    public char get(int row, int column){
		char letter = 'n';
		if(row > map.length || row < 0){
			System.out.println("Row out of Boundary");
		}else if(column > map[column].length || column < 0){
			System.out.println("Column out of Boundary");
		}else{
			letter = map[row][column];
		}
		return letter;
		
	}
    public boolean movePlayer(String direction)
    {
    	return move(player, direction);
    }
    public boolean move(Entity entity, String direction)
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
        if(entity != player && map[rowTarget][columnTarget] == 'p')
        {
        	
        }
        char temp = map[row][column];
        map[row][column] = map[rowTarget][columnTarget];
        map[rowTarget][columnTarget] = temp;
        entity.setPosition(rowTarget,columnTarget);
        return true;
    }
    public void load(String fileName){
        try{
            Scanner inputStream = new Scanner(new FileInputStream(fileName));
            int temp;
            String tempString = "";
            for (int row = 0; inputStream.hasNextLine(); row++){
            	tempString = inputStream.nextLine();
            	for (int column = 0; !tempString.isEmpty(); column++)
            	{
            		temp = Integer.parseInt(tempString.substring(0, 3));
            		if (tempString.length() > 3)
            		{
            			tempString = tempString.substring(4);
            		}
            		else
            		{
            			tempString = "";
            		}
            		if (temp/100 == 0)
            		{
            			map[row][column] = 'x';
            		}
            		else if (temp/100 == 1 || temp/100 == 2)
            		{
            			map[row][column] = 'o';
            		}
            	}
            }
            inputStream.close();
            numRows = map[0].length;
            numColumns = map.length;
            if (player == null)
            {
            	newPlayer(1,1);
            }
            if (exit == null)
            {
            	exit = ""+numRows+" "+numColumns;
            }
        }catch(FileNotFoundException fnfe){
            System.out.println("Cannot find the file " + fileName);
            System.exit(0);
        }
        
    }
    public void loadGraphMap(String fileName){
    	try{
            Scanner inputStream = new Scanner(new FileInputStream(fileName));
            int temp;
            String tempString = "";
            for (int row = 0; inputStream.hasNextLine(); row++){
            	tempString = inputStream.nextLine();
            	for (int column = 0; !tempString.isEmpty(); column++)
            	{
            		temp = Integer.parseInt(tempString.substring(0, 3));
            		if (tempString.length() > 3)
            		{
            			tempString = tempString.substring(4);
            		}
            		else
            		{
            			tempString = "";
            		}
            		graphMap[row][column] = temp;
            	}
            }
            inputStream.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("Cannot find the file " + fileName);
            System.exit(0);
        }
    }
    
    public void loadGraph(){
    	for (int row = 0; row < graphMap.length; row++)
    	{
        	for (int column = 0; column < graphMap.length; column++)
        	{
        		
        	}
    	}
    }

    /*
    public void loadOld(String fileName){
        try{
            Scanner inputStream = new Scanner(new FileInputStream(fileName));
            int row = 0;
            int column = 0;
            char temp= ' ';
            String tempString = "";
            while(inputStream.hasNextLine()){
            	tempString = inputStream.nextLine();
            	for (int i = 0; !tempString.isEmpty(); i++)
            	{
            		temp = tempString.charAt(0);
            		tempString = tempString.substring(1);
                    if(temp == 'p'){
                        newPlayer(row, column);
                    }
                    else if(temp == 'e'){
                        exit = ""+row+" "+column;
                    }
                    else if(temp == 's'){
                        newEnemy('s', row, column);
                    }
                    else if(temp == 'a'){
                        newEnemy('a', row, column);
                    }
                    else if(temp == 'c'){
                        newEnemy('c', row, column);
                    }
                    map[row][column] = temp;
                    column++;
            	}
            	row++;
            	column = 0;
            }
            numRows = map[0].length;
            numColumns = map.length;
            inputStream.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("Cannot find the file " + fileName);
            System.exit(0);
        }
        
    }
    */
    public void newPlayer(int row, int column)
    {
    	if (player == null)
    	{
        	player = new Player(row, column);
        	entities.add(0, player);
    	}
    	else
    	{
        	player = new Player(row, column);
        	entities.set(0, player);
    	}
    	map[row][column] = 'p';
    }
    
    public boolean newEnemy(char type, int row, int column)
    {
    	Enemy newEnemy;
    	if (type == 's')
    	{
    		newEnemy = new Enemy("Snake", row, column);
    	}
    	else if (type == 'a')
    	{
    		newEnemy = new Enemy("Spider", row, column);
    	}
    	else if (type == 'c')
    	{
    		newEnemy = new Enemy("Cat", row, column);
    	}
    	else
    	{
    		return false;
    	}
    	entities.add(newEnemy);
    	return true;
    }
    
    public char newEnemy(int row, int column)
    {
    	Random random = new Random();
    	Enemy newEnemy;
    	int type = random.nextInt(3);
    	if (type == 0)
    	{
    		newEnemy = new Enemy("Snake", row, column);
        	map[row][column] = 's';
    	}
    	else if (type == 1)
    	{
    		newEnemy = new Enemy("Spider", row, column);
        	map[row][column] = 'a';
    	}
    	else if (type == 2)
    	{
    		newEnemy = new Enemy("Cat", row, column);
        	map[row][column] = 'c';
    	}
    	else
    	{
    		return 'n';
    	}
    	entities.add(newEnemy);
    	return map[row][column];
    }
    
    public int rows()
    {
    	return(map.length);
    }
    
    public int columns()
    {
    	return(map[0].length);
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
