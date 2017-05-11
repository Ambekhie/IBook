package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.rowset.CachedRowSetImpl;

import Control.Pair;

public class User {

	protected final String USER_TABLE = "USER";
	protected final String BOOK_TABLE = "BOOK";
	protected final int COLUMN_TYPE = 5;
	protected final int COLUMN_NAME = 4;
	private final String USER_INFO = "SELECT * FROM USER WHERE USER_NAME = ?";
	private final String BOOK_INFO = "SELECT * FROM BOOK WHERE ISBN = ?";
	private final String BUY_BOOK = "UPDATE BOOK SET COPIES = COPIES - ? WHERE ISBN = ?";
	private final String UPDATE_SALES = "INSERT INTO SALES (ISBN, USER_NAME, DATE, QUANTITY) VALUES(?, ?, ?, ?)";
	private PreparedStatement userInfo;
	private PreparedStatement bookInfo;
	private PreparedStatement updateSales;
	private PreparedStatement buyBook;
	protected String userName;
	protected Connection connection;
	protected Statement stmt;
	protected ResultSetMetaData metaData;

	protected Map<String, Pair<String, Integer>> shoppingCart;
	protected Map<String, String> book;

	public User(Connection connection) throws SQLException {
		this.connection = connection;
		this.stmt = (Statement) connection.createStatement();
		this.userInfo = (PreparedStatement) connection.prepareStatement(this.USER_INFO);
		this.bookInfo = (PreparedStatement) connection.prepareStatement(this.BOOK_INFO);
		this.buyBook = (PreparedStatement) connection.prepareStatement(this.BUY_BOOK);
		this.updateSales = (PreparedStatement) connection.prepareStatement(this.UPDATE_SALES);
		this.shoppingCart = new HashMap<String, Pair<String, Integer>>();
		this.book = new HashMap<String, String>();
	}

	public Pair<Pair<Boolean, Boolean>, String> signIn(String userName, String password) {
		Pair<Boolean, Boolean> result = new Pair<>(false, false);
		String privilage = "User";
		try {
			this.userInfo.setString(1, userName);
			ResultSet set = this.userInfo.executeQuery();
			if (set.next()) { // VALID USERNAME
				String temp = set.getString("PASSWORD");
				if (password.equals(temp)) { // VALID PASSWORD
					result.setFirst(true);
					result.setSecond(true);
				} else {
					result.setFirst(true);
					result.setSecond(false);
				}
				privilage = set.getBoolean("PRIVILAGE") ? "Manager" : "Customer";
			}
		} catch (SQLException e) { // CONNECTION FAILURE
			System.out.println(e.getMessage());
			result.setFirst(false);
			result.setSecond(false);
		}
		return new Pair<>(result, privilage);
	}

