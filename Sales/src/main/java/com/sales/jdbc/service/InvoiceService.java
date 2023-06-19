package com.sales.jdbc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.sales.jdbc.config.InvoiceConfiguration;
import com.sales.jdbc.model.Invoice;

@Configuration
public class InvoiceService {

	@Autowired
	private InvoiceConfiguration invoiceConfiguration;

	PreparedStatement myStmt = null;
	ResultSet myRs = null;
	Connection myConn = null;

	public List<Invoice> getInvoices() {

		try {

//            throw new SQLException("Simulated exception");

			myConn = invoiceConfiguration.dataSource().getConnection();
			myConn.setAutoCommit(false);
			List<Invoice> invoices = new ArrayList<>();
			String sql = "select * from invoice order by invoice_no";

			myStmt = myConn.prepareStatement(sql);
			myRs = myStmt.executeQuery(sql);

			while (myRs.next()) {
				int id = myRs.getInt("id");
				String receiver = myRs.getString("receiver");
				Double amount = myRs.getDouble("amount");
				int invoiceNo = myRs.getInt("invoice_no");
				Invoice invoice = new Invoice(id, invoiceNo, receiver, amount,
						new java.util.Date(myRs.getDate("invoice_date").getTime()));
				invoices.add(invoice);
			}

			myConn.commit();
			return invoices;

		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			try {
				if (myConn != null)
					myConn.rollback();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return null;
	}

	public Invoice getInvoiceById(int id) throws Exception {

		Invoice theInvoice = null;
		myConn = invoiceConfiguration.dataSource().getConnection();
		myConn.setAutoCommit(false);
		String checkSql = "SELECT COUNT(*) FROM invoice WHERE id = ?";
		PreparedStatement checkStmt = myConn.prepareStatement(checkSql);
		checkStmt.setInt(1, id);
		ResultSet resultSet = checkStmt.executeQuery();
		resultSet.next();
		int rowCount = resultSet.getInt(1);

		if (rowCount == 0) {
			throw new Exception("No Invoice Found with ID: " + id);
		}

		try {
			String sql = "select * from invoice where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				String receiver = myRs.getString("receiver");
				Double amount = myRs.getDouble("amount");
				int invoiceNo = myRs.getInt("invoice_no");

				theInvoice = new Invoice(id, invoiceNo, receiver, amount,
						new java.util.Date(myRs.getDate("invoice_date").getTime()));
			} else {
				throw new Exception("Could not find invoice id: " + id);
//	            throw new SQLException("Simulated exception");

			}
			myConn.commit();
			return theInvoice;
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			if (myConn != null) {
				try {
					myConn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
			throw se; // Rethrow the SQLException

		} finally {
			close(myConn, myStmt, myRs);
		}
	}

	public Invoice updateInvoice(Invoice invoice, int id) throws Exception {

		try {

			myConn = invoiceConfiguration.dataSource().getConnection();
			myConn.setAutoCommit(false);
			String checkSql = "SELECT COUNT(*) FROM invoice WHERE id = ?";
			PreparedStatement checkStmt = myConn.prepareStatement(checkSql);
			checkStmt.setInt(1, id);
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			int rowCount = resultSet.getInt(1);

			if (rowCount == 0) {
				throw new Exception("No Invoice Found with ID: " + id);
			}

			String sql = "update invoice set invoice_No= ?,receiver= ?, amount =? where id = ?;";
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, invoice.getInvoiceNo());
			myStmt.setString(2, invoice.getReceiver());
			myStmt.setDouble(3, invoice.getAmount());
			myStmt.setInt(4, id);
			myStmt.execute();

			myConn.commit();
			return invoice;

		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			if (myConn != null) {
				try {
					myConn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
			throw se; // Rethrow the SQLException

		} finally {
			close(myConn, myStmt, null);
		}
	}

	public Invoice insertInvoice(Invoice invoice) throws Exception {

		try {
			myConn = invoiceConfiguration.dataSource().getConnection();
			myConn.setAutoCommit(false);
			String sql = "insert into invoice" + "(amount, invoice_date, invoice_no , receiver)"
					+ "  values (?, ?, ? ,?)";

			myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			myStmt.setDouble(1, invoice.getAmount());
			myStmt.setDate(2, new java.sql.Date(invoice.getInvoiceDate().getTime()));
			myStmt.setInt(3, invoice.getInvoiceNo());
			myStmt.setString(4, invoice.getReceiver());
			int rowCount = myStmt.executeUpdate();

			if (rowCount != 1) {
				throw new Exception("Failed to insert invoice");

			}
			ResultSet generatedKeys = myStmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				int generatedId = generatedKeys.getInt(1);
				invoice.setId(generatedId);
			}
			myConn.commit();
			return invoice;
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			if (myConn != null) {
				try {
					myConn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
			throw se; // Rethrow the SQLException

		} finally {
			close(myConn, myStmt, null);
		}
	}

	public String deleteInvoiceById(int id) throws Exception {

		try {
			myConn = invoiceConfiguration.dataSource().getConnection();
			myConn.setAutoCommit(false);
			String sql = "delete from invoice where id=?";
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, id);

			int deleted = myStmt.executeUpdate();
			myConn.commit();

			if (deleted == 1) {
				return "Invoice Deleted Successfully";
			} else {
				return "No Invoice Found with ID: " + id;
			}

		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			if (myConn != null) {
				try {
					myConn.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
			throw se; // Rethrow the SQLException

		} finally {
			close(myConn, myStmt, null);
		}

	}


	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
