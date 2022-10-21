package libraries;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {

	static String customizedUrl = "org=";
	static String middleUrl = "&from_issue_date=";
	static String issueDate = "2019-01-01";
	static String tailUrl = "&size=500&page=";
	static String base = "https://diavgeia.gov.gr/opendata/search.json?";
	static int DEEDS_PER_PAGE = 500;

	public static Object retrieveAndReturn(String base, String customizedUrl) {
		return Util.retrieveAndReturn(base, customizedUrl, 0, 0);
	}

	public static Object retrieveAndReturn(String base) {
		return Util.retrieveAndReturn(base, "", 0, 0, false, "organizations");
	}

	public static Object retrieveAndReturn(String base, String customizedUrl, int pageMin, int pageMax) {
		/*
		 * Η συνάρτηση αυτή δέχεται ως παράμετρο το βασικό και ένα προσαρμοσμένο url,
		 * καθώς και το διάστημα σελίδων αποτελεσμάτων και με βάση αυτά κάνει κλήση στο
		 * api της διαύγειας για να καταμετρήσει στο σύνολο αυτό των δεδομένων, για ένα
		 * συγκεκριμένο χρονικό διάστημα, πόσες πράξεις με προσωπικά δεδομένα υπάρχουν
		 * επιστρέφοντας το πλήθος αυτό
		 */

		boolean privateData = false;
		return retrieveAndReturn(base, customizedUrl, pageMin, pageMax, privateData, "organizations");

	}

	private static Object retrieveAndReturn(String base, String customizedUrl, int pageMin, int pageMax,
			boolean privateData, String gettingItem) {
		// TODO Auto-generated method stub
		try {
			int count = 0;
			int totalDeeds = 0;
			ArrayList<String> organizations = new ArrayList<String>();
			HashMap<Integer, String> universtitiesUidsToLabels = new HashMap<Integer, String>();
			System.out.println("...connecting, wait..");
			for (int pages = pageMin; pages <= pageMax; pages++) {
				URL url = null;
				if (pages != 0) {
					url = new URL(base + customizedUrl + pages);
				} else {
					url = new URL(base + customizedUrl);
				}

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				System.out.println(url.toString());
				conn.setRequestMethod("GET");
				conn.connect();

				// Check if connect is made
				int responseCode = conn.getResponseCode();

				System.out.println("...αρχή ανάγνωσης.." + pages);
				// 200 OK
				if (responseCode != 200) {
					throw new RuntimeException("HttpResponseCode: " + responseCode);
				} else {

					Scanner scanner = new Scanner(url.openStream());
					String inline = "";
					while (scanner.hasNext()) {
						inline += scanner.nextLine();
					}
					// Close the scanner
					scanner.close();

					JSONParser parse = new JSONParser();
					JSONObject jobj = (JSONObject) parse.parse(inline);
					if (pages == 0) {

						JSONObject objInfo = (JSONObject) jobj.get("info");
						// System.out.println(objInfo.get("total"));
						// this.totalDeeds = (long) (objInfo.get("total"));
					}
					if (privateData) {
						count += Integer.valueOf((readData(jobj, "privateData")).toString());

					} else if (!privateData) {
						switch (gettingItem) {
						case "totalDeeds":
							JSONObject objInfo = (JSONObject) jobj.get("info");
							totalDeeds = (int) ((long) (objInfo.get("total")));
							break;
						case "organizations":
							organizations = (ArrayList<String>) Util.readData(jobj, "organizations");
							break;
						case "universtitiesUidsToLabels":
							universtitiesUidsToLabels = (HashMap<Integer, String>) Util.readData(jobj,
									"universtitiesUidsToLabels");
							break;
						default:
						}
					}
					System.out.println("...τέλος.." + pages);
				}

			}
			if (privateData) {
				return count;
			} else if (!privateData) {
				switch (gettingItem) {
				case "totalDeeds":
					return totalDeeds;
				case "organizations":
					return organizations;
				case "universtitiesUidsToLabels":
					return universtitiesUidsToLabels;
				default:
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public static List<Integer> returnMultipleUniversitiesUidsBasedOnLabels(List<String> selectedUniversitiesLabels) {
		HashMap<Integer, String> universtitiesUidsLabels = Util.retrieveAndReturnAllUniversitiesLabelsUids();
		List<Integer> selectedUniversitiesUids = new ArrayList<Integer>();
		for (Entry<Integer, String> setOfUniversity : universtitiesUidsLabels.entrySet()) {
			// Printing all elements of a Map
			System.out.println(setOfUniversity.getKey() + " = " + setOfUniversity.getValue());
			if (selectedUniversitiesLabels.contains(setOfUniversity.getValue())) {
				selectedUniversitiesUids.add(setOfUniversity.getKey());
			}
		}
		return selectedUniversitiesUids;
	}

	public static HashMap<Integer, String> retrieveAndReturnAllUniversitiesLabelsUids() {
		return (HashMap<Integer, String>) retrieveAndReturn(
				"https://diavgeia.gov.gr/opendata/organizations.json?category=UNIVERSITY", "", 0, 0, false,
				"universtitiesUidsToLabels");
	}

	public static HashMap<String, ArrayList<Integer>> retrieveAndReturnForMultipleUniversitiesDeedsAndPrivateData(
			List<String> selectedUniversities) {
		List<Integer> selectedUniversitiesUids = returnMultipleUniversitiesUidsBasedOnLabels(selectedUniversities);
		List<Integer> selectedUniversitiesTotalDeeds = new ArrayList<Integer>();
		List<Integer> selectedUniversitiesPagesToAccess = new ArrayList<Integer>();
		List<Integer> selectedUniversitiesDeedsWithPrivateData = new ArrayList<Integer>();
		HashMap<String, ArrayList<Integer>> infoForMultipleUniversities = new HashMap<String, ArrayList<Integer>>();
		for (int i = 0; i < selectedUniversitiesUids.size(); i++) {
			/*
			 * θα τραβήξω για κάθε πανεπιστήμιο την πρώτη σελίδα με 100 εγγραφές, ξεχωριστή
			 * επιλογή στην συνάρτηση για να πάρω τα total deeds και / 500 να βγάλω τα
			 * απαραίτητα pages - θα αποθηκεύω σε List τα total deeds - και τα pages,
			 * έχοντας διατηρημμένη την σειρά
			 * 
			 * τα οποία (pages) σε δεύτερο Loop θα τα στείλω για να πάρω τα private data,
			 * που θα βάλω σε νέο List
			 * 
			 * Άρα έχω τρία Lists τα οποία μπορώ να βάλω σε ένα νέο List ή σε array me
			 * warning και τα γυρνώ για να φτιάξω πλέον το γράφημα
			 */

			int pages = 0;
			// https://diavgeia.gov.gr/opendata/search.json?org=99206915&from_issue_date=2019-01-01&size=100&page=99206919&from_issue_date=2019-01-01&size=100&page=
			// WARNING
			System.out.println("selectedUniversitiesUids.get(i) "+selectedUniversitiesUids.get(i));
			int currentUniversityId = selectedUniversitiesUids.get(i);
			String customizedUrlTemp = customizedUrl + currentUniversityId + middleUrl + issueDate + "&size=100&page=";
			int totalDeeds = (Integer) Util.retrieveAndReturn(base, customizedUrlTemp, 0, pages, false, "totalDeeds");
			selectedUniversitiesTotalDeeds.add(totalDeeds);
			selectedUniversitiesPagesToAccess.add(totalDeeds / DEEDS_PER_PAGE);

			// int decisionsWithPrivateDataPage0 = Util.retrieveAndReturn(base,
			// customizedUrl, 0, pages); }
		}
		for (int i = 0; i < selectedUniversitiesUids.size(); i++) {
			int currentUniversityId = selectedUniversitiesUids.get(i);
			String customizedUrlTemp = customizedUrl + currentUniversityId + middleUrl + issueDate + tailUrl;
			int deedsWithPrivateData = (int) Util.retrieveAndReturn(base, customizedUrlTemp, 0,
					selectedUniversitiesPagesToAccess.get(i), true, null);
			selectedUniversitiesDeedsWithPrivateData.add(deedsWithPrivateData);
		}
		// infoForMultipleUniversities.put("selectedUniversitiesUids",
		// (ArrayList<Integer>) selectedUniversitiesUids);
		infoForMultipleUniversities.put("selectedUniversitiesTotalDeeds",
				(ArrayList<Integer>) selectedUniversitiesTotalDeeds);
		// infoForMultipleUniversities.put("selectedUniversitiesPagesToAccess",
		// (ArrayList<Integer>) selectedUniversitiesPagesToAccess);
		infoForMultipleUniversities.put("selectedUniversitiesDeedsWithPrivateData",
				(ArrayList<Integer>) selectedUniversitiesDeedsWithPrivateData);

		return infoForMultipleUniversities;
	}

	private static Object readData(JSONObject jobj, String gettingItem) {
		/*
		 * Η συνάρτηση αυτή δέχεται ως παράμετρο ένα αντικείμενο json με δομή
		 * συγκεκριμένη που είναι ένα array από json objects, που αντιστοιχούν σε
		 * πράξεις και επιστρέφει τον αριθμό των πράξεων του array που περιείχαν
		 * προσωπικά δεδομένα
		 */

		int countPrivateData = 0;
		ArrayList<String> organizations = new ArrayList<String>();
		HashMap<Integer, String> universtitiesUidsToLabels = new HashMap<Integer, String>();

		// getting decisions
		JSONArray ja = null;
		switch (gettingItem) {
		case "privateData":
			ja = (JSONArray) jobj.get("decisions");
			break;
		case "organizations":
			ja = (JSONArray) jobj.get("organizations");
			break;
		case "universtitiesUidsToLabels":
			ja = (JSONArray) jobj.get("organizations");
			break;
		default:
			ja = (JSONArray) jobj.get("organizations");
		}

		// iterating array of objects inside decisions, the decisions
		Iterator<JSONObject> itr = ja.iterator();

		while (itr.hasNext()) {
			JSONObject decision = (JSONObject) itr.next();

			switch (gettingItem) {
			case "privateData":
				boolean privateData = (boolean) decision.get("privateData");
				if (privateData)
					countPrivateData++;
				break;
			case "organizations":
				String organization = decision.get("label").toString();
				organizations.add(organization);
				break;
			case "universtitiesUidsToLabels":
				String universtityLabel = decision.get("label").toString();
				int universityUid = Integer.valueOf(decision.get("uid").toString());
				universtitiesUidsToLabels.put(universityUid, universtityLabel);
				break;
			default:
			}

		}

		switch (gettingItem) {
		case "privateData":
			return countPrivateData;
		case "organizations":
			return organizations;
		case "universtitiesUidsToLabels":
			return universtitiesUidsToLabels;
		default:
			return null;

		}
	}
}
