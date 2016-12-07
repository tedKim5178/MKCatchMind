import java.util.ArrayList;

public class RoomInfo {
	
	// 최대 몇명인지.
	int capacity;
	String roomName;
	boolean gameOn;
	ArrayList<UserInfo> userAl;
	
	public RoomInfo() {
		// TODO Auto-generated constructor stub
		capacity = 0;
		gameOn = false;
		roomName = "";
		userAl = new ArrayList();
		
	}
}
