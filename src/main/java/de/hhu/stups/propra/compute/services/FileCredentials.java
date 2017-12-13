package de.hhu.stups.propra.compute.services;

import de.hhu.stups.propra.compute.oldPJ.Credentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileCredentials implements Credentials {

	private final String mailUser;
	private final String mailPassword;
	private final String weatherApiKey;

	private Properties loadCredentials() {
		Properties props = new Properties();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream stream = classLoader.getResourceAsStream("credentials.properties");
			props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public FileCredentials() {
		Properties properties = loadCredentials();
		mailUser = properties.getProperty("mail.user");
		mailPassword = properties.getProperty("mail.password");
		weatherApiKey = properties.getProperty("forecast.key");
	}

	@Override
	public String getMailUser() {
		return mailUser;
	}

	@Override
	public String getMailPassword() {
		return mailPassword;
	}

	@Override
	public String getWeatherApiKey() {
		return weatherApiKey;
	}

}
