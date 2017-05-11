package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import com.sun.rowset.CachedRowSetImpl;

import Control.Pair;

public class UserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JToolBar userProfile;
	private JToolBar shoppingToolBar;
	private JToolBar managerToolBar;
	private FlowLayout FL;
	private JButton viewProfile;
	private JButton logout;
	private JPanel mainPanel;
	private JPanel userAccount;
	private JPanel shoppingCartPanel;
	private JPanel managerPanel;
	private JPanel pan;

	private JButton changeUsername;
	private JButton changePassword;
	private JButton changeLname;
	private JButton changeFname;
	private JButton changeAddress;
	private JButton changeEmail;
	private JButton changePhone;
	private JButton back;
	private JButton searchBtn;
	private JButton shoppingCart;
	private JButton removeItem;
	private JButton checkOut;
	private JButton manager;
	private JButton addBook;
	private JButton modifyBook;
	private JButton placeOrder;
	private JButton confirmOrder;
	private JButton promoteCustomer;
	private JButton viewReports;
	private JButton backShoppingCart;
	private JButton backManager;

	private JCheckBox ISBN;
	private JCheckBox title;
	private JCheckBox category;
	private JCheckBox author;
	private JCheckBox publisher;

	private JTextField ISBNText;
	private JTextField titleText;
	private JTextField authorText;
	private JTextField publisherText;

	private JCheckBox science;
	private JCheckBox art;
	private JCheckBox religion;
	private JCheckBox history;
	private JCheckBox geography;

	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPanel;

	private ArrayList<JButton> buttons;
	private ArrayList<JButton> buttonsOrders;
	private ArrayList<JButton> buttonsCustomers;

	private JLabel username;
	private JLabel password;
	private JLabel fname;
	private JLabel lname;
	private JLabel email;
	private JLabel phoneNo;
	private JLabel address;
	private JLabel welcome2;

	private Map<String, JLabel> labels;

	private CachedRowSetImpl userInfo;
	private String userName;

	public UserView(String userName, CachedRowSetImpl userInfo, String privilage) {

		this.userName = userName;
		this.userInfo = userInfo;
		this.setSize(500, 700);
		this.setTitle("Book Store");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setResizable(false);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(204, 204, 255));
		mainPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setLocation(100, 70);
		scrollPane.setSize(400, 600);
		mainPanel.add(scrollPane);

		this.FL = new FlowLayout();
		this.userProfile = new JToolBar();
		userProfile.setFloatable(false);
		mainPanel.add(userProfile, BorderLayout.NORTH);
		userProfile.setBackground(new Color(255, 255, 153));
		userProfile.setLayout(FL);
		FL.setAlignment(FlowLayout.LEFT);

		JLabel name = new JLabel(this.userName); // get user name
		name.setFont(new Font("Serif", Font.BOLD, 18));
		userProfile.add(name);
		userProfile.setLocation(0, 0);
		userProfile.setSize(500, 70);
		viewProfile = new JButton("");
		shoppingCart = new JButton("");
		shoppingCart.setName("Shopping Cart");
		shoppingCart.setIcon(new ImageIcon(UserView.class.getResource("/shopping_cart.jpg")));
		shoppingCart.setBackground(Color.white);
		for (int i = 0; i < 5; i++) {
			userProfile.addSeparator();
		}

		logout = new JButton("");
		viewProfile.setBackground(Color.white);
		logout.setBackground(Color.white);
		viewProfile.setIcon(new ImageIcon(UserView.class.getResource("/viewProfile.png")));
		logout.setIcon(new ImageIcon(UserView.class.getResource("/logout.png")));

		if (privilage.equals("Manager")) {

			manager = new JButton("");
			manager.setName("manager");
			manager.setIcon(new ImageIcon(UserView.class.getResource("/manager.png")));
			manager.setBackground(Color.white);
			userProfile.add(manager);
		}
		userProfile.add(shoppingCart);
		userProfile.add(viewProfile);
		userProfile.add(logout);

		mainPanel.add(userProfile, BorderLayout.NORTH);

		userAccount = new JPanel();
		userAccount.setLayout(null);
		userAccount.setBackground(new Color(204, 255, 204));

		JLabel welcome = new JLabel("WELCOME");
		welcome.setFont(new Font("Serif", Font.PLAIN, 32));
		welcome.setLocation(100, 5);
		welcome.setSize(200, 75);
		welcome.setForeground(new Color(0, 38, 153));
		userAccount.add(welcome);

		welcome2 = new JLabel(this.userName); // username

		welcome2.setFont(new Font("Monospaced", Font.BOLD, 32));
		welcome2.setLocation(300, 5);
		welcome2.setSize(250, 75);
		welcome2.setForeground(new Color(0, 38, 153));
		userAccount.add(welcome2);
		try {
			this.userInfo.next();
			this.username = new JLabel(this.userInfo.getString("USER_NAME"));
			this.password = new JLabel(this.userInfo.getString("PASSWORD"));
			this.fname = new JLabel(this.userInfo.getString("F_NAME"));
			this.lname = new JLabel(this.userInfo.getString("L_NAME"));
			this.email = new JLabel(this.userInfo.getString("EMAIL"));
			this.phoneNo = new JLabel(this.userInfo.getString("TELEPHONE"));
			this.address = new JLabel(this.userInfo.getString("ADDRESS"));

			TitledBorder title1;
			title1 = BorderFactory.createTitledBorder(null, "User Name", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));

			username.setBorder(title1);

			username.setFont(new Font("Serif", Font.BOLD, 24));
			username.setLocation(20, 75);
			username.setSize(400, 75);

			userAccount.add(username);

			TitledBorder title2;
			title2 = BorderFactory.createTitledBorder(null, "Password", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			password.setBorder(title2);
			password.setFont(new Font("Serif", Font.BOLD, 24));
			password.setLocation(20, 155);
			password.setSize(400, 75);
			userAccount.add(password);

			TitledBorder title3;
			title3 = BorderFactory.createTitledBorder(null, "First Name", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			fname.setBorder(title3);
			fname.setFont(new Font("Serif", Font.BOLD, 24));
			fname.setLocation(20, 235);
			fname.setSize(400, 75);
			userAccount.add(fname);

			TitledBorder title4;
			title4 = BorderFactory.createTitledBorder(null, "Last Name", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			lname.setBorder(title4);

			lname.setFont(new Font("Serif", Font.BOLD, 24));
			lname.setLocation(20, 315);
			lname.setSize(400, 75);
			userAccount.add(lname);

			TitledBorder title5;
			title5 = BorderFactory.createTitledBorder(null, "E-mail", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			email.setBorder(title5);

			email.setFont(new Font("Serif", Font.BOLD, 24));
			email.setLocation(20, 395);
			email.setSize(400, 75);
			userAccount.add(email);

			TitledBorder title6;
			title6 = BorderFactory.createTitledBorder(null, "Phone Number", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			phoneNo.setBorder(title6);

			phoneNo.setFont(new Font("Serif", Font.BOLD, 24));
			phoneNo.setLocation(20, 475);
			phoneNo.setSize(400, 75);
			userAccount.add(phoneNo);

			TitledBorder title7;
			title7 = BorderFactory.createTitledBorder(null, "Shipping Address", TitledBorder.LEFT, TitledBorder.CENTER,
					new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
			address.setBorder(title7);

			address.setFont(new Font("Serif", Font.BOLD, 24));
			address.setLocation(20, 555);
			address.setSize(400, 75);
			userAccount.add(address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		changeUsername = new JButton();
		changeUsername.setName("USER_NAME");
		changePassword = new JButton();
		changePassword.setName("PASSWORD");
		changeLname = new JButton();
		changeLname.setName("L_NAME");
		changeFname = new JButton();
		changeFname.setName("F_NAME");
		changeAddress = new JButton();
		changeAddress.setName("ADDRESS");
		changeEmail = new JButton();
		changeEmail.setName("EMAIL");
		changePhone = new JButton();
		changePhone.setName("TELEPHONE");

		changeUsername.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changePassword.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeLname.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeFname.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeAddress.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeEmail.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changePhone.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));

		changeUsername.setLocation(425, 91);
		changeUsername.setSize(50, 50);
		userAccount.add(changeUsername);

		changePassword.setLocation(425, 171);
		changePassword.setSize(50, 50);
		userAccount.add(changePassword);

		changeFname.setLocation(425, 251);
		changeFname.setSize(50, 50);
		userAccount.add(changeFname);

		changeLname.setLocation(425, 331);
		changeLname.setSize(50, 50);
		userAccount.add(changeLname);

		changeEmail.setLocation(425, 411);
		changeEmail.setSize(50, 50);
		userAccount.add(changeEmail);

		changePhone.setLocation(425, 491);
		changePhone.setSize(50, 50);
		userAccount.add(changePhone);

		changeAddress.setLocation(425, 571);
		changeAddress.setSize(50, 50);
		userAccount.add(changeAddress);

		back = new JButton();
		back.setIcon(new ImageIcon(UserView.class.getResource("/homeicon.png")));
		back.setLocation(20, 10);
		back.setSize(50, 50);
		userAccount.add(back);

		JLabel search = new JLabel("Search");
		search.setLocation(20, 40);
		search.setSize(90, 20);
		search.setBackground(new Color(255, 230, 204));
		search.setFont(new Font("Serif", Font.BOLD, 20));

		ISBN = new JCheckBox("ISBN");
		ISBN.setMnemonic(KeyEvent.VK_C);

		title = new JCheckBox("Title");
		title.setMnemonic(KeyEvent.VK_C);

		category = new JCheckBox("Category");
		category.setMnemonic(KeyEvent.VK_C);

		author = new JCheckBox("Author");
		author.setMnemonic(KeyEvent.VK_C);

		publisher = new JCheckBox("Publisher");
		publisher.setMnemonic(KeyEvent.VK_C);

		JPanel checkPanel = new JPanel();
		checkPanel.setBackground(new Color(255, 230, 204));
		checkPanel.setLayout(null);
		ISBN.setLocation(5, 60);
		ISBN.setSize(90, 30);
		ISBN.setBackground(new Color(255, 230, 204));

		ISBNText = new JTextField("Enter ISBN");
		ISBNText.setLocation(5, 90);
		ISBNText.setSize(90, 30);

		title.setLocation(5, 120);
		title.setSize(90, 30);
		title.setBackground(new Color(255, 230, 204));

		titleText = new JTextField("Enter title");
		titleText.setLocation(5, 150);
		titleText.setSize(90, 30);

		category.setLocation(5, 180);
		category.setSize(90, 30);
		category.setBackground(new Color(255, 230, 204));

		science = new JCheckBox("Science");
		science.setLocation(15, 210);
		science.setSize(90, 30);
		science.setBackground(new Color(255, 230, 204));

		art = new JCheckBox("Art");
		art.setLocation(15, 240);
		art.setSize(90, 30);
		art.setBackground(new Color(255, 230, 204));

		religion = new JCheckBox("Religion");
		religion.setLocation(15, 270);
		religion.setSize(90, 30);
		religion.setBackground(new Color(255, 230, 204));

		history = new JCheckBox("History");
		history.setLocation(15, 300);
		history.setSize(90, 30);
		history.setBackground(new Color(255, 230, 204));

		geography = new JCheckBox("Geography");
		geography.setLocation(15, 330);
		geography.setSize(90, 30);
		geography.setBackground(new Color(255, 230, 204));

		author.setLocation(5, 360);
		author.setSize(90, 30);
		author.setBackground(new Color(255, 230, 204));

		authorText = new JTextField("Enter Author");
		authorText.setLocation(5, 390);
		authorText.setSize(90, 30);

		publisher.setLocation(5, 420);
		publisher.setSize(90, 30);
		publisher.setBackground(new Color(255, 230, 204));

		publisherText = new JTextField("Enter Publisher");
		publisherText.setLocation(5, 450);
		publisherText.setSize(90, 30);

		searchBtn = new JButton();

		searchBtn.setLocation(25, 500);
		searchBtn.setSize(50, 50);

		searchBtn.setIcon(new ImageIcon(UserView.class.getResource("/search.jpg")));

		checkPanel.add(ISBN);
		checkPanel.add(title);
		checkPanel.add(category);
		checkPanel.add(author);
		checkPanel.add(publisher);
		checkPanel.add(ISBNText);
		checkPanel.add(titleText);
		checkPanel.add(authorText);
		checkPanel.add(publisherText);
		checkPanel.add(science);
		checkPanel.add(art);
		checkPanel.add(religion);
		checkPanel.add(history);
		checkPanel.add(geography);
		checkPanel.add(search);
		checkPanel.add(searchBtn);
		checkPanel.setLocation(0, 40);
		checkPanel.setSize(100, 700);
		mainPanel.add(checkPanel, BorderLayout.LINE_START);

		managerPanel = new JPanel();
		managerPanel.setLayout(null);

		managerToolBar = new JToolBar();
		managerToolBar.setFloatable(false);
		managerToolBar.setBackground(new Color(255, 255, 153));
		managerToolBar.setLayout(FL);
		managerToolBar.setLocation(0, 0);
		managerToolBar.setSize(900, 70);

		addBook = new JButton("Add Book");
		modifyBook = new JButton("Modify Book");
		placeOrder = new JButton("Place Order");
		confirmOrder = new JButton("Confirm");
		promoteCustomer = new JButton("Promote");
		viewReports = new JButton("Reports");
		backManager = new JButton("Back");
		
		addBook.setBackground(new Color(204, 204, 255));
		modifyBook.setBackground(new Color(204, 204, 255));
		placeOrder.setBackground(new Color(204, 204, 255));
		confirmOrder.setBackground(new Color(204, 204, 255));
		promoteCustomer.setBackground(new Color(204, 204, 255));
		viewReports.setBackground(new Color(204, 204, 255));
		backManager.setBackground(new Color(204, 204, 255));

		addBook.setName("Add Book");
		modifyBook.setName("Modify Book");
		placeOrder.setName("Place Order");
		confirmOrder.setName("Confirm Order");
		promoteCustomer.setName("Promote Customer");
		viewReports.setName("View Reports");
		backManager.setName("Back");

		managerToolBar.add(addBook);
		managerToolBar.add(modifyBook);
		managerToolBar.add(placeOrder);
		managerToolBar.add(confirmOrder);
		managerToolBar.add(promoteCustomer);
		managerToolBar.add(viewReports);
		managerToolBar.add(backManager);

		managerPanel.add(managerToolBar);

		scrollPane2 = new JScrollPane();
		scrollPane2.setLocation(0, 70);
		scrollPane2.setSize(500, 600);
		managerPanel.add(scrollPane2);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.repaint();

	}

	public void viewShoppingCart(Map<String, Pair<String, Integer>> shoppingCart) {
		System.out.println(shoppingCart.size());
		shoppingToolBar = new JToolBar();
		shoppingToolBar.setFloatable(false);
		shoppingToolBar.setBackground(new Color(255, 255, 153));
		shoppingToolBar.setLayout(FL);
		FL.setAlignment(FlowLayout.LEFT);
		shoppingToolBar.setLocation(0, 0);
		shoppingToolBar.setSize(500, 70);
		removeItem = new JButton("Remove");
		checkOut = new JButton("Check Out");
		backShoppingCart = new JButton("Back");
		backShoppingCart.setName("Back");
		
		removeItem.setBackground(new Color(204, 204, 255));
		checkOut.setBackground(new Color(204, 204, 255));
		backShoppingCart.setBackground(new Color(204, 204, 255));
		
		shoppingToolBar.add(removeItem);
		shoppingToolBar.add(checkOut);
		shoppingToolBar.add(backShoppingCart);
		scrollPanel = new JScrollPane();
		scrollPanel.setLocation(0, 70);
		scrollPanel.setSize(500, 600);
		shoppingCartPanel = new JPanel();
		shoppingCartPanel.setLayout(null);
		shoppingCartPanel.add(scrollPanel);
		shoppingCartPanel.add(shoppingToolBar);
		pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		int i = 1;
		labels = new HashMap<String, JLabel>();
		for (Entry<String, Pair<String, Integer>> entry : shoppingCart.entrySet()) {
			System.out.println(entry.getValue().getFirst());
			JLabel label = new JLabel(entry.getValue().getFirst());
			label.setFont(new Font("Serif", Font.BOLD, 23));
			label.setBackground(Color.BLUE);
			labels.put(entry.getValue().getFirst(), label);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 40; // make this component tall
			c.weightx = 0.0;
			c.gridwidth = 9;
			c.gridx = 0;
			c.gridy = i++;
			pan.add(label, c);

		}
		scrollPanel.setViewportView(pan);

	}

	public ArrayList<JButton> viewOrders(CachedRowSetImpl set) {

		String ISBN = null;
		String quantity = null;
		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttonsOrders = new ArrayList<JButton>();
		int i = 1;

		try {
			while (set.next()) {

				ISBN = set.getString("ISBN");
				quantity = set.getString("QUANTITY");
				JButton b = new JButton("          ISBN: " + ISBN + "      Quantity: " + Integer.parseInt(quantity)+ "          ");
				b.setFont(new Font("Serif", Font.BOLD, 23));
				b.setName(set.getString("ORDER_ID") + "," + ISBN);
				buttonsOrders.add(b);
				b.setBackground(new Color(204, 204, 255));
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipady = 40; // make this component tall
				c.weightx = 0.0;
				c.gridwidth = 9;
				c.gridx = 0;
				c.gridy = i++;
				pan.add(b, c);

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane2.setViewportView(pan);
		return buttonsOrders;
	}

	public ArrayList<JButton> viewSearch(ArrayList<String> titles) {

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttons = new ArrayList<JButton>();
		int i = 1;

		for (String title : titles) {
			JButton b = new JButton("                     "+title+"                     ");
			b.setName(title);
			b.setFont(new Font("Serif", Font.BOLD, 23));
			buttons.add(b);
			b.setBackground(new Color(204, 204, 255));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 40; // make this component tall
			c.weightx = 0.0;
			c.gridwidth = 9;
			c.gridx = 0;
			c.gridy = i++;
			pan.add(b, c);
		}

		scrollPane.setViewportView(pan);
		return buttons;

	}

	public ArrayList<JButton> viewCustomers(CachedRowSetImpl set) {

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttonsCustomers = new ArrayList<JButton>();
		int i = 1;
		String username;
		try {
			while (set.next()) {

				username = set.getString("USER_NAME");
				JButton b = new JButton("                     "+username+ "                     ");
				
				b.setName(username);
				b.setFont(new Font("Serif", Font.BOLD, 23));
				b.setBackground(new Color(204, 204, 255));
				buttonsCustomers.add(b);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipady = 40; // make this component tall
				c.weightx = 0.0;
				c.gridwidth = 9;
				c.gridx = 0;
				c.gridy = i++;
				pan.add(b, c);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane2.setViewportView(pan);
		return buttonsCustomers;

	}

	public ArrayList<JButton> viewBooks(ArrayList <String> titles) {

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		buttons = new ArrayList<JButton>();
		int i = 1;

		for (String title : titles) {
			JButton b = new JButton("                     "+title+"                     ");
			b.setName(title);
			b.setFont(new Font("Serif", Font.BOLD, 23));
			buttons.add(b);
			b.setBackground(new Color(204, 204, 255));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.ipady = 40; // make this component tall
			c.weightx = 0.0;
			c.gridwidth = 9;
			c.gridx = 0;
			c.gridy = i++;
			pan.add(b, c);
		}

		scrollPane2.setViewportView(pan);
		return buttons;


	}

	public JButton getViewProfile() {
		return this.viewProfile;
	}

	public JButton getUsernameChange() {
		return this.changeUsername;
	}

	public JButton getFnameChange() {
		return this.changeFname;
	}

	public JButton getLnameChange() {
		return this.changeLname;
	}

	public JButton getEmailChange() {
		return this.changeEmail;
	}

	public JButton getPhoneChange() {
		return this.changePhone;
	}

	public JButton getAddressChange() {
		return this.changeAddress;
	}

	public JButton getPasswordChange() {
		return this.changePassword;
	}

	public JPanel getProfilePanel() {
		return this.userAccount;
	}

	public JPanel getMainPanel() {
		return this.mainPanel;
	}

	public JPanel getShoppingCartPanel() {

		return this.shoppingCartPanel;
	}

	public JPanel getManagerPanel() {

		return this.managerPanel;
	}

	public JPanel getPan() {
		return pan;
	}

	public JButton getBackButton() {
		return this.back;
	}

	public JButton getSearchButton() {
		return this.searchBtn;
	}

	public JButton getShoppingCartButton() {
		return this.shoppingCart;
	}

	public JButton getremoveButton() {
		return this.removeItem;
	}

	public JButton getcheckOutButton() {
		return this.checkOut;
	}

	public JButton getManager() {
		return this.manager;
	}

	public JButton getAddBook() {
		return this.addBook;
	}

	public JButton getModifyBook() {
		return this.modifyBook;
	}

	public JButton getPlaceOrder() {
		return this.placeOrder;
	}

	public JButton getConfirmOrder() {
		return this.confirmOrder;
	}

	public JButton getPromoteCustomer() {
		return this.promoteCustomer;
	}

	public JButton getViewReports() {
		return this.viewReports;
	}

	public JButton getBackShoppingCart() {
		return this.backShoppingCart;
	}

	public JButton getBackManager() {
		return this.backManager;
	}

	public JTextField getISBNText() {
		return this.ISBNText;
	}

	public JTextField getTitleText() {
		return this.titleText;
	}

	public JTextField getAuthorText() {
		return this.authorText;
	}

	public JTextField getPublisherText() {
		return this.publisherText;
	}

	public JCheckBox getISBNCheck() {
		return this.ISBN;
	}

	public JCheckBox getTitleCheck() {
		return this.title;
	}

	public JCheckBox getAuthorCheck() {
		return this.author;
	}

	public JCheckBox getPublisherCheck() {
		return this.publisher;
	}

	public JCheckBox getCategoryCheck() {
		return this.category;
	}

	public JCheckBox getScience() {
		return this.science;
	}

	public JCheckBox getArt() {
		return this.art;
	}

	public JCheckBox getGeography() {
		return this.geography;
	}

	public JCheckBox getReligion() {
		return this.religion;
	}

	public JCheckBox getHistory() {
		return this.history;
	}

	public Map<String, JLabel> getLabels() {
		return labels;
	}

	public JLabel getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public void setPassword(String password) {
		this.password.setText(password);
	}

	public void setFname(String fname) {
		this.fname.setText(fname);
	}

	public void setLname(String lname) {
		this.lname.setText(lname);
	}

	public void setEmail(String email) {
		this.email.setText(email);
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo.setText(phoneNo);
	}

	public void setAddress(String address) {
		this.address.setText(address);
	}

	public void setWelcome2(String welcome2) {
		this.welcome2.setText(welcome2);
	}
	
	public JButton getLogout(){
		return this.logout;
	}
	

}
