import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestLocNotManager {

	private Map<Double, Map<Double, LocNot>> nots;
	
	/**
	 * The @Before annotation is a method written once and called before each
	 * test case in a test class.
	 */
	@BeforeEach
	public void setUp() {
		nots = new BST<Double, Map<Double, LocNot>>();
	}

	
	@Test
	public void testGetAllNotsThreeElements() {
		try {
			
			LocNotManager.addNot(nots, new LocNot("Buy water", 24.75365, 46.62900, 0, 0));
			LocNotManager.addNot(nots, new LocNot("Buy water2", 24.75366, 46.62901, 0, 0));
			LocNotManager.addNot(nots, new LocNot("Buy water3", 24.75367, 46.62902, 0, 0));
			List<LocNot> l = LocNotManager.getAllNots(nots);
			LocNotManager.print(l);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testGetAllNotsEmptyList() {
		try {
			List<LocNot> l = LocNotManager.getAllNots(nots);
			LocNotManager.print(l);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testAddNot() {
		try {
			Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy water", 24.75365, 46.62900, 0, 0));
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testDelNot() {
		try {
			Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy water", 24.75365, 46.62900, 0, 0));
			LocNotManager.delNot(nots, 24.72294, 46.61838);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		try {
			Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy water", 24.75365, 46.62900, 0, 0));
			LocNotManager.save("output.txt", nots);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testLoad() {
		try {
			Map<Double, Map<Double, LocNot>> nots = LocNotManager.load("input.txt");
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNotsAt() {
		try {
			BST<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy bottled water", 24.75367, 46.62902, 0, 0));
			List<LocNot> l = LocNotManager.getNotsAt(nots, 24.72330, 46.63650, 100000);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetActiveNotsAt() {
		try {
			BST<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy bottled water", 24.75367, 46.62902, 0, 0));
			List<LocNot> l = LocNotManager.getActiveNotsAt(nots, 24.72330, 46.63650, 100000);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testLocNotManagerPerform() {
		try {
			BST<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.perform(nots, 24.72330, 46.63650, 100000);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testIndex() {
		try {
			Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy bottled water", 24.75367, 46.62902, 0, 0));
			Map<String, List<LocNot>> ind = LocNotManager.index(nots);
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteNots() {
		try {
			Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();
			LocNotManager.addNot(nots, new LocNot("Buy bottled water", 24.75367, 46.62902, 0, 0));
			LocNotManager.delNots(nots, "Buy");
		} catch (Exception e) {
			fail("Your program threw an exception.");
			e.printStackTrace();
		}
	}

}
