package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.rowset.CachedRowSetImpl;

import Control.Pair;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

@SuppressWarnings("deprecation")
public class Manager extends User {
	// PRE-DEFINED QUERIES
	private final String ORDER_INSERTION = "INSERT INTO `ORDER` (ISBN, QUANTITY) VALUES (? , ?)";
	private final String ORDER_CONFIRMATION = "SELECT * FROM `ORDER` WHERE ISBN = ? AND ORDER_ID = ?";
	private final String ORDER_DELETION = "DELETE FROM `ORDER` WHERE ISBN = ? AND ORDER_ID = ?";
	private final String BOOK_ADDITION = "INSERT INTO BOOK VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String USER_PROMOTION = "UPDATE USER SET PRIVILAGE = TRUE WHERE USER_NAME = ?";
	private final String ALL_BOOKS = "SELECT * FROM BOOK";
	private final String ALL_ORDERS = "SELECT * FROM `ORDER`";
	private final String ALL_CUSTOMERS = "SELECT * FROM USER WHERE PRIVILAGE = FALSE";
	private final String TOP_TEN_BOOKS = "SELECT ISBN, TITLE FROM (SELECT ISBN, SUM(QUANTITY) AS QUANTITY FROM SALES WHERE DATE BETWEEN (CURRENT_DATE() - INTERVAL 3 MONTH) AND CURRENT_DATE() GROUP BY ISBN ORDER BY QUANTITY DESC LIMIT 10) AS T NATURAL JOIN BOOK";
	private final String TOP_FIVE_USERS = "SELECT USER_NAME FROM (SELECT USER_NAME, SUM(QUANTITY) AS QUANTITY FROM SALES WHERE DATE BETWEEN (CURRENT_DATE() - INTERVAL 3 MONTH) AND CURRENT_DATE() GROUP BY USER_NAME ORDER BY QUANTITY DESC LIMIT 5) AS T";
	private final String TOTAL_SALES = "SELECT SUM(QUANTITY * SELLING_PRICE) AS SUM FROM ( SELECT ISBN, SUM(QUANTITY) AS QUANTITY FROM SALES WHERE DATE BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE() GROUP BY ISBN ) AS T NATURAL JOIN BOOK";
	// PRE-COMPILED STATEMENTS
	private PreparedStatement bookOrder;
	private PreparedStatement orderConfirmation;
	private PreparedStatement orderDeletion;
	private PreparedStatement userPromotion;
	private PreparedStatement bookAddition;
	private PreparedStatement allBooks;
	private PreparedStatement allOrders;
	private PreparedStatement allCustomers;

	public Manager(Connection connection) throws SQLException {
		super(connection);
		// PREPARE DEFINES STATMENTS
		this.bookOrder = (PreparedStatement) connection.prepareStatement(this.ORDER_INSERTION);
		this.orderConfirmation = (PreparedStatement) connection.prepareStatement(this.ORDER_CONFIRMATION);
		this.orderDeletion = (PreparedStatement) connection.prepareStatement(this.ORDER_DELETION);
		this.userPromotion = (PreparedStatement) connection.prepareStatement(this.USER_PROMOTION);
		this.bookAddition = (PreparedStatement) connection.prepareStatement(this.BOOK_ADDITION);
		this.allBooks = (PreparedStatement) connection.prepareStatement(this.ALL_BOOKS);
		this.allOrders = (PreparedStatement) connection.prepareStatement(this.ALL_ORDERS);
		this.allCustomers = (PreparedStatement) connection.prepareStatement(this.ALL_CUSTOMERS);
	}

	@Override
	public Pair<Boolean, String> addBook(Object[] params) {
		try {
			ResultSet set = super.connection.getMetaData().getColumns(null, null, super.BOOK_TABLE, null);
			for (int i = 0; i < params.length; i++) {
				set.next();
				if (params[i] == null)
					this.bookAddition.setNull(i + 1, set.getInt(super.COLUMN_TYPE));
				else
					this.setQueryParam(i + 1, set, params[i], this.bookAddition);
			}
			this.bookAddition.executeUpdate();
			this.connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Pair<>(false, e.getMessage());
		}
		return new Pair<>(true, null);
	}

