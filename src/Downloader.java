import java.text.SimpleDateFormat;
import java.util.Calendar;

import api.better.Better;
import api.better.Better.BetterTypes;

/**
 * The Class Downloader.
 */
public class Downloader implements Runnable {
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private String downloadLocation;

	public Downloader(final String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	@Override
	public void run() {
		System.out.println(getTime());
		Better better = Better.init(BetterTypes.SINGLE);
		better.downloadAll(downloadLocation);
		System.out.println("\nSleeping\n");
	}

	private String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

}
