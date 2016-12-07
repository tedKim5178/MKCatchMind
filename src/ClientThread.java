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
			
			// 토크나이져 생성
			StringTokenizer st = new StringTokenizer(line, ";");
			int num = Integer.parseInt(st.nextToken());
			
			// 토큰에 의해서 switch 문 실행
			switch(num){
			case 0 : 
			{
				// 0은 채팅이다. 다음 토크나이져에는 해당 아이디가 들어온다
				String senderId = st.nextToken();
				String text = st.nextToken();
				lv.room.area.append(senderId + " : " + text + "\n");
				lv.room.area.setAutoscrolls(true);
				break;
			}
			case 10 :
			{
				// 10은 게임방 속에서 채팅 하는 것이다. 0은 대기방이므로 분류가 필요하다.
				String senderId = st.nextToken();
				String text = st.nextToken();
				lv.room.gameRoom.area.append(senderId + " : " + text + "\n");
				lv.room.gameRoom.area.setAutoscrolls(true);
				break;
			}
			case 100 : 
			{
				// 로그인 버튼 눌렀을때, 
				if(this.user.id.equals(st.nextToken())){
					while(st.hasMoreTokens()){
						lv.room.userList.add(st.nextToken());
					}
				}else{
					// 내가 들어온거면, (들어온사람이 자기 자신이면)
					lv.room.userList.removeAll();
					while(st.hasMoreTokens()){
						lv.room.userList.add(st.nextToken());
					}
				}
				break;
			}
			case 110 :
			{
				// 방들을 추가해주어야 하니까.
				String senderId = st.nextToken();
				// 뒤에는 이제 다 방 제목이다.
				while(st.hasMoreTokens()){
					lv.room.roomList.add(st.nextToken());
				}
				break;
			}
			case 200 :
			{
				// 리스트에서 해당 id 빼준다.
				String id = st.nextToken();
				String roomName = st.nextToken();
				
				// lv.user랑 한번 테스트 해봐야한다. 모두 같은 값을 참조하는지..
				user.roomName = roomName;
				lv.room.userList.remove(id);		
				
				//
				break;
			}
			// 페인트에 관련된거 받았을때
			case 300 :
			{
				// 그리기 했을때,
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
			// make 룸 했을때 
			case 400 :
			{
				// 방 만든 사람
				String id = st.nextToken();
				String roomName = st.nextToken();
				user.roomName = roomName;
				lv.room.userList.remove(id);
				lv.room.roomList.add(roomName);
				// 방장이 들어간거니까 방리스트에 추가.
				
				break;
			}
			case 500 :
			{
				String id = st.nextToken();
				String roomName = st.nextToken();
				System.out.println("User.roomName은?? : " + user.roomName);
				System.out.println("지금 들어온 roomName은? : " + roomName);
				if(user.roomName.equals(roomName)){
					user.gameOn = true;
				}
				break;
			}
			case 900 :
			{
				// 나가는 메세지,
				// 나가는 사람
				String id = st.nextToken();
				lv.room.userList.remove(id);
				break;
			}
			case 1000 :
			{
				// 한명이 다시 대기실로 들어와진다.
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
