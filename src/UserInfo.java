import java.io.PrintWriter;

public class UserInfo {
	
	public String id;
	public String roomName;
	public int team;
	public PrintWriter pw;
	public boolean host;	// �������� �ƴ���
	public boolean turn;	// �ڱ� �������� �ƴ���
	public boolean gameOn;	// ���������� �ƴ���
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
		this.id = "";
		roomName = "";
		team = 0;
		pw = null;
		host = false;
		turn = false;
	}
	
	public UserInfo(PrintWriter pw) {
		// TODO Auto-generated constructor stub
		this.id = "";
		roomName = "";
		team = 0;
		this.pw = pw;
		host = false;
		turn = false;
	}
	
}