	public boolean editProfile(String field, String value) {
		String editQuery = "UPDATE USER SET " + field + " = '" + value + "' WHERE USER_NAME = " + this.getUserName();
		boolean updated = false;
		try {
			updated = (this.stmt.executeUpdate(editQuery) > 0);
			this.connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return updated;
	}

	private String getUserName() {
		return "'" + this.userName + "'";
	}

	public boolean signOut() {
		this.userName = null;
		this.shoppingCart.clear();
		return true;
	}

	public boolean addItem(String ISBN, int quantity) throws SQLException {
		if (!shoppingCart.containsKey(ISBN)) {
			this.bookInfo.setString(1, ISBN);
			ResultSet set = (ResultSet) this.bookInfo.executeQuery();
			String title = set.next() ? set.getString("TITLE").toLowerCase() : null;
			book.put(title, ISBN);
			shoppingCart.put(ISBN, new Pair<String, Integer>(title, 0));
		}
		Pair<String, Integer> curr = shoppingCart.get(ISBN);
		curr.setSecond(curr.getSecond() + quantity);
		return true;
	}

	public boolean removeItem(String title) {
		// System.out.println(shoppingCart.get(title));
		title.toLowerCase();
		if (!book.containsKey(title))
			return false;
		String ISBN = book.get(title);
		book.remove(title);
		shoppingCart.remove(ISBN);
		// System.out.println(shoppingCart.containsKey(title));
		return true;
	}

	public Pair<Boolean, String> checkout(String creditCard, Date expiry) {
		if (!creditCard.matches("^4[0-9]{12}(?:[0-9]{3})?$") || expiry.before(new Date(System.currentTimeMillis())))
			return new Pair<>(false, "INVALID CREDITCARD INFO");
		if (this.shoppingCart.isEmpty())
			return new Pair<>(false, "Shoppingcart is Empty");
		try {
			this.connection.setSavepoint();
			for (Map.Entry<String, Pair<String, Integer>> entry : this.shoppingCart.entrySet()) {
				this.buyBook.setInt(1, entry.getValue().getSecond());
				this.buyBook.setString(2, entry.getKey());
				this.buyBook.addBatch();
				this.updateSales.setString(1, entry.getKey());
				this.updateSales.setString(2, this.userName);
				this.updateSales.setDate(3, new Date(System.currentTimeMillis()));
				this.updateSales.setInt(4, entry.getValue().getSecond());
				this.updateSales.addBatch();
			}
			this.buyBook.executeBatch();
			this.connection.commit();
			this.updateSales.executeBatch();
			this.connection.commit();
			this.shoppingCart.clear();
		} catch (SQLException e) {
			this.shoppingCart.clear();
			System.out.println(e.getMessage());
			return new Pair<>(false, "Checkout InComplete");
		}
		return new Pair<>(true, null);
	}

	public CachedRowSetImpl search(Map<String, ArrayList<String>> param) throws SQLException {
		if (param.isEmpty())
			return new CachedRowSetImpl();
		Iterator<Entry<String, ArrayList<String>>> entries = param.entrySet().iterator();
		StringBuilder query = new StringBuilder("SELECT * FROM BOOK WHERE");
		while (entries.hasNext()) {
			Map.Entry<String, ArrayList<String>> entry = (Map.Entry<String, ArrayList<String>>) entries.next();
			Iterator<String> itr = entry.getValue().iterator();
			while (itr.hasNext()) {
				query.append(" " + entry.getKey() + " = '" + itr.next() + "'");
				if (itr.hasNext() || entries.hasNext()) {
					query.append(" OR");
				}
			}
		}
		CachedRowSetImpl search = new CachedRowSetImpl();
		search.populate(this.stmt.executeQuery(query.toString()));
		return search;
	}

	public Pair<Boolean, String> signUp(Object[] params) {
		return null;
	}

	public void logout() {
		this.shoppingCart.clear();
		this.userName = null;
	}

	public CachedRowSetImpl getUserInfo(String userName) throws SQLException {
		this.userInfo.setString(1, userName);
		CachedRowSetImpl temp = new CachedRowSetImpl();
		temp.populate(this.userInfo.executeQuery());
		return temp;
	}

	public Pair<Boolean, String> addBook(Object[] param) {
		return null;
	}

	public boolean modifyBook(String ISBN, int field, Object value) {
		return false;
	}

	public Pair<Boolean, String> orderBook(String ISBN, int quantity) {
		return null;
	}

	public boolean confirmOrder(String ISBN, int order) {
		return false;
	}

	public boolean promoteUser(String userName) {
		return false;
	}

	public CachedRowSetImpl getCustomers() throws SQLException {
		return null;
	}

	public CachedRowSetImpl getBooks() throws SQLException {
		return null;
	}

	public CachedRowSetImpl getOrders() throws SQLException {
		return null;
	}

	public void getTotalSales() {
	}

	public void getTopFiveUsers() {
	}

	public void getTopTenBooks() {
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Pair<String, Integer>> getShoppingCart() {
		return this.shoppingCart;
	}

	protected void setQueryParam(int col, ResultSet set, Object object, PreparedStatement stmt) throws SQLException {
		System.out.println(set.getInt(COLUMN_TYPE));
		switch (set.getInt(COLUMN_TYPE)) {
		case java.sql.Types.INTEGER:
			stmt.setInt(col, (Integer) object);
			break;
		case java.sql.Types.FLOAT:
			stmt.setFloat(col, (Float) object);
			break;
		case java.sql.Types.REAL:
			stmt.setDouble(col, (Double) object);
			break;
		case java.sql.Types.VARCHAR:
			stmt.setString(col, (String) object);
			break;
		case java.sql.Types.DATE:
			stmt.setDate(col, (Date) object);
			break;
		default:
			System.out.println(set.getInt(5));
			return;
		}
	}

}
