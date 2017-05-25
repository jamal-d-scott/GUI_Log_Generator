import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import javax.swing.JOptionPane;

import java.util.Collections;


public class ShortestPath 
{
	public static String find(String start, String end)
	{
		Graph g = new Graph();
		
		g.addVertex("Office", Arrays.asList(new Vertex("Myer", 2), new Vertex("ResLife", 1)));
		g.addVertex("ResLife", Arrays.asList(new Vertex("Office", 1), new Vertex("Royer", 1)));
		g.addVertex("Royer", Arrays.asList(new Vertex("ResLife", 1), new Vertex("Schlosser", 3)));
		g.addVertex("Schlosser", Arrays.asList(new Vertex("Royer", 3), new Vertex("Myer", 5), new Vertex("AQuads", 4)));
		g.addVertex("Myer", Arrays.asList(new Vertex("Office", 2), new Vertex("Schlosser", 5), new Vertex("Alpha", 3),  new Vertex("Ober", 2)));
		g.addVertex("Ober", Arrays.asList(new Vertex("Myer", 2), new Vertex("Library", 2), new Vertex("CHL", 1),  new Vertex("Brinser", 1)));
		g.addVertex("Alpha", Arrays.asList(new Vertex("Myer", 3), new Vertex("Library", 1), new Vertex("AQuads", 4)));
		g.addVertex("Library", Arrays.asList(new Vertex("Brinser", 3), new Vertex("Alpha", 1), new Vertex("Ober", 1), new Vertex("BSC", 1)));
		g.addVertex("AQuads", Arrays.asList(new Vertex("Schlosser", 4), new Vertex("Alpha", 4), new Vertex("BSC", 1), new Vertex("GYM", 1), new Vertex("LOT4", 1)));
		g.addVertex("BSC", Arrays.asList(new Vertex("Library", 1), new Vertex("Brinser", 1), new Vertex("BSC", 1), new Vertex("AQuads", 1), new Vertex("GYM", 3)));
		g.addVertex("GYM", Arrays.asList(new Vertex("BSC", 3), new Vertex("AQuads", 1), new Vertex("BSC", 1), new Vertex("AQuads", 1), new Vertex("Leffler", 1)));
		g.addVertex("LOT4", Arrays.asList(new Vertex("AQuads", 1), new Vertex("Leffler", 1), new Vertex("Lake", 2)));
		g.addVertex("Leffler", Arrays.asList(new Vertex("LOT4", 1), new Vertex("GYM", 1), new Vertex("Lake", 2), new Vertex("Track", 1)));
		g.addVertex("Lake", Arrays.asList(new Vertex("LOT4", 2), new Vertex("Leffler", 2), new Vertex("Brown", 1), new Vertex("YC", 1)));
		g.addVertex("Brown", Arrays.asList(new Vertex("Lake", 1)));
		g.addVertex("YC", Arrays.asList(new Vertex("Track", 1), new Vertex("Lake", 1), new Vertex("Solar", 2)));
		g.addVertex("Solar", Arrays.asList(new Vertex("YC", 2), new Vertex("Track", 3)));
		g.addVertex("Track", Arrays.asList(new Vertex("Leffler", 1), new Vertex("YC", 2), new Vertex("Solar", 3), new Vertex("Quads", 5)));
		g.addVertex("Quads", Arrays.asList(new Vertex("Track", 5), new Vertex("Soccer", 1)));
		g.addVertex("Soccer", Arrays.asList(new Vertex("Quads", 1), new Vertex("Stadium", 3)));
		g.addVertex("Stadium", Arrays.asList(new Vertex("Soccer", 3), new Vertex("Apts", 2)));
		g.addVertex("Apts", Arrays.asList(new Vertex("Stadium", 2), new Vertex("Founders", 3)));
		g.addVertex("Founders", Arrays.asList(new Vertex("Disc", 10), new Vertex("Apts", 5), new Vertex("Brinser", 5)));
		g.addVertex("Disc", Arrays.asList(new Vertex("Founders", 10), new Vertex("CHL", 7)));
		g.addVertex("CHL", Arrays.asList(new Vertex("Disc", 7), new Vertex("Ober", 1)));
		g.addVertex("Brinser", Arrays.asList(new Vertex("Founders", 5), new Vertex("Ober", 1), new Vertex("Library", 3), new Vertex("BSC", 1)));
		
		ArrayList<String> list =  (ArrayList<String>) g.getShortestPath(start, end);
		Collections.reverse(list);
		
		String path = start + " -> ";
		
		for(int i = 0; i < list.size(); i++)
		{
			if(i < list.size()-1 )
				path += list.get(i) +" -> ";
			else
				path += list.get(i);
		}
		System.out.println(path);
		return path;
	}
}

class Vertex implements Comparable<Vertex> {
	
	private String id;
	private Integer distance;
	
	public Vertex(String id, Integer distance) {
		super();
		this.id = id;
		this.distance = distance;
	}

	public String getId() {
		return id;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Vertex other = (Vertex) obj;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", distance=" + distance + "]";
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.distance < o.distance)
			return -1;
		else if (this.distance > o.distance)
			return 1;
		else
			return this.getId().compareTo(o.getId());
	}
	
}

class Graph {
	
	private final Map<String, List<Vertex>> vertices;
	
	public Graph() {
		this.vertices = new HashMap<String, List<Vertex>>();
	}
	
	public void addVertex(String character, List<Vertex> vertex) {
		this.vertices.put(character, vertex);
	}
	
	public List<String> getShortestPath(String start, String finish) {
		final Map<String, Integer> distances = new HashMap<String, Integer>();
		final Map<String, Vertex> previous = new HashMap<String, Vertex>();
		PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();
		
		for(String vertex : vertices.keySet()) {
			if (vertex == start) {
				distances.put(vertex, 0);
				nodes.add(new Vertex(vertex, 0));
			} else {
				distances.put(vertex, Integer.MAX_VALUE);
				nodes.add(new Vertex(vertex, Integer.MAX_VALUE));
			}
			previous.put(vertex, null);
		}
		
		while (!nodes.isEmpty()) {
			Vertex smallest = nodes.poll();
			if (smallest.getId() == finish) {
				final List<String> path = new ArrayList<String>();
				while (previous.get(smallest.getId()) != null) {
					path.add(smallest.getId());
					smallest = previous.get(smallest.getId());
				}
				return path;
			}

			if (distances.get(smallest.getId()) == Integer.MAX_VALUE) {
				break;
			}
						
			for (Vertex neighbor : vertices.get(smallest.getId())) {
				Integer alt = distances.get(smallest.getId()) + neighbor.getDistance();
				if (alt < distances.get(neighbor.getId())) {
					distances.put(neighbor.getId(), alt);
					previous.put(neighbor.getId(), smallest);
					
					forloop:
					for(Vertex n : nodes) {
						if (n.getId() == neighbor.getId()) {
							nodes.remove(n);
							n.setDistance(alt);
							nodes.add(n);
							break forloop;
						}
					}
				}
			}
		}
		
		return new ArrayList<String>(distances.keySet());
	}
	
}
