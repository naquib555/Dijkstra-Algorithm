package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

import engine.DijkstraAlgorithm;
import model.Edge;
import model.Graph;
import model.Vertex;

public class TestDijkstraAlgorithm {

	private List<Vertex> nodes;
	private List<Edge> edges;
	
	@Test
	public void testExecute() throws NumberFormatException, IOException {
		
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		BufferedReader br = new BufferedReader(new FileReader("NYC.txt"));
		
		
		ArrayList<Vertex> location = new ArrayList<Vertex>();
		
		/*location.add(new Vertex("0", "Mohakhali"));
		location.add(new Vertex("1", "Brac"));
		location.add(new Vertex("2", "Gulshan_1"));
		location.add(new Vertex("3", "Gulshan_2"));
		location.add(new Vertex("4", "Banani"));*/
		
		int totalVertices = Integer.parseInt(br.readLine());
		int totalEdges = Integer.parseInt(br.readLine());
		
		for (int i = 0; i <totalVertices ; i++) {
			location.add(new Vertex(""+i, ""+i));
		}

		
		for(Vertex loc : location) {
			nodes.add(loc);
		}
		
		
		//lane details-->edges
		/*addLane("Mohakhali-Brac", 0, 1, 4);
		addLane("Brac-Gulshan_1", 1, 2, 2);
		addLane("Gulshan_1-Gulshan_2", 2, 3, 7);
		addLane("Gulshan_2-Banani_linkRoad", 3, 4, 8);
		addLane("Banani-Mohakhali", 4, 0, 10);
		addLane("Banani-Brac", 4, 1, 3);*/
		String line;
		StringTokenizer st;
		for (int i = 0; i < totalEdges; i++) {
			line = br.readLine();
			st = new StringTokenizer(line);
			String source = st.nextToken();
			String destination = st.nextToken();
			String distance = st.nextToken();
			String lane = source + "-" + destination;
			addLane(lane, Integer.parseInt(source), Integer.parseInt(destination), Integer.parseInt(distance));
		}
		
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		Vertex sourceVertex = nodes.get(42212);
		Vertex destinationVertex = nodes.get(44346);
		dijkstra.execute(sourceVertex);
		LinkedList<Vertex> path = dijkstra.getPath(destinationVertex);
		
		assertNotNull(path);
		assertTrue(path.size() > 0);
		
		int count = 0;
		for(Vertex vertex : path) {
			System.out.print(vertex);
			count++;
			if(count < path.size()) System.out.print("--" + "" + "-->");
		}
		System.out.println();
		System.out.println("Cost: "+dijkstra.getDistance(destinationVertex));
		
	}
	
	private void addLane(String laneId, int sourceLocationNumber, int destinationLocationNumber, int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocationNumber), nodes.get(destinationLocationNumber), duration);
		edges.add(lane);
	}

}
