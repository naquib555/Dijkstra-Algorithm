package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Edge;
import model.Graph;
import model.Vertex;

public class DijkstraAlgorithm {

	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;
	
	public DijkstraAlgorithm(Graph graph) {
		this.nodes = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}
	
	public void execute(Vertex source) {
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		
		while(unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistance(node);
			
		}
	}
	
	private void findMinimalDistance(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for(Vertex target : adjacentNodes) {
			if(getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target,  getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}
	}
	
	private int getDistance(Vertex node, Vertex target) {
		for(Edge edge : edges) {
			if(edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("This should not happen");
	}
	
	
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for(Edge edge : edges) {
			if(edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
			
	}
	
	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	
	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum =  null;
		for(Vertex vertex : vertexes) {
			if(minimum == null)
				minimum = vertex;
			else if(getShortestDistance(vertex) < getShortestDistance(minimum))
					minimum = vertex;
		}
		return minimum;
	}
	
	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if(d == null)
			return Integer.MAX_VALUE;
		else return d;
	}
	
	//returns path --> if the path from source to the selected target exists
	public LinkedList<Vertex> getPath(Vertex target) {
		int cost;
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		//checks if a path exists
		if(predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while(predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		Collections.reverse(path);//order
		return path;
	}
	
	public Integer getDistance(Vertex destination) {
		return distance.get(destination);
	}
	
	
/*
	 * Dijkstra Algorithm
	 * 
	 * 
	 * 
		Foreach node set distance[node] = HIGH
		SettledNodes = empty
		UnSettledNodes = empty
		
		Add sourceNode to UnSettledNodes
		distance[sourceNode]= 0
		
		while (UnSettledNodes is not empty) {
		  evaluationNode = getNodeWithLowestDistance(UnSettledNodes)
		  remove evaluationNode from UnSettledNodes 
		    add evaluationNode to SettledNodes
		    evaluatedNeighbors(evaluationNode)
		}
		
		getNodeWithLowestDistance(UnSettledNodes){
		  find the node with the lowest distance in UnSettledNodes and return it 
		}
		
		evaluatedNeighbors(evaluationNode){
		  Foreach destinationNode which can be reached via an edge from evaluationNode AND which is not in SettledNodes {
		    edgeDistance = getDistance(edge(evaluationNode, destinationNode))
		    newDistance = distance[evaluationNode] + edgeDistance
		    if (distance[destinationNode]  > newDistance) {
		      distance[destinationNode]  = newDistance 
		      add destinationNode to UnSettledNodes
		    }
		  }
*/
	
	
	
	
}
