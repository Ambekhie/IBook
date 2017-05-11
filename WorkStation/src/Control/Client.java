package Control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private Socket socket;
	private final String IP_ADDRESS = "127.0.0.1";
	private final int PORT_NUMBER = 6666;

	public Client() {
		try {
			System.out.println("Connecting");
			this.socket = new Socket(this.IP_ADDRESS, this.PORT_NUMBER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void finalize() {
		try {
			this.socket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object sendUserInput(String method, Class<?>[] types, Object[] params) throws ClassNotFoundException, IOException {
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeUTF(method);
		output.writeObject(types);
		output.writeObject(params);
		return (Object) new ObjectInputStream(socket.getInputStream()).readObject();
	}

}
