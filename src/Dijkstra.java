import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;
public class Dijkstra {
	private Edge[] edgeTo;
	private double[] distTo;
	private PQ<Double> pq;
	
	public Dijkstra(WeightedGraph graph, int source) {
		edgeTo = new Edge[graph.V()];
		distTo = new double[graph.E()];
		pq = new PQ<Double>();
		
		for (int v = 0; v < graph.V(); v++){
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[source] = 0;
		
		pq.add(source, 0.0);
		while (!pq.isEmpty()) {
			PQ.Node minNode = pq.removeNode();
//			System.out.println("\nUpdating: key = " + minNode.getKey() + "; distTo = " + minNode.getItem());
			update(graph, minNode.getKey());
		}
	}
	
	public void update(Edge edge) {
		int v = edge.either();
		int w = edge.other(v);
		if (distTo[w] > distTo[v] + edge.weight()) {
			distTo[w] = distTo[v] + edge.weight();
			edgeTo[w] = edge;
		}
	}
	// Update cost to a Node in Graph
	private void update(WeightedGraph graph, int v) {
		for (Edge edge : graph.adj(v)) {
			int w = edge.other(v);
			if (distTo[w] > distTo[v] + edge.weight()) {
				distTo[w] = distTo[v] + edge.weight();
				edgeTo[w] = edge;
				
//				System.out.println("Adding: key = " + w + "; distTo = " + distTo[w]); 			// Debugging
//				System.out.println(w + " already in PQ: " + pq.contains(w));					// Debugging
				if (pq.contains(w)) {
					pq.changeKey(w, distTo[w]);
				} else {
					pq.add(w, distTo[w]);
				}
			}
		}
	}
	
	/* Client queries for shortest path to each node
	 */
	public double distTo(int v) {
		return distTo[v];
	}
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	// Return a shortest path to Node v
	public Iterable<Edge> pathTo(int v) {
		Edge e = edgeTo[v];
		Stack<Edge> shortestPath = new Stack<>();
		while (e != null) {
			shortestPath.add(e);
			int w = e.other(v);
			e = edgeTo[w];
		}
		return shortestPath;	
	}
	
	/**
	 * Main methods
	 * 
	 */
	public static void main(String args[]) throws IOException {
		File file = new File("test.txt");
		WeightedGraph graph = new WeightedGraph(new FileInputStream(file));
		Dijkstra shortestPath = new Dijkstra(graph, 0);
		Stack<Edge> pathTo5 = (Stack<Edge>)shortestPath.pathTo(5);
		for (Edge edge : pathTo5) {
			System.out.println(edge);
		}
		System.out.println("Distance to Node 5 is: " + shortestPath.distTo(5));
	}
}










