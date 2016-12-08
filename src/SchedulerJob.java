import java.util.ArrayList;
import java.util.TimerTask;

public class SchedulerJob extends TimerTask{
	
	ArrayList al = null;
	public SchedulerJob(ArrayList userAl) {
		// TODO Auto-generated constructor stub
		al = userAl;
	}
	public void run() {
		// turn을 바꿔주는 것을 해주면됨.  그리고 각 client로 전달해줘야됨.
		// 그럼 총 3명씩 그리니까 1분 30초를 준다고 했을때 차례가 바뀔때는 모두에게 전달해줘서
		// 모두가 볼 수 있는 팝업창을 띄워주고 
		// 차례인놈의 아이디를 보내줘서 그 팝업창 안에 띄워주면 된다.
		// 그리고 turn을 바꿔주면 그림을 그릴 수 있게 된다. 지금은 turn이 고려되지 않았다.
		// 즉 그림판에 접근하기 위해선 turn과 gameOn 변수가 모두 1이어야 하는 것이다.
		
	};
}
