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
		        // use buffered reader to read line by line
		    	 buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), Charset.defaultCharset()));

		        double longitude, latitude; 
		        int maxNbRepeats, nbReapeats;
		        String locationName = null;
		        String line = null;
		        String[] fields = null;
		        // read line by line till end of file
		        while ((line = buffReader.readLine()) != null) {
		            // split each line based on regular expression having
		            // "any digit followed by one or more spaces".

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
		 try {
	            notWriter = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(fileName), StandardCharsets.UTF_8));
	            nots.toString();
	            
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
		Map<Double, LocNot> locNots = null;
		
//		Double currentLat = 
		
		
		List<Pair<Double, Map<Double, LocNot>>> list = nots.getAll();
		
		
		return null;
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
		return false;
	}

	// Return the list of notifications within a square of side dst (in meters) centered at the position (lat, lng) (it does not matter if the notification is active or not). Do not call Map.getAll().
	public static List<LocNot> getNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		return null;
	}

	// Return the list of active notifications within a square of side dst (in meters) centered at the position (lat, lng). Do not call Map.getAll().
	public static List<LocNot> getActiveNotsAt(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
		return null;
	}

	// Perform task of any active notification within a square of side dst (in meters) centered at the position (lat, lng) (call method perform). Do not call Map.getAll().
	public static void perform(Map<Double, Map<Double, LocNot>> nots, double lat, double lng, double dst) {
	}

	// Return a map that maps every word to the list of notifications in which it appears. The list must have no duplicates.
	public static Map<String, List<LocNot>> index(Map<Double, Map<Double, LocNot>> nots) {
		return null;
	}

	// Delete all notifications containing the word w.
	public static void delNots(Map<Double, Map<Double, LocNot>> nots, String w) {
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
