package Control;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server extends Thread {

	private ServerSocket server;
	private Socket socket;
	private InetSocketAddress port;
	private Connection connection;
	private final String DB_URL = "jdbc:mysql://localhost/BOOK_STORE";
	private final String DB_USERNAME = "root";
	private final String DB_PASSWORD = "admin";
	private final int PORT_NUMBER = 6666;
	public Server() throws SQLException {
		this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USERNAME, this.DB_PASSWORD);
		this.connection.setAutoCommit(false);
		this.port = new InetSocketAddress(this.PORT_NUMBER);
	}

	public void run() {
		System.out.println("running");
		try {
			this.server = new ServerSocket();
			this.server.setReuseAddress(true);
			this.server.bind(this.port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		while (true) {
			try {
				this.socket = this.server.accept();
				Worker client = new Worker(this.socket, this.connection);
				Thread thread = new Thread(client);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("CLIENT FOUND !");
		}
	}

	public static void main(String[] args) throws SQLException {
		new Server().start();	
	}
}
