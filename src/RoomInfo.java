import java.util.ArrayList;

public class RoomInfo {
	
	// 최대 몇명인지.
	int capacity;
	String roomName;
	boolean gameOn;
	String answer;
	ArrayList<UserInfo> userAl;
	
	public RoomInfo() {
		// TODO Auto-generated constructor stub
		capacity = 0;
		gameOn = false;
		roomName = "";
		answer = "문기";
		userAl = new ArrayList();
		
	}
}
