
public class BSTNode<K extends Comparable<? super K>, T> implements Comparable<BSTNode<K, T>> {

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
	public int compareTo(BSTNode<K, T> other) {
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