import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	public static void main(String[] args) throws IOException {
		
		// ���� ����
		ServerSocket server = new ServerSocket(10001);
		
		ArrayList<UserInfo> al = new ArrayList();
		ArrayList<RoomInfo> roomAl = new ArrayList();
		
		while(true){
			Socket socket = server.accept();
			// ��ó���� �������� �����°� id����  �켱 ���۵� ����
			//ServerThread st = new ServerThread(socket, hm);
			ServerThread st = new ServerThread(socket, al, roomAl);
			st.start();			
		}
	}
}
