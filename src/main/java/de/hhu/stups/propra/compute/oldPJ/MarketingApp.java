package de.hhu.stups.propra.compute.oldPJ;

import java.util.Collection;

public class MarketingApp {

	private static final int MILLIS_PER_SIX_HOURS = 3_600_000 * 6;
	private Thermometer weatherForecast;
	private MailService mailSender;
	private Customers kundenDB;

	public MarketingApp(Thermometer t, MailService m, Customers cr) {
		weatherForecast = t;
		mailSender = m;
		kundenDB = cr;
	}

	public void marketingLoop() throws InterruptedException {
		while (true) {
			doMarketing();
			Thread.sleep(MILLIS_PER_SIX_HOURS);
		}
	}

	public void doMarketing() {
		int temperature = weatherForecast.getCelsiusTemperature();
		if (temperature > 30) {
			Collection<String> kunden = kundenDB.getMailAddresses();
			for (String kunde : kunden) {
				mailSender.sendMail(kunde);
			}
		}
	}

}
