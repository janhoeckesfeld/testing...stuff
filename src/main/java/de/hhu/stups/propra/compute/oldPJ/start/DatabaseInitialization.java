package de.hhu.stups.propra.compute.oldPJ.start;


import de.hhu.stups.propra.compute.services.DerbyCustomerRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseInitialization {

	public static void main(String[] args) {
		DerbyCustomerRepository customers = new DerbyCustomerRepository();
		System.out.println("Initializing database");
		customers.initDatabase();

		System.out.println("Populating database");

		ClassLoader classLoader = DatabaseInitialization.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream("customers.txt");
		List<String> mails = new BufferedReader(new InputStreamReader(stream,
				StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
		for (String mail : mails) {
			customers.addMailAddress(mail);
			System.out.println("Added: " + mail);
		}
		System.out.println("Done");
	}

}
