package com.java7.improvements;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/**
 * Try-with-resources statement to automatically close resources of
 * typeConnection,ResultSet, andStatement
 * 
 * RowSet 1.1 introduces RowSetFactoryand RowSetProvider
 *
 * @see https://today.java.net/today/2004/10/15/jdbcRowsets.pdf
 * @author Maydene Fisher
 * 
 */
public class JDBC4 {

	public static void main(String[] args) {

		RowSetFactory myRowSetFactory = null;
		JdbcRowSet jdbcRs = null;

		try {
			myRowSetFactory = RowSetProvider.newFactory();
			jdbcRs = myRowSetFactory.createJdbcRowSet();

			jdbcRs.setUrl("jdbc:myDriver:myAttribute");
			jdbcRs.setUsername("username");
			jdbcRs.setPassword("password");

			jdbcRs.setCommand("SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
			jdbcRs.execute();
			updatingColumnValues(jdbcRs);
			insertinRows(jdbcRs);
			deletingRows(jdbcRs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * A JdbcRowSet object can call the method next , as seen in the preceding
	 * code fragment, and it can also call any of the other ResultSet cursor
	 * movement methods. For example, the following lines of code move the
	 * cursor to the fourth row in jdbcRs and then to the third row.
	 * 
	 */
	public static void navigatingJdbcRowSet(JdbcRowSet jdbcRs) throws SQLException {

		while (jdbcRs.next()) {
			String name = jdbcRs.getString("COF_NAME");
			BigDecimal price = jdbcRs.getBigDecimal("PRICE");
			System.out.println(name + " " + price);
		}

		jdbcRs.absolute(4);
		jdbcRs.previous();

		/**
		 * The method previous is analogous to the method next in that it can be
		 * used in a while loop to traverse all of the rows in order. The
		 * difference is that you must move the cursor to after the last row,
		 * and previous moves the cursor toward the beginning.
		 * 
		 */
		jdbcRs.afterLast();
		while (jdbcRs.previous()) {
			String name = jdbcRs.getString("COF_NAME");
			BigDecimal price = jdbcRs.getBigDecimal("PRICE");
			System.out.println(name + " " + price);
		}
	}

	public static void updatingColumnValues(JdbcRowSet jdbcRs) throws SQLException {
		jdbcRs.absolute(3);
		jdbcRs.updateFloat("PRICE", 10.99f);
		jdbcRs.updateRow();
	}

	/**
	 * When you call the method insertRow, the new row is inserted into jdbcRs
	 * and is also inserted into the database. The preceding code fragment goes
	 * through this process twice, so two new rows are inserted into jdbcRs and
	 * the database.
	 * 
	 */
	public static void insertinRows(JdbcRowSet jdbcRs) throws SQLException {
		jdbcRs.moveToInsertRow();
		jdbcRs.updateString("COF_NAME", "House_Blend");
		jdbcRs.updateInt("SUP_ID", 49);
		jdbcRs.updateBigDecimal("PRICE", new BigDecimal("7.99"));
		jdbcRs.updateInt("SALES", 0);
		jdbcRs.updateInt("TOTAL", 0);
		jdbcRs.insertRow();
		jdbcRs.moveToCurrentRow();

		jdbcRs.moveToInsertRow();
		jdbcRs.updateString("COF_NAME", "House_Blend_Decaf");
		jdbcRs.updateInt("SUP_ID", 49);
		jdbcRs.updateBigDecimal("PRICE", new BigDecimal("8.99"));
		jdbcRs.updateInt("SALES", 0);
		jdbcRs.updateInt("TOTAL", 0);
		jdbcRs.insertRow();
		jdbcRs.moveToCurrentRow();
	}

	/**
	 * In the following lines of code, the first line moves the cursor to the
	 * last row, and the second line deletes the last row from jdbcRs and from
	 * the database.
	 * 
	 */
	public static void deletingRows(JdbcRowSet jdbcRs) throws SQLException {
		jdbcRs.last();
		jdbcRs.deleteRow();
	}

	/**
	 * Usage of try-with-resources Statement: In JDBC 4.1, the statement
	 * try-with-resources can be used to close the JDBC resources automatically.
	 * Using this try-with-resources, the objects of type java.sql.connection,
	 * java.sql.ResultSet and java.sql.statement can be automatically closed.
	 * 
	 * In this Java code snippet, a method called sampleQueryProcessing
	 * processes a query to retrieve records from STUDENT table of Oracle
	 * database. Notice the difference in try statement.&nbsp;
	 * 
	 * The try statement try (Statement sampleStmt =
	 * sampleCon.createStatement()); is an example of the try-with-resources
	 * statement in JDBC 4.1. In this line of code, a new Statement instance
	 * called sampleStmt is created within the try statement. When the try block
	 * terminates, the object sampleStmt will be automatically closed.&nbsp;
	 * 
	 *
	 */
	public static void sampleQueryProcessing(Connection sampleCon) throws SQLException {
		String sampleQuery = "select ROLLNO, NAME, ADDRESS from STUDENT";
		try (Statement sampleStmt = sampleCon.createStatement()) {
			ResultSet rs = sampleStmt.executeQuery(sampleQuery);
			while (rs.next()) {
				int rollNo = rs.getInt("ROLLNO");
				String studentName = rs.getString("NAME");
				String studentAddress = rs.getString("ADDRESS");
			}
		}
	}

}
