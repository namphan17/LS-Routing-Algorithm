public class Edge {
		private final int v;
		private final int w;
		private final double weight;
		
		public Edge(int v, int w, double weight){
			this.v = v;
			this.w = w;
			this.weight = weight;
		}
		
		// Methods that return 2 vertices of this edge
		public int other(int x) {
			if (x == this.v) {
				return this.w;
			} else {
				return this.v;
			}
		}
		public int either() {
			return v;
		}
		// Return edge's weight
		public double weight() {
			return weight;
		}
		
		// To string method
		public String toString() {
			return String.format("%d-%d %.2f",  v, w, weight);
		}
	}