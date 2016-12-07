import java.io.BufferedReader;
import java.util.StringTokenizer;

public class ClientThread extends Thread{
	UserInfo user;
	BufferedReader br;
	LoginView lv;
	GameRoom gr = null;
	
	public ClientThread(BufferedReader br, LoginView lv) {
		// TODO Auto-generated constructor stub
		this.br = br;
		this.lv = lv;
		this.user = lv.user;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line = null;
		String[] token = null;
			
		while(true){
			try {
				line = br.readLine();
				System.out.println(line);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			// ��ũ������ ����
			StringTokenizer st = new StringTokenizer(line, ";");
			int num = Integer.parseInt(st.nextToken());
			
			// ��ū�� ���ؼ� switch �� ����
			switch(num){
			case 0 : 
			{
				// 0�� ä���̴�. ���� ��ũ���������� �ش� ���̵� ���´�
				String senderId = st.nextToken();
				String text = st.nextToken();
				lv.room.area.append(senderId + " : " + text + "\n");
				lv.room.area.setAutoscrolls(true);
				break;
			}
			case 10 :
			{
				// 10�� ���ӹ� �ӿ��� ä�� �ϴ� ���̴�. 0�� �����̹Ƿ� �з��� �ʿ��ϴ�.
				String senderId = st.nextToken();
				String text = st.nextToken();
				lv.room.gameRoom.area.append(senderId + " : " + text + "\n");
				lv.room.gameRoom.area.setAutoscrolls(true);
				break;
			}
			case 100 : 
			{
				// �α��� ��ư ��������, 
				if(this.user.id.equals(st.nextToken())){
					while(st.hasMoreTokens()){
						lv.room.userList.add(st.nextToken());
					}
				}else{
					// ���� ���°Ÿ�, (���»���� �ڱ� �ڽ��̸�)
					lv.room.userList.removeAll();
					while(st.hasMoreTokens()){
						lv.room.userList.add(st.nextToken());
					}
				}
				break;
			}
			case 110 :
			{
				// ����� �߰����־�� �ϴϱ�.
				String senderId = st.nextToken();
				// �ڿ��� ���� �� �� �����̴�.
				while(st.hasMoreTokens()){
					lv.room.roomList.add(st.nextToken());
				}
				break;
			}
			case 200 :
			{
				// ����Ʈ���� �ش� id ���ش�.
				String id = st.nextToken();
				String roomName = st.nextToken();
				
				// lv.user�� �ѹ� �׽�Ʈ �غ����Ѵ�. ��� ���� ���� �����ϴ���..
				user.roomName = roomName;
				lv.room.userList.remove(id);		
				
				//
				break;
			}
			// ����Ʈ�� ���õȰ� �޾�����
			case 300 :
			{
				// �׸��� ������,
				int type = Integer.parseInt(st.nextToken());
				int x1 = Integer.parseInt(st.nextToken());
				int y1 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				int w = x2 - x1;
				int h = y2 - y1;
				lv.room.gameRoom.gg.setStroke(lv.room.gameRoom.bs);
				lv.room.gameRoom.gg.setPaintMode();
				
				switch(type){
				case 0 : lv.room.gameRoom.g.drawLine(x1, y1, x2, y2);break;
				case 1 : lv.room.gameRoom.g.drawOval(x1, y1, w, h);break;
				case 2 : lv.room.gameRoom.g.drawRect(x1, y1, w, h);break;
				case 3 : lv.room.gameRoom.g.drawLine(x1, y1, x2, y2);break;
				}
				break;
			}
			// make �� ������ 
			case 400 :
			{
				// �� ���� ���
				String id = st.nextToken();
				String roomName = st.nextToken();
				user.roomName = roomName;
				lv.room.userList.remove(id);
				lv.room.roomList.add(roomName);
				// ������ ���Ŵϱ� �渮��Ʈ�� �߰�.
				
				break;
			}
			case 500 :
			{
				String id = st.nextToken();
				String roomName = st.nextToken();
				System.out.println("User.roomName��?? : " + user.roomName);
				System.out.println("���� ���� roomName��? : " + roomName);
				if(user.roomName.equals(roomName)){
					user.gameOn = true;
				}
				break;
			}
			case 900 :
			{
				// ������ �޼���,
				// ������ ���
				String id = st.nextToken();
				lv.room.userList.remove(id);
				break;
			}
			case 1000 :
			{
				// �Ѹ��� �ٽ� ���Ƿ� ��������.
				String id = st.nextToken();
				lv.room.userList.add(id);
				break;
			}
			case 1010:
			{
				String roomName = st.nextToken();
				lv.room.roomList.remove(roomName);
				break;
			}
			}// end of switch
		}
	}
}
