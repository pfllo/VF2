package wip.VF2.graph;

import java.util.ArrayList;

public class Node {
	
	public Graph graph; // the graph to which the node belongs
	
	public int id; // a unique id - running number
	public int label; // for semantic feasibility checks
	
	public ArrayList<Edge> outEdges = new ArrayList<Edge>(); // edges of which this node is the origin
	public ArrayList<Edge> inEdges = new ArrayList<Edge>(); // edges of which this node is the destination
	
	public Node(Graph g, int id, int label) {
		this.graph = g;
		this.id = id;
		this.label = label;
	}	
}