	@Override
	public Pair<Boolean, String> orderBook(String ISBN, int quantity) {
		try {
			this.bookOrder.setString(1, ISBN);
			this.bookOrder.setInt(2, quantity);
			this.bookOrder.executeUpdate();
			this.connection.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Pair<>(false, e.getMessage());
		}
		return new Pair<>(true, null);
	}

	@Override
	public boolean modifyBook(String ISBN, int field, Object value) {
		try {
			ResultSet set = super.connection.getMetaData().getColumns(null, null, super.BOOK_TABLE, null);
			for (int i = 0; i < field; i++)
				set.next();
			String query = "UPDATE BOOK SET " + set.getString(super.COLUMN_NAME) + " = ? WHERE ISBN = ?";
			PreparedStatement pstmt = super.connection.prepareStatement(query);
			this.setQueryParam(1, set, value, pstmt);
			pstmt.setString(2, ISBN);
			pstmt.executeUpdate();
			this.connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean confirmOrder(String ISBN, int order) {
		try {
			this.orderConfirmation.setString(1, ISBN);
			this.orderConfirmation.setInt(2, order);
			ResultSet set = this.orderConfirmation.executeQuery();
			if (set.next()) {
				this.orderDeletion.setString(1, ISBN);
				this.orderDeletion.setInt(2, order);
				this.orderDeletion.executeUpdate();
				this.connection.commit();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean promoteUser(String userName) {
		try {
			this.userPromotion.setString(1, userName);
			this.userPromotion.executeUpdate();
			this.connection.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public CachedRowSetImpl getBooks() throws SQLException {
		CachedRowSetImpl books = new CachedRowSetImpl();
		books.populate(this.allBooks.executeQuery());
		return books;
	}

	@Override
	public CachedRowSetImpl getOrders() throws SQLException {
		CachedRowSetImpl orders = new CachedRowSetImpl();
		orders.populate(this.allOrders.executeQuery());
		return orders;
	}

	@Override
	public CachedRowSetImpl getCustomers() throws SQLException {
		CachedRowSetImpl customers = new CachedRowSetImpl();
		customers.populate(this.allCustomers.executeQuery());
		return customers;
	}

	@Override
	public void getTotalSales() {

		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(Columns.column("THE TOTAL SALES SUM  : ", "SUM", DataTypes.stringType()))
				.title(// title of the report
						Components.text("TOTAL SALES").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())// show page number on the
													// page footer
				.setDataSource(TOTAL_SALES, super.connection);

		try {
			report.show();
		} catch (DRException e) {
			System.out.println(e.getMessage());
		}

		return;

	}

	@Override
	public void getTopFiveUsers() {
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(Columns.column("USERS NAMES : ", "USER_NAME", DataTypes.stringType()))
				.title(// title of the report
						Components.text("TOP FIVE USERS").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())// show page number on the
													// page footer
				.setDataSource(TOP_FIVE_USERS, super.connection);

		try {
			report.show();
		} catch (DRException e) {
			System.out.println(e.getMessage());
		}
		return;
	}

	@Override
	public void getTopTenBooks() {
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(Columns.column("BOOK NAMES : ", "TITLE", DataTypes.stringType()))
				.title(// title of the report
						Components.text("TOP TEN BOOKS").setHorizontalAlignment(HorizontalAlignment.CENTER))
				.pageFooter(Components.pageXofY())// show page number on the
													// page footer
				.setDataSource(TOP_TEN_BOOKS, super.connection);

		try {
			report.show();
		} catch (DRException e) {
			System.out.println(e.getMessage());
		}
		return;
	}
}
