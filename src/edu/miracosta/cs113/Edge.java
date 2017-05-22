package edu.miracosta.cs113;

public class Edge 
{
	private int dest;
	private int source;
	private double weight;
	
	public Edge(int source, int dest)
	{
		this.source = source;
		this.dest = dest;
		weight = Double.POSITIVE_INFINITY;
	}
	
	public Edge(int source, int dest, double w)
	{
		this.source = source;
		this.dest = dest;
		this.weight = w;
	}

	public int getDest() {
		return dest;
	}
	
	public int getSource() {
		return source;
	}


	public double getWeight() {
		return weight;
	}

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
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	
	

}
