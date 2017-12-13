package de.hhu.stups.propra.compute.oldPJ.start;

import de.hhu.stups.propra.compute.oldPJ.*;
import de.hhu.stups.propra.compute.services.DerbyCustomerRepository;
import de.hhu.stups.propra.compute.services.FileCredentials;
import de.hhu.stups.propra.compute.services.GMailSender;
import de.hhu.stups.propra.compute.services.OpenWeatherMapForecast;


public class Startup {

	public static void main(String[] args) throws Exception {

		Credentials credentials = new FileCredentials();
		MailService mailer = new GMailSender(credentials);
		Thermometer therometer = new OpenWeatherMapForecast(credentials);
		Customers customers = new DerbyCustomerRepository();

		MarketingApp app = new MarketingApp(therometer, mailer, customers);
		app.marketingLoop();

	}

}
