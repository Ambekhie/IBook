package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sun.rowset.CachedRowSetImpl;

import View.BookModificationView;
import View.BookView;
import View.RegView;
import View.UserView;

public class Engine {

	private RegView reg;
	private UserView userView;
	private BookModificationView modifyView;
	private Client client;
	private Map<String, ArrayList<String>> selectedBooks;
	private Map<String, ArrayList<String>> booksManager;
	private ArrayList<String> titles;
	private ArrayList<String> titlesManager;
	private String selectedISBN;
	private String selectedISBNManager;

	public Engine() throws ClassNotFoundException, SQLException {

		this.client = new Client();
		this.reg = new RegView();
		this.reg.getLogIn().addActionListener(new LogIn());
		this.reg.getSubmit().addActionListener(new Submit());
		this.reg.getMainFrame().setVisible(true);
	}

	private class LogIn implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {

			String userName = reg.getTabOneUserName().getText().trim();
			@SuppressWarnings("deprecation")
			String password = reg.getTabOnePassword().getText().trim();
			Pair<Pair<Boolean, Boolean>, String> state = null;
			Class<?>[] types;
			Object[] params;
			types = new Class[] { String.class, String.class };
			params = new Object[] { userName, password };
			try {
				state = (Pair<Pair<Boolean, Boolean>, String>) client.sendUserInput("signIn", types, params);
			} catch (ClassNotFoundException | IOException e2) {
				System.out.println(e2.getMessage());
				return;
			}

			boolean userNameMatched = state.getFirst().getFirst();
			boolean passwordMatched = state.getFirst().getSecond();

			if (userNameMatched && passwordMatched) {

				try {
					String privilage = state.getSecond();
					reg.getMainFrame().setVisible(false);
					// user.getUserInfo(userName);
					types = new Class[] { String.class };
					params = new Object[] { userName };
					CachedRowSetImpl rowSet = (CachedRowSetImpl) client.sendUserInput("getUserInfo", types, params);

					userView = new UserView(userName, rowSet, privilage);
					userView.getViewProfile().addActionListener(new ProfileView());
					userView.getUsernameChange().addActionListener(new Change());
					userView.getPasswordChange().addActionListener(new Change());
					userView.getEmailChange().addActionListener(new Change());
					userView.getFnameChange().addActionListener(new Change());
					userView.getLnameChange().addActionListener(new Change());
					userView.getPhoneChange().addActionListener(new Change());
					userView.getAddressChange().addActionListener(new Change());
					userView.getBackButton().addActionListener(new BackPressed());
					userView.getSearchButton().addActionListener(new Search());
					userView.getShoppingCartButton().addActionListener(new ShoppingCart());
					userView.getLogout().addActionListener(new LoggingOut());

					userView.getAddBook().addActionListener(new Manager());
					userView.getModifyBook().addActionListener(new Manager());
					userView.getPlaceOrder().addActionListener(new Manager());
					userView.getConfirmOrder().addActionListener(new Manager());
					userView.getPromoteCustomer().addActionListener(new Manager());
					userView.getViewReports().addActionListener(new Manager());
					userView.getBackManager().addActionListener(new Manager());
					if (privilage.equals("Manager"))
						userView.getManager().addActionListener(new Manager());

				} catch (ClassNotFoundException e1) {
					System.out.println(e1.getMessage());
					return;
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					return;
				}
			} else if (!userNameMatched) {

				JOptionPane.showMessageDialog(userView, "Invalid Username.", "error", JOptionPane.ERROR_MESSAGE);
			} else if (userNameMatched && !passwordMatched) {

				JOptionPane.showMessageDialog(userView, "Password doesn't match with username.", "warning",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	private class Submit implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {

			Object arr[] = new Object[8];

			try {
				arr[0] = reg.getTabTwoUserName().getText().trim();
				arr[1] = reg.getFirstName().getText().trim();
				arr[2] = reg.getLastName().getText().trim();
				arr[3] = reg.getTabTwoPassword().getText().trim();
				arr[4] = reg.getEmail().getText().trim();
				arr[5] = reg.getPhone().getText().trim();
				arr[6] = reg.getAddress().getText().trim();
				arr[7] = false;
				Class<?>[] types = new Class[] { Object[].class };
				Object[] params = new Object[] { arr };
				Pair<Boolean, String> ret = null;
				try {
					ret = (Pair<Boolean, String>) client.sendUserInput("signUp", types, params);
				} catch (ClassNotFoundException | IOException e1) {
					System.out.println(e1.getMessage());
					return;
				}

				if (!ret.getFirst()) {
					JOptionPane.showMessageDialog(userView, ret.getSecond(), "warning", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(userView, "You Signed Up Successfully :))");
					reg.getTappedPane().setSelectedIndex(0);
				}
			} catch (Exception e3) {
				JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private class ProfileView implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			userView.setContentPane(userView.getProfilePanel());
			userView.repaint();
			userView.revalidate();
		}

	}

	private class Change implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			JButton src = (JButton) event.getSource();
			String str = "Please Enter Your New Value";

			String changed;
			try {
				changed = JOptionPane.showInputDialog(str).trim();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return;
			}
			String field = src.getName();

			Class<?>[] types = new Class[] { String.class, String.class };
			Object[] params = new Object[] { field, changed };
			boolean ret = false;
			try {
				ret = (Boolean) client.sendUserInput("editProfile", types, params);
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.getMessage());
				return;
			}

			if (!ret) {
				JOptionPane.showMessageDialog(userView, "Kindly Enter Valid Data", "warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (field.equals("USER_NAME")) {
					userView.setUsername(changed);
					userView.setWelcome2(changed);
				} else if (field.equals("PASSWORD")) {
					userView.setPassword(changed);
				} else if (field.equals("F_NAME")) {
					userView.setFname(changed);
				} else if (field.equals("L_NAME")) {
					userView.setLname(changed);
				} else if (field.equals("EMAIL")) {
					userView.setEmail(changed);
				} else if (field.equals("TELEPHONE")) {
					userView.setPhoneNo(changed);
				} else if (field.equals("ADDRESS")) {
					userView.setAddress(changed);
				}
			}
		}

	}

