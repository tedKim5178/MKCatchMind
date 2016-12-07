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
		
		// �켱 ��ó������ Server�� �����ؼ� socket�� ���;���. �׷��� input, outputstream���� ���ᰡ��.
		UserInfo user;
		Socket socket;
		BufferedReader br;
		PrintWriter pw;
		String ID;
		GameRoom gr = null;
		try {
			
			// ��Ʈ��ũ ����.
			socket = new Socket("127.0.0.1", 10001);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// ���� ���̵� �ʿ���
			
			user = new UserInfo(pw);
			
						
			// ó�� �����ϸ� �α���/ ȸ������ �ϴ� ��ư�� �ִ� â�� ����ش�.
			LoginView lv = new LoginView(pw, user);
			// �̰� �����ϴ°� ������� ����. �� �ڿ� ���� �غ���.
			ClientThread ct = new ClientThread(br, lv);
			ct.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
