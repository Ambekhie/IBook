package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Control.Pair;

public class Customer extends User {
	// PRE-DEFINED QUERIES
	private final String SIGNUP_QUERY = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, FALSE)";
	// PRE-COMPILED STATEMENT
	private PreparedStatement signup;

	public Customer(Connection connection) throws SQLException {
		super(connection);
		this.signup = (PreparedStatement) super.connection.prepareStatement(this.SIGNUP_QUERY);
	}

	@Override
	public Pair<Boolean, String> signUp(Object[] params) {
		try {
			ResultSet set = super.connection.getMetaData().getColumns(null, null, super.USER_TABLE, null);
			for (int i = 0; i < params.length; i++) {
				set.next();
				if (params[i] == null)
					this.signup.setNull(i + 1, set.getInt(super.COLUMN_TYPE));
				else
					this.setQueryParam(i + 1, set, params[i], this.signup);
			}
			this.signup.executeUpdate();
			this.connection.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Pair<>(false, e.getMessage());
		}
		return new Pair<>(true, null);
	}

}
