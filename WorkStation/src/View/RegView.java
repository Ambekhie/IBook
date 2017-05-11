package View;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class RegView {

	private JFrame frame;
	private final JPanel panel = new JPanel();
	private JTextPane tabOneUserName;
	private JPasswordField tabOnePassword;
	private JButton logIn;
	private JTextPane firstName;
	private JTextPane lastName;
	private JTextPane tabTwoUserName;
	private JTextPane phone;
	private JTextPane address;
	private JTextPane email;
	private JTextPane tabTwoPassword;
	private JButton submit;
	private JTabbedPane tabbedPane;
	/**
	 * Create the application.
	 */
	public RegView() {
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setBounds(100, 100, 445, 380);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Sign In", null, this.panel, null);
		this.panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(220, 5, 0, 0);
		this.panel.add(label);
		
		this.tabOneUserName = new JTextPane();
		this.tabOneUserName.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 16));
		this.tabOneUserName.setForeground(UIManager.getColor("EditorPane.caretForeground"));
		this.tabOneUserName.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		this.tabOneUserName.setToolTipText("Enter your User Name");
		this.tabOneUserName.setBounds(56, 110, 310, 46);
		this.panel.add(this.tabOneUserName);
		
		this.tabOnePassword = new JPasswordField();
		this.tabOnePassword.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.tabOnePassword.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		this.tabOnePassword.setToolTipText("Enter your Password");
		this.tabOnePassword.setBounds(56, 195, 310, 44);
		this.panel.add(this.tabOnePassword);
		
		this.logIn = new JButton("Log In");
		this.logIn.setBackground(UIManager.getColor("Button.focus"));
		this.logIn.setBounds(152, 272, 117, 35);
		this.panel.add(this.logIn);
		
		JLabel label1 = new JLabel("User Name");
		label1.setBackground(UIManager.getColor("Button.highlight"));
		label1.setForeground(UIManager.getColor("RadioButtonMenuItem.background"));
		label1.setBounds(56, 90, 82, 15);
		this.panel.add(label1);
		
		JLabel label2 = new JLabel("Password");
		label2.setForeground(UIManager.getColor("ScrollBar.background"));
		label2.setBounds(56, 168, 70, 15);
		this.panel.add(label2);
		JLabel label10 = new JLabel(new ImageIcon(RegView.class.getResource("/pic2.jpg")));
		label10.setBounds(0, 0, 441, 358);
		this.panel.add(label10);
		
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("Sign Up", null, panel1, null);
		panel1.setLayout(null);
		
		JLabel label3 = new JLabel("First Name");
		label3.setBounds(12, 12, 87, 15);
		panel1.add(label3);
		
		this.firstName = new JTextPane();
		this.firstName.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.firstName.setSelectionColor(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.firstName.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.firstName.setBounds(12, 30, 181, 30);
		panel1.add(this.firstName);
		
		JLabel label4 = new JLabel("Last Name");
		label4.setBounds(217, 12, 87, 15);
		panel1.add(label4);
		
		this.lastName = new JTextPane();
		this.lastName.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.lastName.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.lastName.setBounds(217, 30, 207, 30);
		panel1.add(this.lastName);
		
		JLabel label7 = new JLabel("User Name");
		label7.setBounds(12, 115, 87, 15);
		panel1.add(label7);
		
		this.tabTwoUserName = new JTextPane();
		this.tabTwoUserName.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.tabTwoUserName.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.tabTwoUserName.setBounds(12, 142, 412, 30);
		panel1.add(this.tabTwoUserName);
		
		JLabel label5 = new JLabel("Phone");
		label5.setBounds(12, 63, 70, 15);
		panel1.add(label5);
		
		this.phone = new JTextPane();
		this.phone.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.phone.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.phone.setBounds(12, 82, 181, 30);
		panel1.add(this.phone);
		
		JLabel label6 = new JLabel("Address");
		label6.setBounds(217, 63, 70, 15);
		panel1.add(label6);
		
		this.address = new JTextPane();
		this.address.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.address.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.address.setBounds(217, 82, 207, 30);
		panel1.add(this.address);
		
		JLabel label8 = new JLabel("Email");
		label8.setBounds(12, 179, 70, 15);
		panel1.add(label8);
		
		this.email = new JTextPane();
		this.email.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.email.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.email.setBounds(12, 207, 412, 30);
		panel1.add(this.email);
		
		JLabel label9 = new JLabel("Password");
		label9.setBounds(12, 240, 70, 15);
		panel1.add(label9);
		
		this.tabTwoPassword = new JTextPane();
		this.tabTwoPassword.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 15));
		this.tabTwoPassword.setBackground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
		this.tabTwoPassword.setBounds(12, 267, 412, 30);
		panel1.add(this.tabTwoPassword);
		
		this.submit = new JButton("Submit");
		this.submit.setBounds(138, 300, 117, 20);
		panel1.add(this.submit);
		
		JLabel label11 = new JLabel("");
		label11.setIcon(new ImageIcon(RegView.class.getResource("/pic2.jpg")));
		label11.setBounds(0, 0, 441, 350);
		panel1.add(label11);
	}
	public JFrame getMainFrame() {
		return this.frame;
	}
	public JTextPane getTabOneUserName() {
		return this.tabOneUserName;
	}
	public JTextPane getTabTwoUserName() {
		return this.tabTwoUserName;
	}
	public JTextPane getTabTwoPassword() {
		return this.tabTwoPassword;
	}
	public JPasswordField getTabOnePassword() {
		return this.tabOnePassword;
	}
	public JTextPane getPhone() {
		return this.phone;
	}
	public JTextPane getAddress() {
		return this.address;
	}
	public JTextPane getEmail() {
		return this.email;
	}
	public JTextPane getFirstName() {
		return this.firstName;
	}
	public JTextPane getLastName() {
		return this.lastName;
	}
	public JButton getLogIn() {
		return this.logIn;
	}
	public JButton getSubmit() {
		return this.submit;
	}
	
	public JTabbedPane getTappedPane (){
		return this.tabbedPane;
	}
}
