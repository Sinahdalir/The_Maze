package edu.miracosta.cs113;

public class Player extends Entity
{
	static int DEF_LIVES = 10;
	int lives;
	int lastVertex;
	
	/**Default Constructor
	 * 
	 */
	public Player()
	{
		super();
		lives = DEF_LIVES;
	}
	
	/** Full Constructor
	 * 
	 * @param rowTarget, number of row
	 * @param columnTarget, number of column
	 */
	public Player(int rowTarget, int columnTarget)
	{
		super("Player", rowTarget, columnTarget);
		lives = DEF_LIVES;
		lastVertex = 0;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void setLives(int lives)
	{
		this.lives = lives;
	}
	
	public int getLastVertex()
	{
		return lastVertex;
	}
	
	public void setLastVertex(int vertex)
	{
		lastVertex = vertex;
	}
}
