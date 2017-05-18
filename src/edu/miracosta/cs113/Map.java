package edu.miracosta.cs113;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map{

    public static final int DEF_ROW = 20;
    public static final int DEF_COLUMN = 20;
    public static final String DEF_MAPFILE = "def_map.txt";
    
    private char[][] map;
    private String currentPosition;
    private String exit;

    public Map(){
        map = new char[DEF_ROW][DEF_COLUMN];
        this.currentPosition = "";
        this.exit = "";
        load(DEF_MAPFILE);
    }


    public Map(String fileName){
        map = new char[DEF_ROW][DEF_COLUMN];
        this.currentPosition = "";
        this.exit = "";
        load(fileName);
    }

    public void setCurrentPosition(String position){
        if(position.length() > 5){
            System.out.println("String length is too long");
            System.exit(0);
        }else{
            currentPosition = position;
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

    public String getCurrentPosition(){
        return this.currentPosition;
    }

    public String getExit(){
        return this.exit;
    }

    public void moveRight(){
        int row = getRow(this.currentPosition);
        int column = getColumn(this.currentPosition);
        if(column ==20){
            System.out.println("Cannot move out of boundary");
        }else if(map[row][column+1] == 'x'){
            System.out.println("Cannot move to the wall");
        }else{
            map[row][column] = 'o';
            map[row][column+1] = 'p';
            currentPosition = "" + row +" "+(column+1);  
        }
    }
    
    public char getChar(String position){
		int row = getRow(position);
		int column = getColumn(position);
		return getChar(row, column);
	}
    
    public char getChar(int row, int column){
		char letter = 'n';
		if(row > 20 || row < 0){
			System.out.println("Row out of Boundary");
		}else if(column > 20 || column < 0){
			System.out.println("Row out of Boundary");
		}else{
			letter = map[row][column];
		}
		return letter;
		
	}
    public void moveUp(){
        int row = getRow(this.currentPosition);
        int column = getColumn(this.currentPosition);
        if(row == 0){
            System.out.println("Cannot move out of boundary");
        }else if(map[row-1][column] == 'x'){
            System.out.println("Cannot move to the wall");
        }else{
            map[row][column] = 'o';
            map[row-1][column] = 'p';
            currentPosition = "" + (row-1)+" "+ column;  
        }
    }
    public void moveLeft(){
        int row = getRow(this.currentPosition);
        int column = getColumn(this.currentPosition);
        if(column ==0){
            System.out.println("Cannot move out of boundary");
        }else if(map[row][column-1] == 'x'){
            System.out.println("Cannot move to the wall");
        }else{
            map[row][column] = 'o';
            map[row][column-1] = 'p';
            currentPosition = "" + row +" "+(column-1);  
        }
    }
    public void moveDown(){
        int row = getRow(this.currentPosition);
        int column = getColumn(this.currentPosition);
        if(row ==20){
            System.out.println("Cannot move out of boundary");
        }else if(map[row+1][column] == 'x'){
            System.out.println("Cannot move to the wall");
        }else{
            map[row][column] = 'o';
            map[row+1][column] = 'p';
            currentPosition = "" + (row+1) +" "+column;  
        }
    }

    public int getRow(String position){
        int space = this.currentPosition.indexOf(" ");
        String rowS = this.currentPosition.substring(0, space);
        int row = Integer.parseInt(rowS);
        return row;
    }

    public int getColumn(String position){
        int space = this.currentPosition.indexOf(" ");
        int column = this.currentPosition.substring(space+1);
        return column;
    }

    public void load(String fileName){
        try{
            Scanner inputStream = new Scanner(new FileInputStream(fileName));
            int row = 0;
            int column = 0;
            char temp= ' ';
            while(inputStream.hasNext()){
                temp = inputStream.next();
                if(temp == 'p'){
                    currentPosition = ""+row+" "+column;
                }
                if(temp == 'e'){
                    exit = ""+row+" "+column;
                }
                map[row][column++] = temp;
                if(row == 20 && column == 20)   break;
                if(column == 20){
                    row++;
                    column = 0;
                }
            }
        }catch(FileNotFoundException fnfe){
            System.out.println("Cannot find the file" + fileName);
            System.exit(0);
        }
    }
}
