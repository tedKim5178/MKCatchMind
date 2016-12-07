import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WaitingRoom extends JFrame implements ActionListener{
	
	JPanel global = new JPanel();
	GridLayout gridLayout1 = new GridLayout();
	List userList = new List();		// 유저 리스트 목록
	List roomList = new List();		// 방 리스트 목록
	JPanel buttons = new JPanel();		// 나가기/ 방만들기 버튼/ 방 참여하기 버튼
	JPanel chatting = new JPanel();	// 채팅창
	JPanel list = new JPanel();
	
	// ---- buttons 에 들어갈 button들 ----
	JButton exit 		= new JButton("나가기");
	JButton makeRoom 	= new JButton("방만들기");
	JButton enterRoom 	= new JButton("방들어가기");
	
	// ---- chatting ----
	JTextArea area = new JTextArea();
	JScrollPane jsP = new JScrollPane();
	JTextField text = new JTextField();
	
	// Network
	PrintWriter pw = null;

	// ID
	String id = null;
	
	// Game Room
	GameRoom gameRoom = null;
	
	// User
	UserInfo user= null;
	public WaitingRoom(PrintWriter pw) throws Exception{
		// TODO Auto-generated constructor stub
		this.pw = pw;
		gameRoom = new GameRoom(pw);
	}
	
	public WaitingRoom(PrintWriter pw , UserInfo user){
		this.pw = pw;
		this.user = user;
	}
	
	public WaitingRoom() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		this.getContentPane().setBackground(new Color(249,255,255));
		this.setResizable(true);
		this.setBounds(1000, 400, 500, 500);
		this.getContentPane().setLayout(null);
		//global.setBorder(BorderFactory.createEtchedBorder());
		//global.setOpaque(false);
		//global.setBounds(new Rectangle(0,0, 600, 600));
		//global.setLayout(null);
		//gridLayout1.setRows(3);
		//gridLayout1.setColumns(1);
		
		userList.setBounds(300,0,200,300);
		roomList.setBounds(300,300,200,150);
		
		chatting.setLayout(null);
		
		
		chatting.setBounds(0,0,300,500);
		area.setBounds(0,0,300,500);
		text.setBounds(0,480,300,20);
		jsP.setAutoscrolls(true);
		jsP.setBounds(0,0,300,600);
		
		
		exit.setBounds(300, 475, 200, 25);
		makeRoom.setBounds(300, 450, 100, 25);
		enterRoom.setBounds(400, 450, 100, 25);
		
		//buttons.setOpaque(false);
		//buttons.setLayout(null);
		//list.setOpaque(false);
		list.setLayout(null);
		//list.setOpaque(false);
		
		
		
		//this.getContentPane().add(global, null);
		this.getContentPane().add(buttons, null);
		this.getContentPane().add(chatting, null);
		this.getContentPane().add(roomList, null);
		this.getContentPane().add(userList, null);
		this.getContentPane().add(makeRoom, null);
		this.getContentPane().add(enterRoom, null);
		this.getContentPane().add(exit, null);
		//global.add(buttons, null);
		//global.add(list, null);
		//list.add(userList, null);
		//list.add(roomList, null);
		//global.add(chatting, null);
		chatting.add(area, null);
		chatting.add(text, null);
		chatting.add(jsP, null);
		jsP.getViewport().add(area, null);
		setBounds(200,200, 520, 550);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		text.addActionListener(this);
		makeRoom.addActionListener(this);
		enterRoom.addActionListener(this);
		exit.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		Object ob = e.getSource();
		if(ob == text){
			// textField로 부터 text를 가져와서 서버에 write 해주자!
			String msg = "0;" + id + ";" + text.getText();
			text.setText("");
			try {
				pw.println(msg);
				pw.flush();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		// 방만들기 버튼을 만든다면 이제 게임방(그림그리는 방 출력해줘야됨)
		}else if(ob == makeRoom){
			// 그림 그리는 방 화면에 보여주자
			// 우선 방 제목 입력
			String roomName = JOptionPane.showInputDialog("방제목을 입력하세요");
			String msg = "400;" + id + ";" + roomName + ";";
			
			// 보내주고 여기서 하나 새롭게 만들자.
			gameRoom = new GameRoom(pw, user);
			gameRoom.id = user.id;
			gameRoom.user.host = true;
			gameRoom.init();
			
			try {
				pw.println(msg);
				pw.flush();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}else if(ob == exit){
			
			String msg = "900;" + id + ";";
			try {
				pw.println(msg);
				pw.flush();
				System.exit(1);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}else if(ob == enterRoom){
			// 1번 방 선택했으면 
			roomList.getItem(roomList.getSelectedIndex());
			String roomName = roomList.getSelectedItem();
			// 들어가는거 해줬으면 또 메세지 보내줘야함
			String msg = "200;" + id + ";" + roomName + ";";
			try {
				pw.println(msg);
				pw.flush();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			gameRoom = new GameRoom(pw, user);
			gameRoom.id = user.id;
			gameRoom.init();
		}
	}
	
}
