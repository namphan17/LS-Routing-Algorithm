import java.util.Stack;
import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class WeightedGraph {
	
	private final int V;	// number of vertices
	private int E;			// number of edges
	private Stack<Edge>[] adjV;
	
	public WeightedGraph(int V) {
		this.V = V;
		this.E = 0;
		adjV = (Stack<Edge>[]) new Stack[V];
		for (int i = 0; i < V; i++) {
			adjV[i] = new Stack<Edge>();
		}
	}
	public WeightedGraph(InputStream in) throws IOException {
		InputStreamReader subReader = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(subReader);
		V = Integer.parseInt(reader.readLine());
//		System.out.println("Vertices: " + V);								// Debugging
		int edgeNum = Integer.parseInt(reader.readLine());
//		System.out.println("Edges: " + E);									// Debugging
		adjV = (Stack<Edge>[]) new Stack[V];
		for (int i = 0; i < V; i++) {
			adjV[i] = new Stack<Edge>();
		}
		for (int i = 0; i < edgeNum; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
//			System.out.println(st); 										// Debugging
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			double d = Double.parseDouble(st.nextToken());
			Edge edge = new Edge(v, w, d);
			addEdge(edge);
//			System.out.println(edge);
		}
	}
	public int V() {
		return V;
	}
	public int E() {
		return E;
	}
	
	public void addEdge(Edge edge) {
		
		// Debugging
//		int v = edge.either();
//		System.out.print(v +" - ");
//		System.out.println(edge.other(v));
		
		adjV[edge.either()].add(edge);
		adjV[edge.other(edge.either())].add(edge);
		E++;
	}
	
	// Returns a list of edges that connect through vertex v
	public Iterable<Edge> adj(int v) {
		return adjV[v];
	}
	// Returns a list of all edges in this graph
//	public Iterable<Edge> edges() {
//		
//	}
	/**
	 * String representation
	 * 
	 */
	public String toString() {
		String s = V + " vertices, " + E + " edges\n";
		for (int v = 0; v < V; v++) {
			for (Edge edge : this.adj(v)) {
				System.out.println(edge);
			}
		}
		return s;
	}
	
	/**
	 * Main method
	 * 
	 */
	public static void main(String args[]) throws IOException{
		File file = new File("test.txt");
		WeightedGraph graph = new WeightedGraph(new FileInputStream(file));
//		System.out.println(graph);
	}
}
