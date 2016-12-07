import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginView extends JFrame implements ActionListener {

	PrintWriter pw = null;

	// �ʿ��Ѱ� : ���̵�, ��й�ȣ ĭ, �α���, ȸ������ ��ư

	// �Է� ĭ
	JTextField ID = new JTextField();
	JTextField password = new JTextField();

	// ��ư��
	JButton login = new JButton("�α���");
	//JButton register = new JButton();

	// �Է� ĭ �ǳ�
	JPanel input = new JPanel();

	// ��ư�� �ǳ�
	JPanel buttons = new JPanel();
	String message = null;
	
	JLabel idLabel = new JLabel("ID�� �Է��ϼ���");
	JLabel passwordLabel = new JLabel("��й�ȣ�� �Է��ϼ���");

	// bwjassd
	UserInfo user;
	
	WaitingRoom room = null;
	public LoginView(PrintWriter pw, UserInfo user) {
		try {
			this.user = user;
			room = new WaitingRoom(pw, user);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// TODO Auto-generated constructor stub
		this.pw = pw;

		// �α��� ȭ�� �ش� �ϴ� �� ������.
		this.getContentPane().setBackground(new Color(236, 197, 0));
		this.setResizable(true);
		this.setBounds(1000, 400, 400, 400);
		this.getContentPane().setLayout(null);
		input.setLayout(null);
		buttons.setLayout(null);

		input.setBounds(10, 10, 350, 200);
		buttons.setBounds(10, 220, 350, 90);

		ID.setBounds(10, 40, 330, 40);
		idLabel.setBounds(10,10,330,20);
		password.setBounds(10, 130, 330, 40);
		passwordLabel.setBounds(10, 100, 330, 20);
		login.setBounds(10, 10, 330, 60);
		//register.setBounds(60, 10, 40, 30);
		
		this.getContentPane().add(input, null);
		this.getContentPane().add(buttons, null);
		input.add(ID, null);
		input.add(password, null);
		input.add(idLabel, null);
		input.add(passwordLabel, null);
		buttons.add(login, null);
		//buttons.add(register, null);

		this.setVisible(true);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		ID.addActionListener(this);
		password.addActionListener(this);
		login.addActionListener(this);
		//register.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		// login ��ư�� ������ ������ 100;id �޼����� �����ش�.
		if (ob == login) {

			this.user.id = ID.getText();
			System.out.println(user.id);
			// waitingroom�� id�� �����������!
			room.id = user.id;
			// �޼��� �����
			message = "100;" + user.id;
			System.out.println(message);
			// ������ ������..!
			room.init();
			try {
				pw.println(message);
				pw.flush();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			// register(ȸ������ �������)
		} 
	}
	
	
}
