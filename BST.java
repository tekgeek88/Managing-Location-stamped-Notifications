public class BST<K extends Comparable<K>, T> implements Map<K, T> {

	BSTNode<K, T> root;
	BSTNode<K, T> current;
	int size;
	boolean isFull = false;

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
		if (current.data != null) {
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
		BSTNode<K, T> result;
		result = findNode(key);
		if ( key.compareTo(result.key) == 0) {
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
		prev = root;  	// Remember the previous node for insertion

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
		BSTNode<K, T> p = root;
		BSTNode<K, T> q = null; // Parent

		while (p != null) {
			if (k1.compareTo(p.key) < 0) {
				q = p;
				p = p.left;
			} else if (k1.compareTo(p.key) > 0) {
				q = p;
				p = p.right;
			}
			// found the key
			else {
				// Check the three cases
				if ((p.left != null) && (p.right != null)) {
					// Case 3: two children
					// Search for the min in the right subtree
					BSTNode<K, T> min = p.right;
					q = p;
					while(min.left != null) {
						q = min;
						min = min.left;
					}
					p.key = min.key;
					p.data = min.data;
					k1 = min.key;
					p = min;
				} // Now fall back to either case 1 or 2
				// The subtree rooted at p will change here
				// One child
				if (p.left != null) {
					p = p.left;
				}
				// One or no children
				else {
					p = p.right;
				}
				// No parent for p, root must change
				if (q == null) {
					root = p;
				}
				else {
					if (k1.compareTo(q.key) < 0) {
						q.left = p;
					} else {
						q.right = p;
					}
				}
				current = root;
				return true;
			}
		}
		return false; // Not found
	}

	//	private BSTNode<K, T> remove(K key, BSTNode<K, T> node) {
	//		if( node == null ) {
	//			return null;
	//		}
	//		if(key.compareTo(node.key ) < 0 ) {
	//			node.left = remove(key, node.left );
	//		} else if(key.compareTo( node.key) > 0 ) {
	//			node.right = remove(key, node.right );
	//		} else if(node.left != null && node.right != null ) {
	//			// Two children
	//			node.key = findMin(node.right).key;
	//			node.right = removeMin(node.right);
	//		}
	//		else {
	//			node = (node.left != null ) ? node.left : node.right;
	//		}
	//
	//		return node;
	//	}

	/**
	 * Remove min element from a subtree
	 * @param node the root node
	 * @return new root node
	 */
	private BSTNode<K, T> removeMin(BSTNode<K, T> node) {
		if (node == null) {
			return null;
		} else if (node.left != null) {
			node.left = removeMin(node.left);
			return node;
		} else {
			return node.right;
		}
	}

	/**
	 * Search a root node to find the smallest element
	 * Helper method used to remove nodes with children
	 * @param node
	 * @return
	 */
	private BSTNode<K, T> findMin(BSTNode<K, T> node) {
		if (node == null) {
			return null;
		}
		while(node.left != null) {
			node = node.left;
		}
		return node;
	}

	/** {@inheritDoc} */
	@Override
	public List<Pair<K, T>> getAll() {
		BSTNode<K, T> runner = root;
		List<Pair<K, T>> list = new LinkedList<Pair<K, T>>();
		getAll(runner, list);
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
			System.out.println("node.key: " + node.key);
		} 
		if (k2.compareTo(node.key) > 0) { 
			getRange(node.right, k1, k2, list); 
		}
	}

	/** {@inheritDoc} */
	@Override
	public int nbKeyComp(K k1, K k2) {
		BSTNode<K, T> runner = root;
		int count = 0;// = countInRange(runner, k1, k2, 0); 
		return count;
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

}// End BST class

