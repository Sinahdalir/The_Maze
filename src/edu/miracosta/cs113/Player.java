package edu.miracosta.cs113;

public class Player extends Entity
{
	static int DEF_LIVES = 5;
	int lives;
	
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
	}
}
