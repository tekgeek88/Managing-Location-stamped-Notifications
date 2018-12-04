public class BST<K extends Comparable<K>, T> implements Map<K, T> {

	BSTNode<K, T> root;
	BSTNode<K, T> current;
	boolean isFull = false;
	
	
	private static class BSTNode<K extends Comparable<K>, T> implements Comparable<K> {

		K key;
		T data;
		BSTNode<K, T> left, right;

		BSTNode(K key, T data) {
			this(key, data, null, null);
		}

		BSTNode(K key, T value, BSTNode<K, T> left, BSTNode<K, T> right) {
			this.key = key;
			this.data = value;
			this.left = left;
			this.right = right;
		}		

		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(K other) {
			if (other != null) {
				return key.compareTo(other);
			} else {
				return 0;
			}
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (key != null) {
				sb.append("Key: ");
				sb.append(key);
			}
			if (left != null ) {
				sb.append(" Left: ");
				sb.append(left.key);
			}
			if (right != null ) {
				sb.append(" Right: ");
				sb.append(right.key);
			}
			return sb.toString();
		}

	}// End BSTNode class
	
	

	/** 
	 * Default constructor
	 */
	BST() {
		root = null;
		current = null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean empty() {
		return root == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean full() {
		return isFull;
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		root = null;
		current = null;
		isFull = false;
	}

	/** {@inheritDoc} */
	@Override
	public T retrieve() {
		if (current != null) {
			return current.data;
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void update(T e) {
		if (current != null) {
			current.data = e;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean find(K key) {
		BSTNode<K, T> result = get(key);
		if (result == null) {
			return false;
		} else {
			current = result;
			return true;
		}
	}

	/**
	 * Private helper method used to get a node for the given key
	 * @param key the desired node
	 * @return Desired node if found OR NULL if not found
	 */
	private BSTNode<K, T> get(K key) {
		BSTNode<K, T> result = findNode(key);
		if (result != null && key.compareTo(result.key) == 0) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int nbKeyComp(K key) {
		BSTNode<K, T> runner = root;
		return getnbKeyComp(runner, key);
	}

	/** {@inheritDoc} */
	@Override
	public int nbKeyComp(K k1, K k2) {
		BSTNode<K, T> runner = root;
		// If the keys are not in ascending order we reverse them
		if (k2.compareTo(k1) < 0) {
			K temp = k1;
			k1 = k2;
			k2 = temp;
		}
		return getnbKeyComp(runner, k1, k2);
	}

	private int getnbKeyComp(BSTNode<K, T> node, K low, K high) {
		if(node == null) 
			return 0; 

		// Node is in range
		if(node.key.compareTo(low) >= 0 && node.key.compareTo(high) <= 0) { 
			return 1 + getnbKeyComp(node.left, low, high) + this.getnbKeyComp(node.right, low, high);
		}
		// If current node is smaller than low,  
		// then recur for right child 
		else if(node.key.compareTo(low) < 0) 
			return 1 + getnbKeyComp(node.right, low, high); 

		// Else recur for left child 
		else {
			return getnbKeyComp(node.left, low, high);
		}
	}

	/**
	 * Helper method used to count the number of comparisons taken before finding a node
	 * @param node
	 * @param key
	 * @return
	 */
	private int getnbKeyComp(BSTNode<K, T> node, K key) {
		int count = 0;
		BSTNode<K, T> runner;   // Current node in search

		runner = root;  // Always start at the root node

		while ( runner != null ) {
			if (key.compareTo(runner.key ) < 0 ) {
				runner = runner.left;
				count++;
			} else if ( key.compareTo( runner.key ) > 0 ) {
				runner = runner.right;
				count++;
			} else {
				// Found key in BST
				return count;
			}
		}
		// Return 0 if key is not found
		return 0;
	}

	/**
	 * Searches BST for given key, returns node if found, or parent if not found.
	 * And returns null if BST is empty. 
	 * @param key
	 * @return
	 */
	private BSTNode<K, T> findNode(K key) {

		BSTNode<K, T> runner;   // Current node in search
		BSTNode<K, T> prev;   	// Parent of node if not found

		runner = root;  // Always start at the root node
		prev = null;  	// Remember the previous node for insertion

		while ( runner != null ) {
			if (!runner.isLeaf()) {
				isFull = true;
			} else {
				isFull = false;
			}

			if (key.compareTo(runner.key ) < 0 ) {
				prev = runner;
				runner = runner.left;
			} else if ( key.compareTo( runner.key ) > 0 ) {
				prev = runner;
				runner = runner.right;
			} else {
				// Found key in BST
				return runner;
			}
		}
		// Return parent node if key is not found
		return prev;
	}

	/** {@inheritDoc} */
	@Override
	public boolean insert(K key, T data) {

		// If BST is empty
		if (root == null) {
			root = current = new BSTNode<K, T>(key, data);
			isFull = true;
			return true;
		}

		// Find a node with the given key or it's parent if it doesn't exist
		BSTNode<K, T> runner = findNode(key);

		// If the key already exists do nothing and return false.
		if (runner.key.compareTo(key) == 0) {
			return false;
		}

		// We need to insert it and return true
		else {
			// Create a new node with runner as its parent
			BSTNode<K, T> newNode = new BSTNode<K, T>(key, data);
			if (key.compareTo(runner.key) < 0) {
				runner.left = newNode;
			} else {
				runner.right = newNode;
			}
			current = newNode;
			return true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean remove(K key) {
		// Search for key
		K k1 = key;
		BSTNode<K, T> runner = root;
		BSTNode<K, T> prev = null; // Parent

		while (runner != null) {
			if (k1.compareTo(runner.key) < 0) {
				prev = runner;
				runner = runner.left;
			} else if (k1.compareTo(runner.key) > 0) {
				prev = runner;
				runner = runner.right;
			}
			// found the key
			else {
				// Check the three cases
				// If we delete this node it will not change our fullness
				if ((runner.left != null) && (runner.right != null)) {
					// Case 3: two children
					// Search for the min in the right subtree
					BSTNode<K, T> min = runner.right;
					prev = runner;
					while(min.left != null) {
						prev = min;
						min = min.left;
					}
					runner.key = min.key;
					runner.data = min.data;
					k1 = min.key;
					runner = min;
				}
				// Now fall back to either case 1 or 2
				// The subtree rooted at p will change here
				// One child
				if (runner.left != null) {
					runner = runner.left;
				}
				// One or no children
				else {
					runner = runner.right;
				}
				// No parent for p, root must change
				if (prev == null) {
					root = runner;
					if (runner != null) {
						if (!runner.isLeaf()) {
							isFull = false;
						} else {
							isFull = true;
						}
					} else {
						isFull = false;
					}
				}
				else {
					if (k1.compareTo(prev.key) < 0) {
						prev.left = runner;
					} else {
						prev.right = runner;
					}
					if (!prev.isLeaf()) {
						isFull = false;
					} else {
						isFull = true;
					}
				}
				current = root;
				return true;
			}
		}
		return false; // Not found
	}

	/** {@inheritDoc} */
	@Override
	public List<Pair<K, T>> getAll() {
		BSTNode<K, T> runner = root;
		List<Pair<K, T>> list = new LinkedList<Pair<K, T>>();
		getAll(runner, list);
		list.findFirst();
		return list;
	}

	/**
	 * Private helper method
	 * Traverse the the BST from the given root using an in-order traversal
	 * @param node
	 */
	private void getAll(BSTNode<K, T> node, List<Pair<K, T>> list) {
		if( node != null ) {
			getAll(node.left, list);
			list.insert(new Pair<K, T>(node.key, node.data));
			getAll(node.right, list);
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<Pair<K, T>> getRange(K k1, K k2) {
		BSTNode<K, T> runner = root;
		List<Pair<K, T>> listInRange = new LinkedList<Pair<K, T>>();
		// If the keys are not in ascending order we reverse them
		if (k2.compareTo(k1) < 0) {
			K temp = k1;
			k1 = k2;
			k2 = temp;
		}
		getRange(runner, k1, k2, listInRange);
		listInRange.findFirst();
		return listInRange;
	}

	private void getRange(BSTNode<K, T> node, K k1, K k2, List<Pair<K, T>> list) { 
		if (node == null) { 
			return; 
		} 
		if (k1.compareTo(node.key) < 0) { 
			getRange(node.left, k1, k2, list);
		} 
		if ((k1.compareTo(node.key) <= 0) && (k2.compareTo(node.key) >= 0)) { 
			list.insert(new Pair<K, T>(node.key, node.data));
		} 
		if (k2.compareTo(node.key) > 0) { 
			getRange(node.right, k1, k2, list); 
		}
	}

	/**
	 * Display the BST using an in-order traversal
	 */
	public void printInOrder() {
		printInOrder(root);
	}

	/**
	 * Traverse the the BST from the given root using an in-order traversal
	 * @param node
	 */
	private void printInOrder(BSTNode<K, T> node) {
		if( node != null ) {
			printInOrder(node.left);
			System.out.println(node.key);
			printInOrder(node.right);
		}
	}

	// traversals from previous tree implementations
	private String preorder(BSTNode<K, T> node){
		if(node == null) return "".toString();
		else {		
			String left, right;
			left = preorder(node.left);
			right = preorder(node.right);
			return node + ", " + left + right;
		}
	}

	public String preorder() {
		String str = preorder(root);
		int end = Math.max(0, str.length()-2);
		return "[" + str.substring(0, end) + "]";
	}

	private String inorder(BSTNode<K, T> node){

		if(node == null) return "".toString();
		else {		
			String left, right;
			left = inorder(node.left);
			right = inorder(node.right);
			return left + node + ", " + right;
		}
	}

	public String inorder() {
		String str = inorder(root);
		int end = Math.max(0, str.length()-2);
		return "[" + str.substring(0, end) + "]";
	}

	private String postorder(BSTNode<K, T> node){

		if(node == null) return "".toString();
		else {		
			String left, right;
			left = postorder(node.left);
			right = postorder(node.right);
			return left + right + node + ", ";
		}
	}

	public String postorder() {
		String str = postorder(root);
		int end = Math.max(0, str.length()-2);
		return "[" + str.substring(0, end) + "]";
	}

}// End BST class

