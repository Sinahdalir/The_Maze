package edu.miracosta.cs113;

public class Edge 
{
	private int dest = -1;
	private int source = -1;
	private int id = -1;
	private double weight;
	
	/**
	 * Default constructor
	 */
	public Edge()
	{
		weight = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Default constructor
	 * @param source
	 * @param dest
	 */
	public Edge(int source, int dest)
	{
		this.source = source;
		this.dest = dest;
		weight = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Constructor with weight
	 * @param source
	 * @param dest
	 * @param w
	 */
	public Edge(int source, int dest, double w)
	{
		this.source = source;
		this.dest = dest;
		this.weight = w;
	}

	/** Accessor Methods
	 * 
	 * @return int the destination vertex
	 */
	public int getDest() {
		return dest;
	}
	
	public void setDest(int dest) {
		this.dest = dest;
	}
	
	/** Accessor Methods
	 * 
	 * @return int the source vertex
	 */
	public int getSource() {
		return source;
	}
	
	public void setSource(int source) {
		this.source = source;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	/** Accessor Methods
	 * 
	 * @return double the weight of edge
	 */
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/** Return string with given instance variables 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Edge [dest=" + dest + ", source=" + source + ", weight=" + weight + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dest;
		result = prime * result + source;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**Equals method that compares instance variables of two objects 
	 * @return boolean true if they are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (dest != other.dest)
			return false;
		if (source != other.source)
			return false;
		return true;
	}

	
	

}