	private class BackPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			userView.setContentPane(userView.getMainPanel());
			userView.repaint();
			userView.revalidate();

		}

	}

	private class ShoppingCart implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent event) {

			JButton src = (JButton) event.getSource();

			if (src.getName().equals("Shopping Cart")) {

				Class<?>[] types = new Class[] {};
				Object[] params = new Object[] {};
				try {
					userView.viewShoppingCart((Map<String, Pair<String, Integer>>) client
							.sendUserInput("getShoppingCart", types, params));
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
					return;
				}
				userView.setContentPane(userView.getShoppingCartPanel());
				userView.getremoveButton().addActionListener(new RemovingItem());
				userView.getcheckOutButton().addActionListener(new CheckingOut());
				userView.getBackShoppingCart().addActionListener(new ShoppingCart());
				userView.repaint();
				userView.revalidate();
			} else if (src.getName().equals("Back")) {
				userView.setContentPane(userView.getMainPanel());
				userView.repaint();
				userView.revalidate();
			}

		}

	}

	private class Search implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

			boolean ISBNChecked = userView.getISBNCheck().isSelected();
			boolean categoryChecked = userView.getCategoryCheck().isSelected();
			boolean titleChecked = userView.getTitleCheck().isSelected();
			boolean authorChecked = userView.getAuthorCheck().isSelected();
			boolean publisherChecked = userView.getPublisherCheck().isSelected();
			boolean historyChecked = userView.getHistory().isSelected();
			boolean scienceChecked = userView.getScience().isSelected();
			boolean artChecked = userView.getArt().isSelected();
			boolean religionChecked = userView.getReligion().isSelected();
			boolean geographyChecked = userView.getGeography().isSelected();

			ArrayList<String> list;
			if (ISBNChecked) {
				list = new ArrayList<String>();
				list.add(userView.getISBNText().getText().trim());
				map.put("ISBN", list);
			}
			if (titleChecked) {
				list = new ArrayList<String>();
				list.add(userView.getTitleText().getText().trim());
				map.put("TITLE", list);
			}
			if (authorChecked) {
				list = new ArrayList<String>();
				list.add(userView.getAuthorText().getText().trim());
				map.put("AUTHOR", list);
			}
			if (publisherChecked) {
				list = new ArrayList<String>();
				list.add(userView.getPublisherText().getText().trim());
				map.put("PUBLISHER", list);
			}
			if (categoryChecked) {
				list = new ArrayList<String>();
				if (historyChecked) {
					list.add("history");
				}
				if (scienceChecked) {
					list.add("science");
				}
				if (artChecked) {
					list.add("art");
				}
				if (religionChecked) {
					list.add("religion");
				}
				if (geographyChecked) {
					list.add("geography");
				}
				map.put("CATEGORY", list);
			}

			try {
				Class<?>[] types = new Class[] { Map.class };
				Object[] params = new Object[] { map };

				CachedRowSetImpl set = (CachedRowSetImpl) client.sendUserInput("search", types, params);
				setToMap(set);
				ArrayList<JButton> buttons = userView.viewSearch(titles);

				for (JButton button : buttons) {
					button.addActionListener(new BookViewer());
				}

			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}

	}

	private class BookViewer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton src = (JButton) event.getSource();
			BookView view = new BookView(src.getName(), selectedBooks.get(src.getName()));
			selectedISBN = selectedBooks.get(src.getName()).get(0);
			view.getBuyItem().addActionListener(new BuyingBook());

		}
	}

	private class BuyingBook implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			String quantity = null;
			try {
				quantity = JOptionPane.showInputDialog("Enter Quantity").trim();
			} catch (Exception e) {
				return;
			}
			try {
				int copies = Integer.parseInt(quantity);
				if (copies < 0)
					throw new Exception();
				Class<?>[] types = new Class[] { String.class, int.class };
				Object[] params = new Object[] { selectedISBN, copies };
				client.sendUserInput("addItem", types, params);

			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	private class RemovingItem implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			String title = null;

			try {
				title = JOptionPane.showInputDialog("Enter Title of the book to be removed").trim().toLowerCase();
			} catch (Exception e) {
				return;
			}
			try {
				Class<?>[] types = new Class[] { String.class };
				Object[] params = new Object[] { title };
				client.sendUserInput("removeItem", types, params);

				JLabel lbl;
				if (userView.getLabels().containsKey(title)) {
					lbl = userView.getLabels().get(title);

					userView.getPan().remove(lbl);
					userView.repaint();
					userView.revalidate();
				} else {
					JOptionPane.showMessageDialog(userView, "This Book doesn't exist!", "warning",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private class CheckingOut implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent event) {

			if (userView.getLabels().size() != 0) {

				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				String value1 = null;
				String value2 = null;
				Object[] message = { "Credit Card Number:", field1, "Expiry Date:", field2 };

				int option = 0;
				try {
					option = JOptionPane.showConfirmDialog(userView, message, "Enter all your values",
							JOptionPane.OK_CANCEL_OPTION);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				if (option == JOptionPane.OK_OPTION) {
					value1 = field1.getText().trim();
					value2 = field2.getText().trim();
				}

				Class<?>[] types = new Class[] { String.class, Date.class };
				Object[] params = new Object[] { value1, Date.valueOf(value2) };
				Pair<Boolean, String> ret = null;
				try {
					ret = (Pair<Boolean, String>) client.sendUserInput("checkout", types, params);
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e.getMessage());
				}

				if (ret.getFirst()) {
					JOptionPane.showMessageDialog(userView, "Your Order is done :))");
				} else {
					JOptionPane.showMessageDialog(userView, ret.getSecond(), "warning", JOptionPane.WARNING_MESSAGE);
				}
				for (JLabel lbl : userView.getLabels().values()) {
					userView.getPan().remove(lbl);
					userView.repaint();
					userView.revalidate();
				}
				userView.getLabels().clear();
			} else {
				JOptionPane.showMessageDialog(userView, "Your Shopping Cart is Empty!", "warning",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	public void setToMap(CachedRowSetImpl set) {

		ArrayList<String> attributes;
		selectedBooks = new HashMap<String, ArrayList<String>>();
		titles = new ArrayList<String>();
		try {
			while (set.next()) {
				attributes = new ArrayList<String>();
				attributes.add(set.getString("ISBN"));
				attributes.add(set.getString("CATEGORY"));
				if (set.getString("AUTHOR") != null) {
					attributes.add(set.getString("AUTHOR"));
				} else {
					attributes.add("");
				}
				attributes.add(set.getString("PUBLISHER"));
				if (set.getDate("PUBLICATION_YEAR") != null) {
					attributes.add(set.getDate("PUBLICATION_YEAR").toString());
				} else {
					attributes.add("");
				}
				attributes.add(String.valueOf(set.getFloat("SELLING_PRICE")));
				selectedBooks.put(set.getString("TITLE"), attributes);
				titles.add(set.getString("TITLE"));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void setToMapManager(CachedRowSetImpl set) {

		ArrayList<String> attributes;
		booksManager = new HashMap<String, ArrayList<String>>();
		titlesManager = new ArrayList<String>();

		try {
			while (set.next()) {
				attributes = new ArrayList<String>();
				attributes.add(set.getString("ISBN"));
				attributes.add(set.getString("TITLE"));
				attributes.add(String.valueOf(set.getFloat("SELLING_PRICE")));
				attributes.add(set.getString("CATEGORY"));
				attributes.add(String.valueOf(set.getInt("THRESHOLD")));
				attributes.add(String.valueOf(set.getInt("COPIES")));
				attributes.add(set.getString("PUBLISHER"));

				if (set.getDate("PUBLICATION_YEAR") != null) {
					attributes.add(set.getDate("PUBLICATION_YEAR").toString());
				} else {
					attributes.add("");
				}
				attributes.add(set.getString("AUTHOR"));
				booksManager.put(set.getString("TITLE"), attributes);
				titlesManager.add(set.getString("TITLE"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private class Manager implements ActionListener {

		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e) {

			JButton src = (JButton) e.getSource();

			if (src.getName().equals("manager")) {

				userView.setContentPane(userView.getManagerPanel());
				userView.repaint();
				userView.revalidate();

			} else if (src.getName().equals("Add Book")) {

				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				JTextField field3 = new JTextField();
				JTextField field4 = new JTextField();
				JTextField field5 = new JTextField();
				JTextField field6 = new JTextField();
				JTextField field7 = new JTextField();
				JTextField field8 = new JTextField();
				JTextField field9 = new JTextField();

				String value1 = null, value2 = null, value3 = null, value4 = null, value5 = null, value6 = null,
						value7 = null, value8 = null, value9 = null;
				Object[] message = { "ISBN:", field1, "Title:", field2, "Category:", field3, "Author:", field4,
						"Publisher:", field5, "Publication Year:", field6, "Price:", field7, "Threshold:", field8,
						"Copies:", field9 };
				int option = 0;
				try {
					option = JOptionPane.showConfirmDialog(userView, message, "Enter all your values",
							JOptionPane.OK_CANCEL_OPTION);
					if (option == JOptionPane.OK_OPTION) {
						value1 = field1.getText().trim();
						value2 = field2.getText().trim();
						value3 = field3.getText().trim();
						value4 = field4.getText().trim();
						value5 = field5.getText().trim();
						value6 = field6.getText().trim();
						value7 = field7.getText().trim();
						value8 = field8.getText().trim();
						value9 = field9.getText().trim();
					}
					Object arr[] = new Object[9];
					arr[0] = (value1.isEmpty()) ? null : value1;
					arr[1] = (value2.isEmpty()) ? null : value2;
					arr[2] = (value7.isEmpty()) ? null : Double.valueOf(value7);
					arr[3] = (value3.isEmpty()) ? null : value3;
					arr[4] = (value8.isEmpty()) ? null : Integer.valueOf(value8);
					arr[5] = (value9.isEmpty()) ? null : Integer.valueOf(value9);
					arr[6] = (value5.isEmpty()) ? null : value5;
					arr[7] = (value6.isEmpty()) ? null : Date.valueOf(value6);
					arr[8] = (value4.isEmpty()) ? null : value4;

					Class<?>[] types = new Class[] { Object[].class };
					Object[] params = new Object[] { arr };
					Pair<Boolean, String> ret;
					ret = (Pair<Boolean, String>) client.sendUserInput("addBook", types, params);
					if (!ret.getFirst()) {
						JOptionPane.showMessageDialog(userView, ret.getSecond(), "warning",
								JOptionPane.WARNING_MESSAGE);

					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("Modify Book")) {

				CachedRowSetImpl ret = null;

				Class<?>[] types = new Class[] {};
				Object[] params = new Object[] {};

				try {
					ret = (CachedRowSetImpl) client.sendUserInput("getBooks", types, params);
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
					return;
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
					return;
				}

				ArrayList<JButton> btns = null;

				setToMapManager(ret);

				btns = userView.viewBooks(titlesManager);

				for (JButton button : btns) {
					button.addActionListener(new BookModification());
				}

			} else if (src.getName().equals("Place Order")) {

				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				Pair<Boolean, String> ret;

				String value1 = null, value2 = null;
				Object[] message = { "ISBN:", field1, "Quantity:", field2 };
				int option = 0;
				try {
					option = JOptionPane.showConfirmDialog(userView, message, "Enter all your values",
							JOptionPane.OK_CANCEL_OPTION);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					return;
				}
				if (option == JOptionPane.OK_OPTION) {

					try {
						value1 = field1.getText().trim();
						value2 = field2.getText().trim();

						Class<?>[] types = new Class[] { String.class, int.class };
						Object[] params = new Object[] { value1, Integer.parseInt(value2) };
						ret = (Pair<Boolean, String>) client.sendUserInput("orderBook", types, params);
						if (!ret.getFirst()) {
							JOptionPane.showMessageDialog(userView, "Invalid ISBN!", "warning",
									JOptionPane.WARNING_MESSAGE);
						}

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}

			} else if (src.getName().equals("Confirm Order")) {

				ArrayList<JButton> btns;
				try {
					Class<?>[] types = new Class[] {};
					Object[] params = new Object[] {};
					btns = userView.viewOrders((CachedRowSetImpl) client.sendUserInput("getOrders", types, params));
					for (JButton button : btns) {
						button.addActionListener(new OrderConfirmation());
					}
				} catch (ClassNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}

			} else if (src.getName().equals("Promote Customer")) {

				ArrayList<JButton> btns;
				try {
					Class<?>[] types = new Class[] {};
					Object[] params = new Object[] {};
					btns = userView
							.viewCustomers((CachedRowSetImpl) client.sendUserInput("getCustomers", types, params));
					for (JButton button : btns) {
						button.addActionListener(new CustomerPromotion());
					}
				} catch (ClassNotFoundException e1) {
					System.out.println(e1.getMessage());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}

			} else if (src.getName().equals("View Reports")) {
				try {
					Class<?>[] types = new Class[] {};
					Object[] params = new Object[] {};
					client.sendUserInput("getTotalSales", types, params);
					client.sendUserInput("getTopFiveUsers", types, params);
					client.sendUserInput("getTopTenBooks", types, params);

				} catch (ClassNotFoundException | IOException e1) {
					System.out.println(e1.getMessage());
				}

			} else if (src.getName().equals("Back")) {

				userView.setContentPane(userView.getMainPanel());
				userView.repaint();
				userView.revalidate();

			}

		}
	}

	private class OrderConfirmation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				JButton src = (JButton) e.getSource();
				String[] words = src.getName().split(",");
				Class<?>[] types = new Class[] { String.class, int.class };
				Object[] params = new Object[] { words[1], Integer.parseInt(words[0]) };
				client.sendUserInput("confirmOrder", types, params);
				src.setVisible(false);
			} catch (ClassNotFoundException | IOException e1) {
				System.out.println(e1.getMessage());
			}
		}

	}

	private class CustomerPromotion implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				JButton src = (JButton) e.getSource();
				Class<?>[] types = new Class[] { String.class };
				Object[] params = new Object[] { src.getName() };
				client.sendUserInput("promoteUser", types, params);
				src.setVisible(false);
			} catch (ClassNotFoundException | IOException e1) {
				System.out.println(e1.getMessage());
			}
		}
	}

	private class BookModification implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JButton src = (JButton) e.getSource();
			modifyView = new BookModificationView(src.getName(), booksManager.get(src.getName()));
			selectedISBNManager = booksManager.get(src.getName()).get(0);

			modifyView.getChangeISBN().addActionListener(new BookChanging());
			modifyView.getChangeTitle().addActionListener(new BookChanging());
			modifyView.getChangeCategory().addActionListener(new BookChanging());
			modifyView.getChangePublisher().addActionListener(new BookChanging());
			modifyView.getChangeAuthor().addActionListener(new BookChanging());
			modifyView.getChangeThreshold().addActionListener(new BookChanging());
			modifyView.getChangeCopies().addActionListener(new BookChanging());
			modifyView.getChangeYear().addActionListener(new BookChanging());
			modifyView.getChangePrice().addActionListener(new BookChanging());

		}

	}

	private class BookChanging implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Class<?>[] types = new Class[] { String.class, int.class, Object.class };
			Object[] params;
			boolean ret;

			JButton src = (JButton) e.getSource();

			if (src.getName().equals("ISBN")) {
				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New ISBN").trim();
					params = new Object[] { selectedISBNManager, 1, changed };

					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setISBN(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}
			} else if (src.getName().equals("TITLE")) {

				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Title").trim();
					params = new Object[] { selectedISBNManager, 2, changed };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setTitle2(changed);
						modifyView.setWelcome2(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}
			} else if (src.getName().equals("CATEGORY")) {

				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Category").trim().toLowerCase();
					params = new Object[] { selectedISBNManager, 4, changed };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setCategory(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}

				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("PUBLICATION_YEAR")) {

				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Publication Year").trim();
					params = new Object[] { selectedISBNManager, 8, Date.valueOf(changed) };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setYear(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("PUBLISHER")) {

				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Publisher").trim();
					params = new Object[] { selectedISBNManager, 7, changed };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setPublisher(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("AUTHOR")) {

				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Author").trim();
					params = new Object[] { selectedISBNManager, 9, changed };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setAuthor(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("PRICE")) {
				ret = false;
				String changed;
				try {
					changed = JOptionPane.showInputDialog("Enter New Price").trim();
					params = new Object[] { selectedISBNManager, 3, Double.valueOf(changed) };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setPrice(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("THRESHOLD")) {

				String changed;
				ret = false;
				try {
					changed = JOptionPane.showInputDialog("Enter New Threshold").trim();
					params = new Object[] { selectedISBNManager, 5, Integer.valueOf(changed) };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setThreshold(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("COPIES")) {

				String changed;
				ret = false;
				try {
					changed = JOptionPane.showInputDialog("Enter New Number of Copies").trim();
					params = new Object[] { selectedISBNManager, 6, Integer.valueOf(changed) };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setCopies(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			} else if (src.getName().equals("PUBLICATION_YEAR")) {

				String changed;
				ret = false;
				try {
					changed = JOptionPane.showInputDialog("Enter New Number of Copies").trim();
					params = new Object[] { selectedISBNManager, 6, Integer.valueOf(changed) };
					ret = (Boolean) client.sendUserInput("modifyBook", types, params);
					if (ret) {
						modifyView.setCopies(changed);
					} else {
						JOptionPane.showMessageDialog(userView, "Enter Valid Data!", "warning",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException e2) {
					System.out.println(e2.getMessage());
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(userView, "INVALID USER INPUT", "warning",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		}

	}

	private class LoggingOut implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Class<?>[] types = new Class[] {};
			Object[] params = new Object[] {};
			try {
				client.sendUserInput("logout", types, params);
				userView.setVisible(false);
				try {
					new Engine();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				System.out.println(e1.getMessage());
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}

		}

	}

	// Launch the application.
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		new Engine();
	}
}