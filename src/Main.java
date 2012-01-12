import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;

import api.soup.MySoup;

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
	private String username, password;;

	public Main(String[] args) {
		readOptions(args);
	}

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
				System.out.println("Thread list flushed");
				System.exit(0);
			}
			if (args[0].trim().equalsIgnoreCase("-p") || args[0].trim().equalsIgnoreCase("-print")) {
				System.out.println(Settings.getSet().toString());
				System.exit(0);
			}
			if (args[0].trim().equalsIgnoreCase("-r") || args[0].trim().equalsIgnoreCase("-run")) {
				configureSettings();
				startForumCheckerThread();
			}
			if (args[0].trim().equalsIgnoreCase("-s") || args[0].trim().equalsIgnoreCase("-stats")) {
				if (!settings.hasSettings()) {
					login(false);
				} else {
					login(true);
				}
				showUserStats();
				System.exit(0);
			}

		} else {
			configureSettings();
			startForumCheckerThread();
		}
	}

	/**
	 * Start downloader thread
	 */
	private void startDownloaderThread() {
		executor.scheduleAtFixedRate(new Downloader(), 0, settings.getRefreshRate(), TimeUnit.MINUTES);

	}

	public static void main(String[] args) {
		new Main(args);
	}

}
