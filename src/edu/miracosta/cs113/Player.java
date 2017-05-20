package edu.miracosta.cs113;

public class Player extends Entity
{
	static int DEF_LIVES = 5;
	int lives;
	public Player()
	{
		super();
		lives = DEF_LIVES;
	}
	public Player(int rowTarget, int columnTarget)
	{
		super("Player", rowTarget, columnTarget);
		lives = DEF_LIVES;
	}
}
