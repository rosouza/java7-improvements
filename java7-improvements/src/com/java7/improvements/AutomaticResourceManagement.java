package com.java7.improvements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The try-with-resources statement is a try statement that declares one or more
 * resources. A resource is an object that must be closed after the program is
 * finished with it.
 * 
 * The try-with-resources statement ensures that each resource is closed at the
 * end of the statement. Any object that implements java.lang.AutoCloseable,
 * which includes all objects which implement java.io.Closeable, can be used as
 * a resource.
 * 
 * Note: A try-with-resources statement can have catch and finally blocks just
 * like an ordinary try statement. In a try-with-resources statement, any catch
 * or finally block is run after the resources declared have been closed.
 *
 * @see http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
 * @author The Java™ Tutorials
 * 
 */
public class AutomaticResourceManagement {

	/**
	 * The following example reads the first line from a file. It uses an
	 * instance of BufferedReader to read data from the file. BufferedReader is
	 * a resource that must be closed after the program is finished with it:
	 */
	public static String readFirstLineFromFile(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		}
	}

	/**
	 * You may declare one or more resources in a try-with-resources statement.
	 * The following example retrieves the names of the files packaged in the
	 * zip file zipFileName and creates a text file that contains the names of
	 * these files:
	 */
	public static void writeToFileZipFileContents(String zipFileName, String outputFileName) throws java.io.IOException {

		java.nio.charset.Charset charset = java.nio.charset.StandardCharsets.US_ASCII;
		java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);

		// Open zip file and create output file with
		// try-with-resources statement

		try (java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);
				java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)) {
			// Enumerate each entry
			for (java.util.Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name and write it to the output file
				String newLine = System.getProperty("line.separator");
				String zipEntryName = ((java.util.zip.ZipEntry) entries.nextElement()).getName() + newLine;
				writer.write(zipEntryName, 0, zipEntryName.length());
			}
		}
	}

	/**
	 * The following example uses a try-with-resources statement to
	 * automatically close a java.sql.Statement object:
	 */
	public static void viewTable(Connection con) throws SQLException {

		String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";

		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String coffeeName = rs.getString("COF_NAME");
				int supplierID = rs.getInt("SUP_ID");
				float price = rs.getFloat("PRICE");
				int sales = rs.getInt("SALES");
				int total = rs.getInt("TOTAL");

				System.out.println(coffeeName + ", " + supplierID + ", " + price + ", " + sales + ", " + total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
