import java.io.PrintWriter;

public class UserInfo {
	
	public String id;
	public String roomName;
	public int team;
	public PrintWriter pw;
	public boolean host;	// 방장인지 아닌지
	public boolean turn;	// 자기 차례인지 아닌지
	public boolean gameOn;	// 게임중인지 아닌지
	
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
