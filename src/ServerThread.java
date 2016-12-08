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
	
	// 타이머 관련
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
				// br로 통해서 해당 클라이언트가 보내는 거 모두 읽음.
				// 그럼 토크나이져로 나눠보자 
				StringTokenizer st = new StringTokenizer(line, ";");
				int num = Integer.parseInt(st.nextToken());
				switch(num){
				case 0 :
				{
					// 클라이언트로 보낼 메세지 만들자.
					// 0; + id + 메세지 내용
					this.msg = "0;" + st.nextToken() + ";" + st.nextToken();
					
					synchronized(userAl){
						// pw 들 다 가져오고
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					break;
				}
				// 앞에 10이 붙어서 오면 GameRoom 의 채팅이란 소리임.
				case 10 :
				{
					// 들어온 유저가 게임 중이면! 체크해준다.
					
					// 클라이언트로 보낼 메세지 만들자
					String id = st.nextToken();
					String sendMsg = st.nextToken();
					int flag = 0;
					
					System.out.println("Send Msg : " + sendMsg);
					if(user.gameOn == true)
					{
						System.out.println("처음 그냥 메세지보내면 나옴?");
						// 뭘 체크해줘? 방금 들어온 메세지를 !
						// 해당 유저가 속해 있는 방을 찾자.
						for(int i=0; i<roomAl.size(); i++){
							if(roomAl.get(i).roomName.equals(user.roomName)){
								System.out.println("RoomNAme : "+roomAl.get(i).roomName);
								System.out.println("Room의 ANswer : " + roomAl.get(i).answer);
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
					
					// 이제 보내는데 다보내는게 아니고 게임방에 있는 사람들한테만 보내준다.
					// 유저들중에 해당 게임방 이름이 user.roomName과 똑같은 애들한테만 보내준다.
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
				// 클라이언트가 처음 로그인 화면에서 아이디 쳐서 서버로 보냈을때
				// 방도 리스트도 추가해줘야함
				case 100 :
				{
					this.user.id = st.nextToken();
					
					synchronized(userAl){
						userAl.add(user);
					}
					
					// 이제 name들 다 얻었으니까 보내자 이거 클라이언트 전부들한테!
					// 여기서 중요한건 gameRoom에 들어가 있는 애들은 빼줘야 한다는것! 조건문 추가하자.
					this.msg = "100;"+user.id+";";
					for(int i=0; i<userAl.size(); i++){
						if(userAl.get(i).roomName.equals("")){
							this.msg = this.msg + (String)userAl.get(i).id + ";";		
						}
					}
					// ArrayList에 있는 id들 전부 다 보내주자.
					synchronized(userAl){
						// pw 들 다 가져오고
						for(int i=0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					
					// 지금 들어온애한테만 방 목록 보여주면됨.
					// 똑같이 100 보내주면 클라쪽에서 겹쳐질수도? 있으니까 혹시 모르니까 110으로
					this.msg = "110;" + user.id + ";";
					for(int i=0; i<roomAl.size(); i++){
						this.msg = this.msg + (String)roomAl.get(i).roomName + ";";
					}
					System.out.println("@@@방 제대로 가는지 테스트 ! : " + this.msg);
					synchronized(userAl){
						// pw 들 다 가져오고
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
				// 방 들어가는거 roomenter 메세지면
				case 200 : 
				{
					// 해당 id를 찾는다 그리고 roomName 바꿔준다.
					String id = st.nextToken();
					roomName = st.nextToken();
					user.roomName = roomName;
					
					// 얘 이제 방 들어가니까 roomList(roomAl) 안에 userlist에 넣어줘야함.
					// 우선 얘가 있는 방 찾자.
					for(int i=0; i<roomAl.size(); i++){
						if(user.roomName.equals(roomAl.get(i).roomName)){
							roomAl.get(i).userAl.add(user);
							roomAl.get(i).capacity = roomAl.get(i).capacity + 1;
						}
					}
					
					// 다시 보내줘야 해당 아이디에 대한 유저를 룸 리스트에서 빼 줄 수 있다.
					this.msg = "200;" + id + ";" + user.roomName + ";";
					
					synchronized(userAl){
						// pw 들 다 가져오고
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
					// x좌표, y좌표 다 받았으니까 그거 다른애들한테 줘볼까!
					
					
					
					
					// 그리고 만약 gameOn 이면 해당 유저한테만 보내주는거고 gameOn이 true면 모든 유저들에게 보내준다. if문필요					
					
					// 우선 주기전에 메세지를 만들자!
					String id = st.nextToken();
					String type = st.nextToken();
					String x1 = st.nextToken();
					String y1 = st.nextToken();
					String x2 = st.nextToken();
					String y2 = st.nextToken();
					
					this.msg = "300;" + type +";" + x1 + ";" + y1 + ";" + x2 + ";" + y2;
					// id 가지고 우선 roomName 찾자
					String tempRoomName = "";
					for(int i=0; i<userAl.size(); i++){
						if(userAl.get(i).id.equals(id)){
							tempRoomName = userAl.get(i).roomName;
						}
					}
					
					synchronized(userAl){
						// pw 들 다 가져오고
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
				// 방만들어졌다는 메세지 받았을때.
				// Roomlist에 하나 추가해주자
				case 400 :
				{
					
					// 누가 만들었는지 id에 아이디값 넣어주고 만약 문기라고 치면
					String id = st.nextToken();
					System.out.println(id + " 가  방만들기 호출했어.");
					
					//user.host = true;
					
					// 방 제목
					roomName = st.nextToken();
					
					// 보내기전에 server가 가지고 있는 room list에 이 방을 추가해줘야함.
					RoomInfo room = new RoomInfo();
					room.roomName = roomName;
					room.capacity = room.capacity + 1;
					room.userAl.add(user);
					roomAl.add(room);
					// 해당 클라이언트 찾고 
					// 근데 이부분 그냥 user.roomName으로도 될듯..!
					//for(int i=0; i<userAl.size(); i++){
						// 아이디가 같으면 거기에 roomName 넣어주자
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
				// 방장이 게임 시작 눌렀을때
				// 이때 타이머도 동작해야한다.
				case 500 :
				{
					// 게임시작 된거니까 해당 방에 있는 유저들한테 게임 시작 했다고 다 보내줘야됨.
					String id = st.nextToken();
					String roomName = user.roomName;
					// 해당 방에 있는 유저들의 gameOn을 다 true로 바꿔주고
					// 해당 방에 있는 유저들한테만 게임 시작됬다고 메세지를 보낸다. 그럼 이제 그림판 그릴 수 있게 해줘야함.
					// 보내줄때 문제도 보내주자.
					Question question = new Question();
					String answer = question.setQuestion();
					// 유저가 속해있는 방에 answer 바꿔주자.
					for(int i=0; i<roomAl.size(); i++){
						if(roomAl.get(i).roomName.equals(roomName)){
							roomAl.get(i).answer = answer;
							System.out.println("해당방의 answer" + roomAl.get(i).answer);
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
					
					// 이 메세지를 받은 애들은 게임을 그릴 수 있게 되는데 두명빼고 6명 한테는 문제를 보여줘야뎀.
					// 우선 이 코드와는 별개로 턴을 바꿔보자. 그리고 출력해보자.
					// 우선 해당 방에 있는 턴을 출력해보자.

					//sw = new StopWatch(userAl, roomAl, roomName);
					//sw.start();
					
					
					break;
				}
				case 900 :
				{
					// 아이디 받고 
					String id = st.nextToken();
					// 얘 나갔다는거 전부한테 알려주자.
					this.msg = "900;" + id + ";";
					// 해당 아이디에 해당하는놈 user 지워주자
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
				// GameRoom 에서 close 눌렀을때
				case 1000:
				{
					String id = st.nextToken();
					this.msg = "1000;" + id + ";";
					// 모든 애들한테 지금 얘 대기방으로 다시 왔다고 알려줘야지!
					// 문제는 여기서 이것만 알려줄게 아니고 방이 없어졌다면 없어진 방도 알려줘야함.
					synchronized(userAl){
						for(int i =0; i<userAl.size(); i++){
							PrintWriter pw = userAl.get(i).pw;
							pw.println(this.msg);
							pw.flush();
						}
					}
					// 애들한테 얘 대기방으로 온거 알려주고 난 후에 방검사를 해줘야한다.
					// 얘가 속한 방을 찾자.
					//user.roomName이 얘가 속한 방임..!
					// 우선 얘 방을 roomList에서 찾고!
					String temp = user.roomName;
					boolean delete = false; 	// 방 지우기가 발생했다 or 아니다.
					for(int i=0; i<roomAl.size(); i++){
						// 만약에 룸네임이 같으면 방을 찾은거다! i가 인덱스니까 i를 이용해서!
						if(user.roomName.equals(roomAl.get(i).roomName)){
							// capacity가 1이면 얘 빼주면서 방 없애주자!
							if(roomAl.get(i).capacity == 1){
								user.roomName = "";
								roomAl.remove(i);
								delete = true;
								// 없애주면서 
							}else{
								// roomList(roomAl) 에서 해당 유저 찾은다음에 이 유저 빼주고 capacity 하나 줄여줘야됨.
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
						// 지워진 방의 이름을 기억해두고 그 방의 String을 보내주자.
						// 누구한테? 클라이언트 모두에게! 
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
