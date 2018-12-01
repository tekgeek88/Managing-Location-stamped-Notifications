public interface Map<K extends Comparable<K>, T> {

// Return true if the tree is empty. Must be O(1).
boolean empty();

// Return true if the tree is full. Must be O(1).
boolean full();

// Removes all elements in the map.
void clear();

// Return the data of the current element
T retrieve();

// Update the data of the current element.
void update(T e);

// Search for element with key k and make it the current element if it exists. If the element does not exist the current is unchanged and false is returned. This method must be O(log(n)) in average.
boolean find(K key);