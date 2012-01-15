import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Class Downloader.
 */
public class Downloader implements Runnable {
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	@Override
	public void run() {
		System.out.println(getTime());
		System.out.println("\nSleeping\n");
	}

	private String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());

	}

}
