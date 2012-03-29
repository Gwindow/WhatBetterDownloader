import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * The Class Settings.
 * 
 * //TODO description
 * 
 * @author Gwindow
 */
public class Settings {

	/** The prefs. */
	private static Preferences prefs;

	/** The set. */
	private static HashSet<Integer> torrentIdSet = new HashSet<Integer>();

	/**
	 * Instantiates a new user settings.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param monitoredForumIds
	 *            the monitored forum ids
	 * @param refreshRate
	 *            the refresh rate
	 */
	public Settings(String username, String password, int refreshRate) {
		prefs = Preferences.userNodeForPackage(this.getClass());
		this.saveUsername(username);
		this.savePassword(password);
		this.saveRefreshRate(refreshRate);
		torrentIdSet = loadSet();

	}

	/**
	 * Instantiates a new user settings.
	 */
	public Settings() {
		prefs = Preferences.userNodeForPackage(this.getClass());
		torrentIdSet = loadSet();
	}

	/**
	 * Load set.
	 * 
	 * @return the hash set
	 */
	private HashSet<Integer> loadSet() {
		HashSet<Integer> set = new HashSet<Integer>();
		String s = prefs.get("WhatBetterDownloader_set", "");
		s = s.replace("[", "").replace("]", "").replaceAll("\\s+", "");
		StringTokenizer st = new StringTokenizer(s, ",");
		while (st.hasMoreTokens()) {
			set.add(Integer.parseInt(st.nextToken()));
		}
		return set;
	}

	/**
	 * Adds the to set.
	 * 
	 * @param s
	 *            the s
	 */
	public static void addToSet(int s) {
		torrentIdSet.add(s);
		prefs.put("WhatBetterDownloader_setset", torrentIdSet.toString());

	}

	/**
	 * Clear settings.
	 * 
	 * @throws BackingStoreException
	 *             the backing store exception
	 */
	public void clearSettings() throws BackingStoreException {
		prefs.remove("WhatBetterDownloader_username");
		prefs.remove("WhatBetterDownloader_password");
		prefs.remove("WhatBetterDownloader_refreshRate");
		prefs.remove("WhatBetterDownloader_downloadLocation");
	}

	/**
	 * Flush set.
	 */
	public void flushSet() {
		prefs.remove("set");
	}

	/**
	 * Checks for settings.
	 * 
	 * @return true, if successful
	 */
	public boolean hasSettings() {
		if ((this.getUsername().length() > 0) && (this.getPassword().length() > 0))
			return true;
		return false;

	}

	/**
	 * String to integer list.
	 * 
	 * @param s
	 *            the s
	 * @return the array list
	 */
	private ArrayList<Integer> stringToIntegerList(String s) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		s = s.replace("[", "").replace("]", "").replaceAll("\\s+", "");
		StringTokenizer st = new StringTokenizer(s, ",");
		while (st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}
		return list;
	}

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return prefs.get("WhatBetterDownloader_username", "");
	}

	/**
	 * Save username.
	 * 
	 * @param username
	 *            the username
	 */
	public void saveUsername(String username) {
		prefs.put("WhatBetterDownloader_username", username);
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return prefs.get("WhatBetterDownloader_password", "");
	}

	/**
	 * Save password.
	 * 
	 * @param password
	 *            the password
	 */
	public void savePassword(String password) {
		prefs.put("WhatBetterDownloader_password", password);
	}

	/**
	 * Gets the refresh rate.
	 * 
	 * @return the refresh rate
	 */
	public int getRefreshRate() {
		return prefs.getInt("WhatBetterDownloader_refreshRate", 15);
	}

	/**
	 * Save refresh rate.
	 * 
	 * @param refreshRate
	 *            the refresh rate
	 */
	public void saveRefreshRate(int refreshRate) {
		prefs.putInt("WhatBetterDownloader_refreshRate", refreshRate);
	}

	/**
	 * Gets the sets the.
	 * 
	 * @return the sets the
	 */
	public static HashSet<Integer> getSet() {
		return torrentIdSet;
	}

	public void saveDownloadLocation(String string) {
		prefs.put("WhatBetterDownloader_downloadLocation", string);
	}

	public String getDownloadLocation() {
		return prefs.get("WhatBetterDownloader_downloadLocation", null);
	}

}