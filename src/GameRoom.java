import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class GameRoom extends JFrame implements MouseMotionListener, MouseListener, ActionListener{
	
	PrintWriter pw = null;
	JPanel PaintPanel = null;
	JComboBox jComboBox = null;
	JPanel canvas = null;
	
	// ----- chatting -----
	JPanel chatting = new JPanel();
	JTextArea area = new JTextArea();
	JScrollPane jsP = new JScrollPane();
	JTextField text = new JTextField();
	
	// StopWatch
	JTextArea watch = new JTextArea();
	
	// userlist
	List userListField = null;
	
	//JTextField userListField = new JTextField();
	// 게임시작/ 나가기 버튼
	JPanel buttons = null;
	JButton start = null;
	JButton exit = null;
	
	// 색들
	JPanel colors = null;
	JButton white = null;
	JButton red = null;
	JButton orange = null;
	JButton yellow = null;
	JButton green = null;
	JButton blue = null;
	JButton black = null;
	JButton eraser = null;
	
	// 그래픽
	Graphics g = null;
	Graphics2D gg = null;
	
	// 선크기
	JTextField thickness = null;
	int lineThick = 1;
	
	// 선좌표
	int x1, y1, x2, y2;
	int w, h;
	int ox, oy;
	
	// 선 칼라
	Color lineColor = null;
	BasicStroke bs = null;
	
	// 콤보박스(그릴 도형의 종류)
	JComboBox shape = null;
	int type = 0;
	
	// id
	String id = "";
	
	// User
	UserInfo user;
	public GameRoom(){
	}
	
	public GameRoom(PrintWriter pw) {
		// TODO Auto-generated constructor stub
		this.pw = pw;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public GameRoom(PrintWriter pw, UserInfo user){
		this.pw = pw;
		this.user = user;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	// init 함수에서 화면에 보여주자..! 뭐를? 게임방을..! 
	// 우선 게임방은 채팅 할 수 있는 채팅 창, 게임이 보여지는 게임창, 들어와있는 유저들이 있는 유저들!
	public void init(){
		this.setLayout(null);
		this.setBounds(100,100,1000,1200);
		this.setBackground(Color.BLACK);
		canvas = new JPanel();
		canvas.setBorder(new LineBorder(new Color(0,0,0)));
		canvas.setBounds(190,12,674,541);
		canvas.setLayout(null);
		canvas.setBackground(Color.WHITE);
		this.getContentPane().add(canvas, null);
		
		// 채팅창 넣자
		chatting.setBounds(190,553,674,200);
		chatting.setLayout(null);
		area.setBounds(0,0,674,100);
		text.setBounds(0,160,674,20);
		
		area.setEditable(false);
		jsP.setAutoscrolls(true);
		jsP.setBounds(0,0,674,160);
		this.getContentPane().add(chatting, null);
		chatting.add(area, null);
		chatting.add(text, null);
		chatting.add(jsP, null);
		
		// 스탑워치
		watch.setBounds(0,350,100,200);
		this.getContentPane().add(watch, null);
		
		// 유저 리스트
		userListField = new List();
		userListField.setBounds(0,0,100,100);
		this.getContentPane().add(userListField, null);
		
		// 버튼들 넣자
		buttons = new JPanel();
		exit = new JButton("나가기");
		start = new JButton("게임시작");
		
		buttons.setBounds(650, 800, 300, 300);
		buttons.setLayout(null);
		exit.setBounds(150, 0, 150, 150);
		start.setBounds(0, 0, 150, 150);
		this.getContentPane().add(buttons, null);
		buttons.add(exit);
		buttons.add(start);
		
		jsP.getViewport().add(area, null);
		// 칼라들 집합
		colors = new JPanel();
		colors.setBorder(new LineBorder(new Color(0,0,0)));
		colors.setBounds(0, 464, 674, 77);
		colors.setLayout(new GridLayout(1, 0, 0, 0));
		canvas.add(colors);
		
		// White
		white = new JButton("White");
		white.setBackground(Color.WHITE);
		white.addActionListener(this);
		colors.add(white);
		
		// Red
		red = new JButton("Red");
		red.setBackground(Color.RED);
		 red.addActionListener(this);
		colors.add(red);
		
		// orange
		orange = new JButton("Orange");
		orange.setBackground(Color.ORANGE);
		orange.addActionListener(this);
		colors.add(orange);
		
		// yellow
		yellow = new JButton("Yellow");
		yellow.setBackground(Color.YELLOW);
		yellow.addActionListener(this);
		colors.add(yellow);
		
		// green
		green = new JButton("Green");
		green.setBackground(Color.GREEN);
		green.addActionListener(this);
		colors.add(green);
		
		// Blue
		blue = new JButton("Blue");
		blue.setBackground(Color.BLUE);
		blue.addActionListener(this);
		colors.add(blue);
		
		// Black
		black = new JButton("Black");
		black.setBackground(Color.BLACK);
		black.addActionListener(this);
		colors.add(black);
		
		eraser = new JButton("Eraser");
		eraser.setBackground(new Color(255,255,224));
		eraser.addActionListener(this);
		colors.add(eraser);
		
		// 선크기
		thickness = new JTextField("1");
		thickness.setBounds(0,0,150,20);
		this.getContentPane().add(thickness);
		
		// 액션리스너 설정
		text.addActionListener(this);
		thickness.addActionListener(null);
		shape = new JComboBox();
		shape.addItemListener(new java.awt.event.ItemListener(){
			public void itemStateChanged(java.awt.event.ItemEvent e){
				if(ItemEvent.SELECTED == e.getStateChange()){
					type = shape.getSelectedIndex();
				}
			}
		});
		
		shape.addItem("선");
		shape.addItem("원");
		shape.addItem("사각");
		shape.addItem("펜");
		shape.setBounds(10,200, 100,50);
		this.getContentPane().add(shape);
		
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e){
				
				String msg = "1000;" + id + ";";
				try {
					pw.println(msg);
					pw.flush();
					setVisible(false);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		g = getGraphics();
		gg = (Graphics2D)g;
		System.out.println("gg" + gg);
		
		float f[] = {1, 0f};
		bs = new BasicStroke(1, 
				BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER,
				10f,f,0f);
		
		start.addActionListener(this);
		exit.addActionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		// 마우스가 움직이기 시작하면 jTextField에서 텍스트를 가져와서 인티져로 바꾼담에 선 크기 결정
		// BasicStroke에 붓을 설정 해주는 거랑 비슷하게 이해!
		//lineThick = Integer.parseInt(thickness.getText());
		//float f[] = {lineThick, 0f};
		//bs = new BasicStroke(lineThick, 
		//		BasicStroke.CAP_ROUND,
		//		BasicStroke.JOIN_MITER,
		//		10f,f,0f);
		
		// 게임 시작 했을 때만 할 수 있음
		if(user.gameOn == true){
			
			gg.setStroke(bs);
			
			x1 = e.getX();
			y1 = e.getY();
			
			x2 = x1;
			y2 = y1;
			ox = x1;
			oy = y1;
			
			w = 0;
			h = 0;
			
		}else{
			JOptionPane.showMessageDialog(null, "대기하십시오.");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		w = x2-x1;
		h = y2-y1;
		gg.setPaintMode();
		switch(type){
		case 0 : g.drawLine(x1, y1, x2, y2);break;
		case 1 : g.drawOval(x1, y1, w, h);break;
		case 2 : g.drawRect(x1, y1, w, h);break;
		}
		
		// 이제 서버로 정보들 보내줘야지..! 보니까 x1, y1, x2, y2 보내면 될거같다..! 
		try {
			System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
			String msg = "300;"+ id + ";" + type + ";" +x1+";"+y1+";"+x2+";"+y2+";";
			pw.println(msg);
			pw.flush();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		ox = x2;
		oy = y2;
		
		x2 = e.getX();
		y2 = e.getY();
		
		switch(type){
		case 0 : line(); break;
		case 1 : oval(); break;
		case 2 : rect(); break;
		case 3 : pen(); break;
		}
	}
	
	public void line(){
		g.setXORMode(Color.white);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1, y1, ox, oy);
	}
	
	public void oval(){
		g.setXORMode(Color.white);
		w = x2-x1;
		h = y2-y1;
		g.drawOval(x1, y1, w, h);
		g.drawOval(x1, y1, (ox-x1), (oy-y1));
	}
	
	public void rect(){
		g.setXORMode(Color.white);
		w = x2-x1;
		h = y2-y1;
		g.drawRect(x1, y1, w, h);
		g.drawRect(x1, y1, (ox-x1), (oy-y1));
	}
	
	public void pen(){
		g.drawLine(x1, y1, x2, y2);
		try {
			System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
			String msg = "300;"+ id + ";" + type + ";" +x1+";"+y1+";"+x2+";"+y2+";";
			pw.println(msg);
			pw.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		x1 = x2;
		y1 = y2;
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if(ob == text){
			String msg = "10;" + id + ";" + text.getText();
			text.setText("");
			try {
				pw.println(msg);
				pw.flush();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}else if(ob == start){

			// 겜 시작 test
			
			// 게임 start 했다는거 보내줘야하는데 이거 우선 방장만 할 수 있게 만들어주자. 현재 유저 정보가 있을 것이다 방 만들기 눌렀을때만 유저 정보를 바꿔주자.
			// input을 쓰레드로 받는다고 가정 하면 메세지 안에 방장 정보 바꾸는 것이 들어가 있어야 할 것이다. 이거 우선 고민해보고 아직은 쓰레드 고민하지 말고 해보자.
			if(user.host == true){
				// JOptionPane.showMessageDialog(null, "방장이 맞습니다.");
				// 이거 대신에 방장이 게임 시작했다는 메세지를 보내면 서버에서 해당 방에 들어있는 유저들의 게임중 상태를
				// true로 다 바꿔줘야함..!
				
				if(user.gameOn == false){
					
					user.gameOn = true;
				
					String msg = "500;" + id + ";" + user.roomName + ";";
					try {
						pw.println(msg);
						pw.flush();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}	
			}
			else{					// 방장이 아니면
				JOptionPane.showMessageDialog(null, "방장이 아닙니다.");
			}
		}else if(ob == exit){
			
		}else if(ob == white){
	         g.setColor(Color.white);
	      }else if(ob == red){
	         g.setColor(Color.red);
	      }else if(ob == orange){
	         g.setColor(Color.orange);
	      }else if(ob == yellow){
	         g.setColor(Color.yellow);
	      }else if(ob == green){
	         g.setColor(Color.green);
	      }else if(ob == blue){
	         g.setColor(Color.blue);
	      }else if(ob == black){
	         g.setColor(Color.black);
	      }else if(ob == eraser){
	         g.setColor(new Color(234,234,234));
	      }
	}
	
	public static void main(String[] args) {
		GameRoom gr = new GameRoom();
		gr.init();
	}
	
}
