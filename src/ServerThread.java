import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class ServerThread extends Thread{
	private Socket socket = null;
	
	private ArrayList<UserInfo> userAl = null;
	private ArrayList<RoomInfo> roomAl = null;
	
	private BufferedReader br = null;
	private PrintWriter pw = null;
	String msg = "";
	//String id = null;
	UserInfo user = null;
	String roomName;
	
	// Ÿ�̸� ����
	StopWatch sw = null;
	
	public ServerThread(Socket socket, ArrayList<UserInfo> userAl, ArrayList<RoomInfo> roomAl) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.userAl = userAl;
		this.roomAl = roomAl;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		user = new UserInfo(pw);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line = null;
		try {
			while((line = br.readLine()) != null){
				// br�� ���ؼ� �ش� Ŭ���̾�Ʈ�� ������ �� ��� ����.
				// �׷� ��ũ�������� �������� 
				StringTokenizer st = new StringTokenizer(line, ";");
				int num = Integer.parseInt(st.nextToken());
				switch(num){
				case 0 :
				{
					// Ŭ���̾�Ʈ�� ���� �޼��� ������.
					// 0; + id + �޼��� ����
					this.msg = "0;" + st.nextToken() + ";" + st.nextToken();
					
					synchronized(userAl){
						// pw �� �� ��������
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					break;
				}
				// �տ� 10�� �پ ���� GameRoom �� ä���̶� �Ҹ���.
				case 10 :
				{
					// ���� ������ ���� ���̸�! üũ���ش�.
					
					// Ŭ���̾�Ʈ�� ���� �޼��� ������
					String id = st.nextToken();
					String sendMsg = st.nextToken();
					int flag = 0;
					
					System.out.println("Send Msg : " + sendMsg);
					if(user.gameOn == true)
					{
						System.out.println("ó�� �׳� �޼��������� ����?");
						// �� üũ����? ��� ���� �޼����� !
						// �ش� ������ ���� �ִ� ���� ã��.
						for(int i=0; i<roomAl.size(); i++){
							if(roomAl.get(i).roomName.equals(user.roomName)){
								System.out.println("RoomNAme : "+roomAl.get(i).roomName);
								System.out.println("Room�� ANswer : " + roomAl.get(i).answer);
								if(roomAl.get(i).answer.equals(sendMsg)){
									System.out.println("@#!#!@#!@#!@#!#");
									flag = 1;
									//sw.time = 0;
									
								}
							}
						}
						for(int i=0; i<userAl.size(); i++){
							if(userAl.get(i).roomName.equals(user.roomName)){
								user.gameOn = false;
							}
						}
					}
					this.msg = "10;" + id + ";" + sendMsg + ";" + flag;
					
					// ���� �����µ� �ٺ����°� �ƴϰ� ���ӹ濡 �ִ� ��������׸� �����ش�.
					// �������߿� �ش� ���ӹ� �̸��� user.roomName�� �Ȱ��� �ֵ����׸� �����ش�.
					synchronized(userAl){
						
						for(int i=0; i<userAl.size(); i++){
							if(userAl.get(i).roomName.equals(user.roomName)){
								PrintWriter pw = userAl.get(i).pw;
								pw.println(this.msg);
								pw.flush();
							}
						}
					}
					break;
				}
				// Ŭ���̾�Ʈ�� ó�� �α��� ȭ�鿡�� ���̵� �ļ� ������ ��������
				// �浵 ����Ʈ�� �߰��������
				case 100 :
				{
					this.user.id = st.nextToken();
					
					synchronized(userAl){
						userAl.add(user);
					}
					
					// ���� name�� �� ������ϱ� ������ �̰� Ŭ���̾�Ʈ ���ε�����!
					// ���⼭ �߿��Ѱ� gameRoom�� �� �ִ� �ֵ��� ����� �Ѵٴ°�! ���ǹ� �߰�����.
					this.msg = "100;"+user.id+";";
					for(int i=0; i<userAl.size(); i++){
						if(userAl.get(i).roomName.equals("")){
							this.msg = this.msg + (String)userAl.get(i).id + ";";		
						}
					}
					// ArrayList�� �ִ� id�� ���� �� ��������.
					synchronized(userAl){
						// pw �� �� ��������
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					
					// ���� ���¾����׸� �� ��� �����ָ��.
					// �Ȱ��� 100 �����ָ� Ŭ���ʿ��� ����������? �����ϱ� Ȥ�� �𸣴ϱ� 110����
					this.msg = "110;" + user.id + ";";
					for(int i=0; i<roomAl.size(); i++){
						this.msg = this.msg + (String)roomAl.get(i).roomName + ";";
					}
					System.out.println("@@@�� ����� ������ �׽�Ʈ ! : " + this.msg);
					synchronized(userAl){
						// pw �� �� ��������
						for(int i=0; i<userAl.size(); i++){
							if(user.id.equals(userAl.get(i).id)){
								PrintWriter pw = userAl.get(i).pw;
								pw.println(this.msg);
								pw.flush();
							}
						}
					}
					
					break;
				}
				// �� ���°� roomenter �޼�����
				case 200 : 
				{
					// �ش� id�� ã�´� �׸��� roomName �ٲ��ش�.
					String id = st.nextToken();
					roomName = st.nextToken();
					user.roomName = roomName;
					
					// �� ���� �� ���ϱ� roomList(roomAl) �ȿ� userlist�� �־������.
					// �켱 �갡 �ִ� �� ã��.
					for(int i=0; i<roomAl.size(); i++){
						if(user.roomName.equals(roomAl.get(i).roomName)){
							roomAl.get(i).userAl.add(user);
							roomAl.get(i).capacity = roomAl.get(i).capacity + 1;
						}
					}
					
					// �ٽ� ������� �ش� ���̵� ���� ������ �� ����Ʈ���� �� �� �� �ִ�.
					this.msg = "200;" + id + ";" + user.roomName + ";";
					
					synchronized(userAl){
						// pw �� �� ��������
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					break;
				}
				case 300:
				{
					// x��ǥ, y��ǥ �� �޾����ϱ� �װ� �ٸ��ֵ����� �ຼ��!
					
					
					
					
					// �׸��� ���� gameOn �̸� �ش� �������׸� �����ִ°Ű� gameOn�� true�� ��� �����鿡�� �����ش�. if���ʿ�					
					
					// �켱 �ֱ����� �޼����� ������!
					String id = st.nextToken();
					String type = st.nextToken();
					String x1 = st.nextToken();
					String y1 = st.nextToken();
					String x2 = st.nextToken();
					String y2 = st.nextToken();
					
					this.msg = "300;" + type +";" + x1 + ";" + y1 + ";" + x2 + ";" + y2;
					// id ������ �켱 roomName ã��
					String tempRoomName = "";
					for(int i=0; i<userAl.size(); i++){
						if(userAl.get(i).id.equals(id)){
							tempRoomName = userAl.get(i).roomName;
						}
					}
					
					synchronized(userAl){
						// pw �� �� ��������
						for(int i=0; i<userAl.size(); i++){
							
							if(userAl.get(i).roomName.equals(tempRoomName)){
								PrintWriter pw = userAl.get(i).pw;
								pw.println(this.msg);
								pw.flush();
							}
						}
					}
					break;
				}
				// �游������ٴ� �޼��� �޾�����.
				// Roomlist�� �ϳ� �߰�������
				case 400 :
				{
					
					// ���� ��������� id�� ���̵� �־��ְ� ���� ������ ġ��
					String id = st.nextToken();
					System.out.println(id + " ��  �游��� ȣ���߾�.");
					
					//user.host = true;
					
					// �� ����
					roomName = st.nextToken();
					
					// ���������� server�� ������ �ִ� room list�� �� ���� �߰��������.
					RoomInfo room = new RoomInfo();
					room.roomName = roomName;
					room.capacity = room.capacity + 1;
					room.userAl.add(user);
					roomAl.add(room);
					// �ش� Ŭ���̾�Ʈ ã�� 
					// �ٵ� �̺κ� �׳� user.roomName���ε� �ɵ�..!
					//for(int i=0; i<userAl.size(); i++){
						// ���̵� ������ �ű⿡ roomName �־�����
					//	if(userAl.get(i).id.equals(id)){
					user.roomName = roomName;
					//	}
					//}
					
					this.msg = "400;" + id + ";" + roomName +";";
					System.out.println(msg);
					synchronized(userAl){
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					break;
				}
				// ������ ���� ���� ��������
				// �̶� Ÿ�̸ӵ� �����ؾ��Ѵ�.
				case 500 :
				{
					// ���ӽ��� �ȰŴϱ� �ش� �濡 �ִ� ���������� ���� ���� �ߴٰ� �� ������ߵ�.
					String id = st.nextToken();
					String roomName = user.roomName;
					// �ش� �濡 �ִ� �������� gameOn�� �� true�� �ٲ��ְ�
					// �ش� �濡 �ִ� ���������׸� ���� ���ۉ�ٰ� �޼����� ������. �׷� ���� �׸��� �׸� �� �ְ� �������.
					// �����ٶ� ������ ��������.
					Question question = new Question();
					String answer = question.setQuestion();
					// ������ �����ִ� �濡 answer �ٲ�����.
					for(int i=0; i<roomAl.size(); i++){
						if(roomAl.get(i).roomName.equals(roomName)){
							roomAl.get(i).answer = answer;
							System.out.println("�ش���� answer" + roomAl.get(i).answer);
						}
					}
					
					this.msg = "500;" + id + ";" + roomName +";" + answer;
					System.out.println(msg);
					synchronized(userAl){
						for(int i=0; i<userAl.size(); i++){
							if(userAl.get(i).roomName.equals(roomName)){
								userAl.get(i).gameOn = true;
								PrintWriter pw = userAl.get(i).pw;
								pw.println(this.msg);
								pw.flush();
							}
						}
					}
					
					// �� �޼����� ���� �ֵ��� ������ �׸� �� �ְ� �Ǵµ� �θ��� 6�� ���״� ������ ������ߵ�.
					// �켱 �� �ڵ�ʹ� ������ ���� �ٲ㺸��. �׸��� ����غ���.
					// �켱 �ش� �濡 �ִ� ���� ����غ���.

					//sw = new StopWatch(userAl, roomAl, roomName);
					//sw.start();
					
					
					break;
				}
				case 900 :
				{
					// ���̵� �ް� 
					String id = st.nextToken();
					// �� �����ٴ°� �������� �˷�����.
					this.msg = "900;" + id + ";";
					// �ش� ���̵� �ش��ϴ³� user ��������
					int index = 0;
					for(int i=0; i<userAl.size(); i++){
						if(userAl.get(i).id.equals(id)){
							index = i;
							break;
						}
					}
					userAl.remove(index);
					synchronized(userAl){
						for(int i =0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
				}
				// GameRoom ���� close ��������
				case 1000:
				{
					String id = st.nextToken();
					this.msg = "1000;" + id + ";";
					// ��� �ֵ����� ���� �� �������� �ٽ� �Դٰ� �˷������!
					// ������ ���⼭ �̰͸� �˷��ٰ� �ƴϰ� ���� �������ٸ� ������ �浵 �˷������.
					synchronized(userAl){
						for(int i =0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					// �ֵ����� �� �������� �°� �˷��ְ� �� �Ŀ� ��˻縦 ������Ѵ�.
					// �갡 ���� ���� ã��.
					//user.roomName�� �갡 ���� ����..!
					// �켱 �� ���� roomList���� ã��!
					String temp = user.roomName;
					boolean delete = false; 	// �� ����Ⱑ �߻��ߴ� or �ƴϴ�.
					for(int i=0; i<roomAl.size(); i++){
						// ���࿡ ������� ������ ���� ã���Ŵ�! i�� �ε����ϱ� i�� �̿��ؼ�!
						if(user.roomName.equals(roomAl.get(i).roomName)){
							// capacity�� 1�̸� �� ���ָ鼭 �� ��������!
							if(roomAl.get(i).capacity == 1){
								user.roomName = "";
								roomAl.remove(i);
								delete = true;
								// �����ָ鼭 
							}else{
								// roomList(roomAl) ���� �ش� ���� ã�������� �� ���� ���ְ� capacity �ϳ� �ٿ���ߵ�.
								user.roomName = "";
								for(int j=0; j < roomAl.get(i).userAl.size(); j++){
									if(user.id.equals(roomAl.get(i).userAl.get(j).id)){
										roomAl.get(i).userAl.remove(j);
									}
								}
								roomAl.get(i).capacity = roomAl.get(i).capacity - 1; 
							}
						}
					}
					
					if(delete == true){
						// ������ ���� �̸��� ����صΰ� �� ���� String�� ��������.
						// ��������? Ŭ���̾�Ʈ ��ο���! 
						this.msg = "1010;" + temp + ";";
						synchronized(userAl){
							for(int i =0; i<userAl.size(); i++){
								PrintWriter pw = userAl.get(i).pw;
								pw.println(this.msg);
								pw.flush();
							}
						}
						delete = false;
					}
					
				}
				}
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		
	}
}
