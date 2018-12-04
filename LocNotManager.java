import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class LocNotManager {

	/**
	 *  Load notifications from file. Assume format is correct. The notifications are
	 *  indexed by latitude then by longitude.
	 * @param fileName
	 * @return
	 */
	public static Map<Double, Map<Double, LocNot>> load(String fileName) {
		BufferedReader buffReader = null;
		Map<Double, Map<Double, LocNot>> nots = new BST<Double, Map<Double, LocNot>>();

		try {

			buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.defaultCharset()));

			double longitude, latitude; 
			int maxNbRepeats, nbReapeats;
			String locationName = null;
			String line = null;
			String[] fields = null;

			while ((line = buffReader.readLine()) != null) {

				fields = line.split("\t");

				latitude = Double.valueOf(fields[0].trim());
				longitude = Double.valueOf(fields[1].trim());
				maxNbRepeats = Integer.valueOf(fields[2].trim());
				nbReapeats = Integer.valueOf(fields[3].trim());
				locationName = fields[4].trim();

				LocNotManager.addNot(nots, new LocNot(locationName, latitude, longitude, maxNbRepeats, nbReapeats));
			}
		} catch (IOException e) {
			System.err.println("Exception:" + e.toString());
		} finally {
			if (buffReader != null) {
				try {
					buffReader.close();
				} catch (IOException e) {
					System.err.println("Exception:" + e.toString());
				}
			}
		}
		return nots;
	}

	/**
	 *  Save notifications to file.
	 * @param fileName
	 * @param nots
	 */
	public static void save(String fileName, Map<Double, Map<Double, LocNot>> nots) {
		BufferedWriter notWriter = null;
		List<LocNot> allNots = null; 
		try {
			notWriter = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(fileName), StandardCharsets.UTF_8));

			allNots = getAllNots(nots);

			while(!allNots.empty()) {
				notWriter.write(allNots.retrieve().toString() + "\n");
				allNots.remove();
			}
			notWriter.close();
		}
		catch (IOException e) {
			System.out.println("Error writing codes to file!");
		}
	}

	/**
	 *  Return all notifications sorted first by latitude then by longitude.
	 * @param nots
	 * @return
	 */
	public static List<LocNot> getAllNots(Map<Double, Map<Double, LocNot>> nots) {

		// A LinkedList to store all of the notifications into
		List<LocNot> allNots = new LinkedList<LocNot>();

		// Fetch a List of all the given latitudes as entries
		List<Pair<Double, Map<Double, LocNot>>> entryLatitude = nots.getAll();

		// {key: Longitude, Value: LocNot}
		List<Pair<Double, LocNot>> entryLocation = null;

		LocNot currentLocation;

		while (!entryLatitude.empty()) {
			entryLocation = entryLatitude.retrieve().second.getAll();
			entryLatitude.remove();

			while (!entryLocation.empty()) {
				currentLocation = entryLocation.retrieve().second;
				allNots.insert(currentLocation);
				entryLocation.remove();
			}

		}
		allNots.findFirst();
		return allNots;
	}

	/**
	 *  Add a notification. Returns true if insert took place, false otherwise.
	 * @param nots
	 * @param not
	 * @return
	 */
	public static boolean addNot(Map<Double, Map<Double, LocNot>> nots, LocNot not) {
		boolean wasInserted = false;
		Map<Double, LocNot> locNots = null;

		// retrieve a Map of location notifications for the given latitude
		if (nots.find(not.getLat())) {
			locNots = nots.retrieve();
			wasInserted = locNots.insert(not.getLng(), not);
		}
		// No locNots for the given latitude exist
		else {
			locNots = new BST<Double, LocNot>();
			locNots.insert(not.getLng(), not);
			nots.insert(not.getLat(), locNots);
			wasInserted = true;
		} 

		return wasInserted;
	}

	// Delete the notification at (lat, lng). Returns true if delete took place, false otherwise.
	public static boolean delNot(Map<Double, Map<Double, LocNot>> nots, double lat, double lng) {
		boolean wasDeleted = false;
		// Search for the latitude and update the current element to point at it
		if (nots.find(lat)) {
			// Fetch all the locations belonging to that latitude
			Map<Double, LocNot> currentLat = nots.retrieve();
			// Remove longitude
			wasDeleted = currentLat.remove(lng);
		}
		return wasDeleted;
	}

	// Return the list of notifications within a square of side dst (in meters) centered at the position (lat, lng) (it does not matter if the notification is active or not). Do not call Map.getAll().
	public static List<LocNot> getNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		dst *= 0.5; // divide distance by two. 
		double lowLat = lat - dst;
		double highLat = lat + dst;
		double lowLng = lng - dst;
		double highLng = lng + dst;
		// A LinkedList to store all of the notifications into
		List<LocNot> allNots = new LinkedList<LocNot>();

		// Fetch a List of all the given latitudes within the given range.
		List<Pair<Double, Map<Double, LocNot>>> entryLatitude = nots.getRange(lowLat, highLat);

		// {key: Longitude, Value: LocNot}
		List<Pair<Double, LocNot>> entryLocation = null;

		Double currentLongitude;
		LocNot currentLocation;

		while (!entryLatitude.empty()) {
			entryLocation = entryLatitude.retrieve().second.getAll();
			entryLatitude.remove();

			while (!entryLocation.empty()) {
				currentLongitude = entryLocation.retrieve().first;
				if( currentLongitude.compareTo(lowLng) >= 0 && currentLongitude.compareTo(highLng) <= 0 ) {
					currentLocation = entryLocation.retrieve().second;
					allNots.insert(currentLocation);		
				}
				entryLocation.remove();
			}
		}
		allNots.findFirst();
		return allNots;
	}

	// Return the list of active notifications within a square of side dst (in meters) centered at the position (lat, lng). Do not call Map.getAll().
	public static List<LocNot> getActiveNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		dst *= 0.5; // divide distance by two. 
		double lowLat = lat - GPS.angle( dst);
		double highLat = lat + GPS.angle( dst);
		double lowLng = lng - GPS.angle( dst);
		double highLng = lng + GPS.angle( dst);

		// A LinkedList to store all of the notifications into
		List<LocNot> allNots = new LinkedList<LocNot>();

		// Fetch a List of all the given latitudes within the given range.
		List<Pair<Double, Map<Double, LocNot>>> entryLatitude = nots.getRange(lowLat , highLat);

		// {key: Longitude, Value: LocNot}
		List<Pair<Double, LocNot>> entryLocation = null;

		Double currentLongitude;
		LocNot currentLocation;

		while (!entryLatitude.empty()) {
			entryLocation = entryLatitude.retrieve().second.getAll();
			entryLatitude.remove();

			while (!entryLocation.empty()) {
				currentLongitude = entryLocation.retrieve().first;
				if( currentLongitude.compareTo(lowLng) >= 0 && currentLongitude.compareTo(highLng) <= 0) {
					currentLocation = entryLocation.retrieve().second;
					if(currentLocation.isActive()) {
						allNots.insert(currentLocation);		
					}
				}
				entryLocation.remove();
			}
		}
		allNots.findFirst();
		return allNots;
	}

	// Perform task of any active notification within a square of side dst (in meters) centered at the position (lat, lng) (call method perform). Do not call Map.getAll().
	public static void perform(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		List<LocNot> allNots = new LinkedList<LocNot>();
		allNots = getActiveNotsAt(nots, lat, lng, dst);
		while(!allNots.empty()) {
			allNots.retrieve().perform();
			allNots.remove();
		}
	}

	// Return a map that maps every word to the list of notifications in which it appears.
	// The list must have no duplicates.
	public static Map<String, List<LocNot>> index(Map<Double, Map<Double, LocNot>> nots) {
		
		// A map of words
		Map<String, List<LocNot>> wordMap = new BST<String, List<LocNot>>();
		
		//		Map<String, Map<Double, Map<Double, LocNot>>> mapster = new BST<String, Map<Double, Map<Double, LocNot>>>();
		
		// Fetch a List of all possible latitudes as entries
		List<Pair<Double, Map<Double, LocNot>>> entryLatitude = nots.getAll();
		List<Pair<Double, LocNot>> entryLocations = null;
		LocNot currentLocation;

		while (!entryLatitude.empty()) {
			entryLocations = entryLatitude.retrieve().second.getAll();
			entryLatitude.remove();

			while (!entryLocations.empty()) {
				currentLocation = entryLocations.retrieve().second;
				String[] words = currentLocation.getText().split(" ");
				// For each word in each location
				
				for (String s: words) {
					// If the current word already existed in the wordMap
					// retrieve the list and append to it
					if (wordMap.find(s)) {
						List<LocNot> currentList = wordMap.retrieve();
						List<LocNot> newList = new LinkedList<LocNot>();
						
						currentList.insert(currentLocation);
					} else {
						List<LocNot> newList = new LinkedList<LocNot>();
						newList.insert(currentLocation);
						wordMap.insert(s, newList);
					}
				}
				entryLocations.remove();
			}
		}
		return wordMap;
	}

	// Delete all notifications containing the word w.
	public static void delNots(Map<Double, Map<Double, LocNot>> nots, String w) {
		Map<String, List<LocNot>> wordMap = index(nots);
		if (wordMap.find(w)) {
			List<LocNot> locations = wordMap.retrieve();
			while(!locations.empty()) {
				LocNot currentLoaction = locations.retrieve(); 
				delNot(nots, currentLoaction.getLat(), currentLoaction.getLng());
				locations.remove();
			}
		}
	}

	// Print a list of notifications in the same format used in file.
	public static void print(List<LocNot> l) {
		System.out.println("-------------------------------------------------------------------------------------");
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve());
				l.findNext();
			}
			System.out.println(l.retrieve());
		} else {
			System.out.println("Empty");
		}
		System.out.println("------------------");
	}

	// Print an index.
	public static void print(Map<String, List<LocNot>> ind) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		List<Pair<String, List<LocNot>>> l = ind.getAll();
		if (!l.empty()) {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve().first);
				print(l.retrieve().second);
				l.findNext();
			}
			System.out.println(l.retrieve().first);
			print(l.retrieve().second);
		} else {
			System.out.println("Empty");
		}
		System.out.println("++++++++++++++++++");
	}

}
