
public class BSTNode<Key extends Comparable<? super Key>, Data> implements Comparable<BSTNode<Key, Data>> {

	Key key;
	Data data;

	BSTNode<Key, Data> left;
	BSTNode<Key, Data> right;


	BSTNode() {
		this(null, null, null, null);
	}

	BSTNode(Key key, Data data) {
		this(key, data, null, null);
	}

	BSTNode(Key key, Data value, BSTNode<Key, Data> left, BSTNode<Key, Data> right) {
		this.key = key;
		this.data = value;
		this.left = left;
		this.right = right;
	}		

	public boolean isLeaf() {
		return left == null && right == null;
	}

	@Override
	public int compareTo(BSTNode<Key, Data> other) {
		if (other != null) {
			return key.compareTo(other.key);
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