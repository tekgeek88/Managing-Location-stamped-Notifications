import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBST {

	private BST<Integer, Integer> testBST;

	public static Random random;

	ArrayList<Integer> randomNumbers;

	/**
	 * The @Before annotation is a method written once and called before each
	 * test case in a test class.
	 */
	@BeforeEach
	public void setUp() {
		testBST = new BST<Integer, Integer>();
		random = new Random();
	}


	@Test
	public void testEmptyAsConstructed() {
		assertTrue(testBST.empty(), "The BST should be empty when first constructed!");
	}

	@Test
	public void testNotEmptyAfterInsert() {
		testBST.insert(5, 5);
		assertFalse(testBST.empty(), "The BST should be empty after an element is inserted!");
	}


	@Test
	public void testIsEmptyAfterInsertThenDelete() {
		testBST.insert(5, 5);
		testBST.remove(5);
		assertTrue(testBST.empty(), "The BST should be empty after an element is inserted!");
	}


	@Test
	public void testEmptyAfterInsert10ThenDelete10() {
		int[] numbers = {10, 4, 6, 2, 5, 1, 3, 8, 9, 7};
		int[] numbersReArranged = {9, 4, 3, 8, 1, 7, 5, 6, 10, 2};

		for (int i = 0; i < numbers.length; i++) {
			testBST.insert(numbers[i], numbers[i]);
		}

		for (int i = 0; i < numbers.length; i++) {
			testBST.remove(numbersReArranged[i]);
		}

		assertTrue(testBST.empty(), "The BST should be empty after an 10 insertions and 10 deletions!");
	}


	@Test
	public void testRetrieveSingleValue() {
		try {
			testBST.insert(12, 12);
			int actual = testBST.retrieve();
			assertEquals("inserted 12", 12, actual);
		} catch (Exception e) {
			fail("Your program threw an exception.");
		}
	}

	@Test
	public void testUpdate() {
		try {
			testBST.insert(100, 100);
			testBST.update(55);
			int actual = testBST.retrieve();
			assertEquals("inserted 100 -> updated 55", 55, actual);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testFull() {
		try {
			testBST.insert(100, 100);
			boolean f = testBST.full();
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testClear() {
		try {
			Map<Integer, Integer> bst = new BST<Integer, Integer>();
			bst.insert(100, 100);
			bst.clear();
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testFindListOf10Rearranged() {
		try {
			int[] numbers = {10, 4, 6, 2, 5, 1, 3, 8, 9, 7};
			int[] numbersReArranged = {9, 4, 3, 8, 1, 7, 5, 6, 10, 2};

			for (int i = 0; i < numbers.length; i++) {
				testBST.insert(numbers[i], numbers[i]);
			}

			for (int i = 0; i < numbersReArranged.length; i++) {
				boolean f = testBST.find(numbersReArranged[i]);
				assertTrue(f, "Trying to find " + numbersReArranged[i]);
			}

		} catch (Exception e) {
			fail("Your program threw an exception.");
		}
	}

	@Test
	public void testNbKeyComp() {
		try {
			Map<Integer, Integer> bst = new BST<Integer, Integer>();
			bst.insert(100, 100);
			int n = bst.nbKeyComp(100);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert() {
		try {
			Map<Integer, Integer> bst = new BST<Integer, Integer>();
			boolean f = bst.insert(100, 100);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testRemove10() {
		int[] numbers = {10, 4, 6, 2, 5, 1, 3, 8, 9, 7};
		int[] numbersReArrangedPlusOne = {9, 4, 3, 8, 1, 7, 5, 6, 10, 2};

		// Insert 10 numbers
		for (int i = 0; i < numbers.length; i++) {
			testBST.insert(numbers[i], numbers[i]);
		}

		try {
			for (int i = 0; i < numbersReArrangedPlusOne.length; i++) {
				boolean wasRemoved = testBST.remove(numbersReArrangedPlusOne[i]);
				assertTrue(wasRemoved, "After removing " + numbersReArrangedPlusOne[i]);
				boolean wasFound = testBST.find(numbersReArrangedPlusOne[i]);
				assertFalse(wasFound, "Trying to find " + numbersReArrangedPlusOne[i]);
			}
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveAnExtra() {
		int[] numbers = {10, 4, 6, 2, 5, 1, 3, 8, 9, 7};
		int[] numbersReArrangedPlusOne = {9, 4, 3, 8, 1, 7, 5, 6, 10, 2};

		// Insert 10 numbers
		for (int i = 0; i < numbers.length; i++) {
			testBST.insert(numbers[i], numbers[i]);
		}

		try {
			// Remove 11
			for (int i = 0; i < numbersReArrangedPlusOne.length; i++) {
				testBST.remove(numbersReArrangedPlusOne[i]);
			}
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAll() {
		try {
			Map<Integer, Integer> bst = new BST<Integer, Integer>();
			bst.insert(10, 10);
			List<Pair<Integer, Integer>> l = bst.getAll();
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRange() {
		try {
			Map<Integer, Integer> bst = new BST<Integer, Integer>();
			List<Pair<Integer, Integer>> l = bst.getRange(10, 2);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			return -1;
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
