import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
public class Client extends JFrame{
	public static void main(String[] args) {
		
		// 우선 맨처음으로 Server와 연결해서 socket을 얻어와야함. 그래야 input, outputstream으로 연결가능.
		UserInfo user;
		Socket socket;
		BufferedReader br;
		PrintWriter pw;
		String ID;
		GameRoom gr = null;
		try {
			
			// 네트워크 정보.
			socket = new Socket("127.0.0.1", 10001);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// 나의 아이디 필요함
			
			user = new UserInfo(pw);
			
						
			// 처음 시작하면 로그인/ 회원가입 하는 버튼이 있는 창을 띄워준다.
			LoginView lv = new LoginView(pw, user);
			// 이거 리드하는거 쓰레드로 받자. 그 뒤에 정리 해보자.
			ClientThread ct = new ClientThread(br, lv);
			ct.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("???");
	}
}
