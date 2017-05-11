package View;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BookModificationView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel modifyPanel;

	private JButton changeISBN;
	private JButton changeTitle;
	private JButton changeCategory;
	private JButton changePublisher;
	private JButton changeYear;
	private JButton changeAuthor;
	private JButton changeCopies;
	private JButton changePrice;
	private JButton changeThreshold;

	private JLabel ISBN;
	private JLabel title;
	private JLabel category;
	private JLabel publisher;
	private JLabel year;
	private JLabel author;
	private JLabel threshold;
	private JLabel copies;
	private JLabel price;

	private JLabel welcome2;

	public BookModificationView(String bookTitle, ArrayList<String> arrayList) {

		this.setSize(500, 700);
		this.setTitle("Book Store");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		modifyPanel = new JPanel();
		modifyPanel.setLayout(null);
		modifyPanel.setBackground(new Color(204, 255, 204));

		welcome2 = new JLabel(bookTitle);
		welcome2.setFont(new Font("Monospaced", Font.BOLD, 32));
		welcome2.setLocation(125, 5);
		welcome2.setSize(250, 75);
		welcome2.setForeground(new Color(0, 38, 153));
		modifyPanel.add(welcome2);

		this.ISBN = new JLabel(arrayList.get(0));
		this.title = new JLabel(arrayList.get(1));
		this.category = new JLabel(arrayList.get(3));
		this.publisher = new JLabel(arrayList.get(6));
		this.year = new JLabel(arrayList.get(7));
		this.threshold = new JLabel(arrayList.get(4));
		this.copies = new JLabel(arrayList.get(5));
		this.price = new JLabel(arrayList.get(2));
		this.author = new JLabel(arrayList.get(8));

		TitledBorder title1;
		title1 = BorderFactory.createTitledBorder(null, "ISBN", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));

		ISBN.setBorder(title1);

		ISBN.setFont(new Font("Serif", Font.BOLD, 24));
		ISBN.setLocation(20, 75);
		ISBN.setSize(400, 60);

		modifyPanel.add(ISBN);

		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder(null, "Title", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		title.setBorder(title2);
		title.setFont(new Font("Serif", Font.BOLD, 24));
		title.setLocation(20, 140);
		title.setSize(400, 60);
		modifyPanel.add(title);

		TitledBorder title3;
		title3 = BorderFactory.createTitledBorder(null, "Category", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		category.setBorder(title3);
		category.setFont(new Font("Serif", Font.BOLD, 24));
		category.setLocation(20, 205);
		category.setSize(400, 60);
		modifyPanel.add(category);

		TitledBorder title4;
		title4 = BorderFactory.createTitledBorder(null, "Publisher", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		publisher.setBorder(title4);
		publisher.setFont(new Font("Serif", Font.BOLD, 24));
		publisher.setLocation(20, 270);
		publisher.setSize(400, 60);
		modifyPanel.add(publisher);

		TitledBorder title5;
		title5 = BorderFactory.createTitledBorder(null, "Publication Year", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		year.setBorder(title5);

		year.setFont(new Font("Serif", Font.BOLD, 24));
		year.setLocation(20, 335);
		year.setSize(400, 60);
		modifyPanel.add(year);

		TitledBorder title6;
		title6 = BorderFactory.createTitledBorder(null, "Author", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		author.setBorder(title6);
		author.setFont(new Font("Serif", Font.BOLD, 24));
		author.setLocation(20, 400);
		author.setSize(400, 60);
		modifyPanel.add(author);

		TitledBorder title7;
		title7 = BorderFactory.createTitledBorder(null, "Threshold", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		threshold.setBorder(title7);
		threshold.setFont(new Font("Serif", Font.BOLD, 24));
		threshold.setLocation(20, 465);
		threshold.setSize(400, 60);
		modifyPanel.add(threshold);

		TitledBorder title8;
		title8 = BorderFactory.createTitledBorder(null, "Copies", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		copies.setBorder(title8);
		copies.setFont(new Font("Serif", Font.BOLD, 24));
		copies.setLocation(20, 530);
		copies.setSize(400, 60);
		modifyPanel.add(copies);

		TitledBorder title9;
		title9 = BorderFactory.createTitledBorder(null, "Price", TitledBorder.LEFT, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 20), new Color(121, 121, 210));
		price.setBorder(title9);
		price.setFont(new Font("Serif", Font.BOLD, 24));
		price.setLocation(20, 595);
		price.setSize(400, 60);
		modifyPanel.add(price);

		changeISBN = new JButton();
		changeISBN.setName("ISBN");
		changeTitle = new JButton();
		changeTitle.setName("TITLE");
		changeCategory = new JButton();
		changeCategory.setName("CATEGORY");
		changePublisher = new JButton();
		changePublisher.setName("PUBLISHER");
		changeYear = new JButton();
		changeYear.setName("PUBLICATION_YEAR");
		changeAuthor = new JButton();
		changeAuthor.setName("AUTHOR");
		changeCopies = new JButton();
		changeCopies.setName("COPIES");
		changeThreshold = new JButton();
		changeThreshold.setName("THRESHOLD");
		changePrice = new JButton();
		changePrice.setName("PRICE");

		changeISBN.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeTitle.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeCategory.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changePublisher.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeYear.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeAuthor.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeCopies.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changeThreshold.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));
		changePrice.setIcon(new ImageIcon(UserView.class.getResource("/user_edit.png")));

		changeISBN.setLocation(425, 80);
		changeISBN.setSize(50, 50);
		modifyPanel.add(changeISBN);

		changeTitle.setLocation(425, 145);
		changeTitle.setSize(50, 50);
		modifyPanel.add(changeTitle);

		changeCategory.setLocation(425, 210);
		changeCategory.setSize(50, 50);
		modifyPanel.add(changeCategory);

		changePublisher.setLocation(425, 275);
		changePublisher.setSize(50, 50);
		modifyPanel.add(changePublisher);

		changeYear.setLocation(425, 340);
		changeYear.setSize(50, 50);
		modifyPanel.add(changeYear);

		changeAuthor.setLocation(425, 405);
		changeAuthor.setSize(50, 50);
		modifyPanel.add(changeAuthor);

		changeThreshold.setLocation(425, 470);
		changeThreshold.setSize(50, 50);
		modifyPanel.add(changeThreshold);

		changeCopies.setLocation(425, 535);
		changeCopies.setSize(50, 50);
		modifyPanel.add(changeCopies);

		changePrice.setLocation(425, 600);
		changePrice.setSize(50, 50);
		modifyPanel.add(changePrice);

		this.setContentPane(modifyPanel);
		this.repaint();
		this.revalidate();
		this.setVisible(true);

	}

	public JButton getChangeISBN() {
		return changeISBN;
	}

	public JButton getChangeTitle() {
		return changeTitle;
	}

	public JButton getChangeCategory() {
		return changeCategory;
	}

	public JButton getChangePublisher() {
		return changePublisher;
	}

	public JButton getChangeYear() {
		return changeYear;
	}

	public JButton getChangeAuthor() {
		return changeAuthor;
	}

	public JButton getChangeCopies() {
		return changeCopies;
	}

	public JButton getChangePrice() {
		return changePrice;
	}

	public JButton getChangeThreshold() {
		return changeThreshold;
	}

	public void setISBN(String iSBN) {
		this.ISBN.setText(iSBN);
	}

	public void setTitle2(String title) {
		this.title.setText(title);
	}

	public void setCategory(String category) {
		this.category.setText(category);
	}

	public void setPublisher(String publisher) {
		this.publisher.setText(publisher);
	}

	public void setAuthor(String author) {
		this.author.setText(author);
	}

	public void setThreshold(String threshold) {
		this.threshold.setText(threshold);
	}

	public void setCopies(String copies) {
		this.copies.setText(copies);
	}

	public void setPrice(String price) {
		this.price.setText(price);
	}

	public void setYear(String year) {
		this.year.setText(year);
	}

	public void setWelcome2(String welcome2) {
		this.welcome2.setText(welcome2);
	}
}