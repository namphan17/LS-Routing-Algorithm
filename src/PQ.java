
/**
 * Priority Queue with explicit link
 * 
 * <p>
 * This class is an implementation of the Priority Queue. It uses Node data type
 * to build up a binary heap. Each Node is linked to its children and its
 * parent. For every element we want to insert to our PQ, we will stored those
 * elements the data into the Node.
 * </p>
 * 
 * <img src="../../../build/classes/resources/fis.png" alt="UML class diagram">
 * 
 * @author Nam Phan
 * @version 31 March 2013
 */
public class PQ<Item extends Comparable<Item>> {
	/**
	 * This is the root of our binary heap.
	 */
	private Node root;
	/**
	 * Store the number of elements in this PQ.
	 */
	private int size;

	public class Node {
		int key;
		Item item;
		Node parent;
		Node leftChild;
		Node rightChild;

		// Getting key and item from node
		public Item getItem() {
			return item;
		}

		public int getKey() {
			return key;
		}

		private boolean less(Node node) {
			return (this.item.compareTo(node.item) < 0);
		}

		private boolean greater(Node node) {
			return (this.item.compareTo(node.item) > 0);
		}

		// helper method that check if a key is already in a Queue
		private boolean checkHasKey(int key) {
			
			
			//Debugging--------------------------------------------------Debugging
//			System.out.println("node: " + this.key);
//			System.out.println("dist to this node: " + this.item +"\n");
			
			if (this.key == key) {
				return true;
			} else if (this.rightChild == null && this.leftChild == null) {
				return false;
			} else if (this.rightChild != null && this.leftChild != null) {
				return this.rightChild.checkHasKey(key) || this.leftChild.checkHasKey(key);
			} else if (this.rightChild != null) {
				return this.rightChild.checkHasKey(key);
			} else {
				return this.leftChild.checkHasKey(key);
			}
		}

		// helper method that find the node that includes a given key
//		private Node findKey(int key) {
//		}
	}

	/**
	 * Create a Priority Queue
	 */
	public PQ() {
		this.root = null;
		size = 0;
	}

	/**
	 * Check to see if there is any elements in the PQ.
	 * 
	 * @return true if there are no elements stored in the PQ and false
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Check if a Priority Queue is a binary heap.
	 * 
	 * @return true if the PQ represents a binary heap structure and false
	 *         otherwise
	 */
	public boolean invariant() {
		return invariant(this.root);
	}

	private boolean invariant(Node root) {
		if (root == null)
			return true;
		if (root.leftChild == null && root.rightChild == null) {
			return true;
		} else {
			if (root.leftChild == null) {
				return (!root.rightChild.less(root) && invariant(root.rightChild));
			} else {
				if (root.rightChild == null) {
					return (!root.less(root.leftChild) && invariant(root.leftChild));
				} else {
					return ((!root.rightChild.less(root) || !root.less(root.leftChild)) && invariant(root.rightChild)
							&& invariant(root.leftChild));
				}
			}
		}
	}

	private void swapValue(Node a, Node b) {
		Item itemTemp = a.item;
		int keyTemp = a.key;
		a.item = b.item;
		b.item = itemTemp;
		a.key = b.key;
		b.key = keyTemp;
	}

	private boolean consistent(Node node) {
		if (node == null)
			return true;
		if (node.leftChild != null && node.rightChild != null) {
			if (node.greater(node.leftChild) || node.greater(node.rightChild))
				return false;
		} else {
			if (node.leftChild != null) {
				if (node.greater(node.leftChild))
					return false;
			} else {
				if (node.rightChild != null) {
					return false;
				}
			}
		}
		return true;
	}

	private void bubbleUp(Node node) {
		while (!consistent(node.parent)) {
			swapValue(node, node.parent);
			bubbleUp(node.parent);
		}
	}

	private Node greater(Node a, Node b) {
		if (a.less(b)) {
			return b;
		}
		return a;
	}

	private Node smaller(Node a, Node b) {
		if (a.less(b)) {
			return a;
		}
		return b;
	}

	private void sink(Node node) {
		while (!consistent(node)) {
			if (node.leftChild != null && node.rightChild != null) {
				Node current = smaller(node.leftChild, node.rightChild);
				swapValue(node, current);
				sink(current);
			} else {
				if (node.leftChild != null) {
					swapValue(node, node.leftChild);
					sink(node.leftChild);
				} else {
					swapValue(node, node.rightChild);
					sink(node.rightChild);
				}
			}
		}
	}

