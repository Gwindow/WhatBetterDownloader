import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;

import api.soup.MySoup;
import api.util.CouldNotLoadException;

/**
 * The Class Main.
 * 
 * @author Gwindow
 */
public class Main {
	/** The Constant SITE. */
	private final static String SITE = "http://what.cd/";

	/** The executor. */
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	/** The user settings. */
	private Settings settings = new Settings();

	/** The scanner. */
	private Scanner scanner = new Scanner(System.in);

	/** The password. */
	private String username, password;

	/** The refresh rate. */
	private int refreshRate;;

	/**
	 * Instantiates a new main.
	 * 
	 * @param args
	 *            the args
	 */
	public Main(String[] args) {
		readOptions(args);
	}

	/**
	 * Read options.
	 * 
	 * @param args
	 *            the args
	 */
	private void readOptions(String[] args) {
		MySoup.setSite(SITE);
		if (args.length > 0) {
			if (args[0].trim().equalsIgnoreCase("-h") || args[0].trim().equalsIgnoreCase("-help")) {
				showHelp();
				System.exit(0);

			}
			if (args[0].trim().equalsIgnoreCase("-c") || args[0].trim().equalsIgnoreCase("-clear")) {
				try {
					settings.clearSettings();
					System.out.println("Settings cleared");
				} catch (BackingStoreException e) {
					System.err.println("Could not clear settings");
				}
				System.exit(0);

			}
			if (args[0].trim().equalsIgnoreCase("-f") || args[0].trim().equalsIgnoreCase("-flush")) {
				settings.flushSet();
				System.out.println("Torerent id list flushed");
				System.exit(0);
			}
			if (args[0].trim().equalsIgnoreCase("-p") || args[0].trim().equalsIgnoreCase("-print")) {
				System.out.println(Settings.getSet().toString());
				System.exit(0);
			}
			if (args[0].trim().equalsIgnoreCase("-r") || args[0].trim().equalsIgnoreCase("-run")) {
				configureSettings();
				startDownloaderThread();
			}

		} else {
			configureSettings();
			startDownloaderThread();
		}
	}

	/**
	 * Configure settings.
	 */
	private void configureSettings() {
		if (!settings.hasSettings()) {
			login(false);
			chooseRefereshRate();
			saveSettings();
		} else {
			try {
				settings = new Settings();
				login(true);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Could not load settings");
				System.exit(0);
			}
		}
	}

	/**
	 * Login.
	 * 
	 * @param auto
	 *            the auto
	 */
	private void login(boolean auto) {
		if (auto) {
			try {
				MySoup.login("login.php", settings.getUsername(), settings.getPassword());
				System.out.println("Logged in \n");
			} catch (CouldNotLoadException e) {
				System.err.println("Could not login");
			}
		} else {
			while (true) {
				try {
					System.out.println("Enter username:");
					username = scanner.nextLine();
					System.out.println("Enter password:");
					password = PasswordField.readPassword("");
					break;
				} catch (Exception e) {
					System.err.println("Error parsing input");
				}
			}
			try {
				MySoup.login("login.php", username, password);
			} catch (CouldNotLoadException e) {
				System.err.println("Could not login");
				System.exit(0);
			}
			System.out.println("\nLogged in\n");
		}
	}

	/**
	 * Choose referesh rate.
	 */
	private void chooseRefereshRate() {
		while (true) {
			try {
				System.out.println("How often should better.php be checked (in minutes)? Minimum is 15 minutes. For example: 30");
				refreshRate = scanner.nextInt();
				if (refreshRate < 15) {
					System.err.println("Minimum rate is 15 minutes");
				} else {
					break;
				}
			} catch (Exception e) {
				System.err.println("Error parsing input");
			}
		}
		System.out.println();
	}

	/**
	 * Save settings.
	 */
	private void saveSettings() {
		try {
			settings.savePassword(password);
			settings.saveUsername(username);
			settings.saveRefreshRate(refreshRate);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not save settings");
			System.exit(0);
		}
	}

	/**
	 * Show help.
	 */
	private void showHelp() {
		System.out.println("WhatBetterDownloader - version 0.1 \n");
		System.out.println("List of options\n");
		System.out.println("-c clear \t clears the user settings");
		System.out.println("-f flush \t flushes the list of torrent ids, don't use this if you don't know what you are doing");
		System.out.println("-p print \t prints the list of saved torrent ids, not for the faint of the heart");
		System.out.println("-r run \t\t run program, if no option is included this is done by default");
		System.out.println("-h help \t show the help menu");
		System.out.println();

	}

	/**
	 * Start downloader thread.
	 */
	private void startDownloaderThread() {
		executor.scheduleAtFixedRate(new Downloader(), 0, settings.getRefreshRate(), TimeUnit.MINUTES);

	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Main(args);
	}

}
