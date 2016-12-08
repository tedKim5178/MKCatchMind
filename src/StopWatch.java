import java.io.PrintWriter;
import java.util.ArrayList;

public class StopWatch extends Thread{
	
	ArrayList<UserInfo> userAl;
	ArrayList<RoomInfo> roomAl;
	
	String roomName;
	int time;
	public StopWatch(ArrayList<UserInfo> userAl, ArrayList<RoomInfo> roomAl, String roomName) {
		// TODO Auto-generated constructor stub
		this.userAl = userAl;
		this.roomName = roomName;
		this.roomAl = roomAl;
		time = 100;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg = "";
		
		
		
		while(true)
		{
			
			msg = "700" + ";" + time;
			
			// �濡 �ִ� �ֵ����� msg�� ��� �����ִµ�..
			// ������ �Ѹ��� ������ break �������.. 
			
			synchronized(roomAl){
				for(int i=0; i<roomAl.size(); i++){
					if(roomAl.get(i).roomName.equals(roomName)){
						// �ش� ���� �̸��� �Ȱ��� ���� ã������ �� �ش� ���� ã������
						if(roomAl.get(i).userAl.size() != 8);
					}
				}
			}
			
			synchronized(userAl){
				for(int i=0; i<userAl.size(); i++){
					if(userAl.get(i).roomName.equals(roomName)){
						userAl.get(i).gameOn = true;
						PrintWriter pw = userAl.get(i).pw;
						pw.println(msg);
						pw.flush();
					}
				}
			}
			try {
				time = time -1;
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(time==0){
				break;
			}
		}
	}
}