	private int size(Node node) {
		if (node == null) {
			return 0;
		}
		return (size(node.leftChild) + size(node.rightChild) + 1);
	}
	
	/**
	 * Insert an element into our PQ. Then do stuff to maintain the heap
	 * structure for the PQ.
	 * 
	 * @param key
	 *            is the elements to be inserted.
	 */
	public void add(int key, Item item) {
		put(key, item);
		bubbleUp(last(root));
	}
	private Node last(Node root) {
		Node current = root;
		if (size(current) == 1)
			return current;
		if (size(root.leftChild) > size(root.rightChild))
			return last(root.leftChild);
		else
			return last(root.rightChild);
	}

	private void put(int key, Item item) {
		this.root = put(root, key, item);
		size++;
	}

	private Node put(Node root, int key, Item item) {
		if (root == null) {
			Node temp = new Node();
			temp.item = item;
			temp.key = key;
			return temp;
		} else {
			if (size(root.leftChild) > size(root.rightChild)) {
				root.rightChild = put(root.rightChild, key, item);
				root.rightChild.parent = root;
			} else {
				root.leftChild = put(root.leftChild, key, item);
				root.leftChild.parent = root;
			}
		}
		return root;
	}

	/**
	 * Checking if this queue already has a key
	 * 
	 */
	public boolean contains(int key) {
//		System.out.println(this.root); 										// Debugging
		if (this.root == null) {
			return false;
		}
			
		return this.root.checkHasKey(key);
	}

	/**
	 * Changing the value of a particular key
	 */
	public void changeKey(int key, Item newValue) {
		Node temp = findKey(this.root, key);
		temp.item = newValue;
		if (temp.parent != null) {
			if (temp.less(temp.parent)) {
				bubbleUp(temp);
			}
		} else {
			sink(temp);
		}
	}
	
	// Helper method that find the Node that includes a given key
	private Node findKey(Node root, int key) {
//		assert (!root.checkHasKey(key)) : "This key doesn't exist in this PQueue";
		if (root.key == key) {
			return root;
		} else if (root.rightChild != null && root.leftChild != null) {
			if (root.rightChild.checkHasKey(key)) {
				return findKey(root.rightChild, key);
			} else {
				return findKey(root.leftChild, key);
			}
		} else if (root.leftChild != null){
			if (root.leftChild.checkHasKey(key)) {
				return findKey(root.leftChild, key);
			}
		}
		return root;
	}

	/**
	 * Remove the greatest(the highest priority) element from the PQ. And then
	 * push the second highest priority element on top of the PQ.
	 * 
	 * @return the top element in the Priority Queue
	 */
	public Node removeNode() {
		assert (!isEmpty()) : "There is no elements in this queue to remove!";
		Node last = last(this.root);
		swapValue(this.root, last);
		if (size(this.root) == 1) {
			this.root = null;
		} else {
			if (size(last.parent) == 2) {
				last.parent.leftChild = null;
			} else {
				last.parent.rightChild = null;
			}
		}
		sink(root);
		size--;
		return last;
	}

	public static void main(String[] args) {
		PQ<Integer> pq = new PQ<>();

		for (int i = 0; i < 20; i++) {
			int item = (int) (Math.random() * 100);
			System.out.print("[" + item + "]  ");
			pq.add(i, item);
		}
		System.out.println();

		pq.changeKey(0, 0);
		pq.changeKey(1, 1);
		pq.changeKey(2, 2);
		pq.changeKey(3, 3);
		pq.changeKey(4, 4);
		pq.changeKey(5, 5);
		pq.changeKey(6, 6);
		pq.changeKey(7, 7);
		pq.changeKey(8, 8);
		pq.changeKey(9, 9);
		pq.changeKey(10, 10);
		pq.changeKey(11, 11);
		pq.changeKey(12, 12);
		pq.changeKey(13, 13);
		pq.changeKey(14, 14);
		pq.changeKey(15, 15);
		pq.changeKey(16, 16);
		pq.changeKey(17, 17);
		pq.changeKey(18, 18);
		pq.changeKey(19, 19);
		pq.changeKey(20, 20);
		

		for (int i = 0; i < 20; i++) {
			PQ.Node minNode = pq.removeNode();
			int key = minNode.getKey();
			int item = (int) minNode.getItem();
			System.out.print("[" + item + "]  ");
		}
	}
}
