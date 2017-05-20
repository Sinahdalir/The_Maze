package edu.miracosta.cs113;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeGUI extends JFrame implements KeyListener
{
	static ImageIcon	SPRITE_WALL				= new ImageIcon("Sprite_Wall.png");
	static ImageIcon	SPRITE_OPEN				= new ImageIcon("Sprite_Open.png");
	static ImageIcon	SPRITE_PLAYER			= new ImageIcon("Sprite_Player.png");
	static ImageIcon	SPRITE_SNAKE			= new ImageIcon("Sprite_Snake.png");
	static ImageIcon	SPRITE_SPIDER			= new ImageIcon("Sprite_Spider.png");
	static ImageIcon	SPRITE_CAT			= new ImageIcon("Sprite_Cat.png");
	
	static int			LIMITED_VIEW_DISTANCE	= 3;
	
	Map					map;
	JFrame				frame;
	JLabel[][]			labels;
	
	MazeGUI(Map map)
	{
		this.map = map;
		frame = new JFrame("*~ The A-MAZE-ing Adventure ~* by Group B");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labels = new JLabel[map.rows()][map.columns()];
		if (LIMITED_VIEW_DISTANCE * 2 + 2 >=map.rows() || LIMITED_VIEW_DISTANCE * 2 + 2 >=map.columns())
		{
			LIMITED_VIEW_DISTANCE = -1;
		}
		if (LIMITED_VIEW_DISTANCE < 0)
		{
			frame.setLayout(new GridLayout(map.rows(), map.columns()));
		}
		else
		{
			frame.setLayout(new GridLayout(LIMITED_VIEW_DISTANCE * 2 + 1, LIMITED_VIEW_DISTANCE * 2 + 1));
		}
		
		buildLabels();
		buildGUI();
		frame.setResizable(false);
		frame.pack();
		frame.addKeyListener(this);
		frame.setVisible(true);
	}
	
	public void buildGUI()
	{
		if (LIMITED_VIEW_DISTANCE < 0)
		{
			for (int row = 0; row < map.rows(); row++)
			{
				for (int column = 0; column < map.columns(); column++)
				{
					frame.add(labels[row][column]);
				}
			}
		}
		else
		{
			frame.getContentPane().removeAll();
			int endRow = map.getRowPosition() + LIMITED_VIEW_DISTANCE;
			int endColumn = map.getColumnPosition() + LIMITED_VIEW_DISTANCE;
			if (endRow < LIMITED_VIEW_DISTANCE * 2)
			{
				endRow = LIMITED_VIEW_DISTANCE * 2;
			}
			else if (endRow > map.rows() - 1)
			{
				endRow = map.rows() - 1;
			}
			if (endColumn < LIMITED_VIEW_DISTANCE * 2)
			{
				endColumn = LIMITED_VIEW_DISTANCE * 2;
			}
			else if (endColumn > map.columns() - 1)
			{
				endColumn = map.columns() - 1;
			}
			for (int row = endRow - LIMITED_VIEW_DISTANCE * 2; row <= endRow; row++)
			{
				for (int column = endColumn - LIMITED_VIEW_DISTANCE * 2; column <= endColumn; column++)
				{
					frame.add(labels[row][column]);
				}
			}
			frame.validate();
		}
	}
	
	public void buildLabels()
	{
		for (int row = 0; row < map.rows(); row++)
		{
			for (int column = 0; column < map.columns(); column++)
			{
				char nextChar = map.get(row, column);
				if (nextChar == 'x')
				{
					labels[row][column] = new JLabel(SPRITE_WALL);
				}
				else if (nextChar == 'o')
				{
					labels[row][column] = new JLabel(SPRITE_OPEN);
				}
				else if (nextChar == 'p')
				{
					labels[row][column] = new JLabel(SPRITE_PLAYER);
				}
				else if (nextChar == 'e')
				{
					labels[row][column] = new JLabel(SPRITE_OPEN);
				}
				else if (nextChar == 's')
				{
					labels[row][column] = new JLabel(SPRITE_SNAKE);
				}
				else if (nextChar == 'a')
				{
					labels[row][column] = new JLabel(SPRITE_SPIDER);
				}
				else if (nextChar == 'c')
				{
					labels[row][column] = new JLabel(SPRITE_CAT);
				}
			}
		}
	}
	
	public boolean swapLabels(int row1, int column1, int row2, int column2)
	{
		if (map.get(row1, column1) == 'n' || map.get(row2, column2) == 'n')
		{
			return false;
		}
		else
		{
			Icon temp = labels[row1][column1].getIcon();
			labels[row1][column1].setIcon(labels[row2][column2].getIcon());
			labels[row2][column2].setIcon(temp);
			buildGUI();
			return true;
		}
	}
	
	public boolean changeLabel(char newChar, int row, int column)
	{
		if (newChar == 'x')
		{
			labels[row][column] = new JLabel(SPRITE_WALL);
			return true;
		}
		else if (newChar == 'o')
		{
			labels[row][column] = new JLabel(SPRITE_OPEN);
			return true;
		}
		else if (newChar == 'p')
		{
			labels[row][column] = new JLabel(SPRITE_PLAYER);
			return true;
		}
		else if (newChar == 'e')
		{
			labels[row][column] = new JLabel(SPRITE_OPEN);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
		{
			System.out.println("(^'-'^) <( UP! or W )");
			if (map.movePlayer("up"))
			{
				swapLabels(map.getRowPosition(), map.getColumnPosition(), map.getRowPosition() + 1,
						map.getColumnPosition());
			}
		}
		else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
		{
			System.out.println("<('-'^) <( LEFT! or A )");
			if (map.movePlayer("left"))
			{
				swapLabels(map.getRowPosition(), map.getColumnPosition(), map.getRowPosition(),
						map.getColumnPosition() + 1);
			}
		}
		else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
		{
			System.out.println("(v'-'v) <( DOWN! or S )");
			if (map.movePlayer("down"))
			{
				swapLabels(map.getRowPosition(), map.getColumnPosition(), map.getRowPosition() - 1,
						map.getColumnPosition());
			}
		}
		else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
		{
			System.out.println("(^'-')> <( RIGHT! or D )");
			if (map.movePlayer("right"))
			{
				swapLabels(map.getRowPosition(), map.getColumnPosition(), map.getRowPosition(),
						map.getColumnPosition() - 1);
			}
			
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0)
	{
		
	}
}
