import java.util.ArrayList;
import java.util.TimerTask;

public class SchedulerJob extends TimerTask{
	
	ArrayList al = null;
	public SchedulerJob(ArrayList userAl) {
		// TODO Auto-generated constructor stub
		al = userAl;
	}
	public void run() {
		// turn�� �ٲ��ִ� ���� ���ָ��.  �׸��� �� client�� ��������ߵ�.
		// �׷� �� 3�� �׸��ϱ� 1�� 30�ʸ� �شٰ� ������ ���ʰ� �ٲ𶧴� ��ο��� �������༭
		// ��ΰ� �� �� �ִ� �˾�â�� ����ְ� 
		// �����γ��� ���̵� �����༭ �� �˾�â �ȿ� ����ָ� �ȴ�.
		// �׸��� turn�� �ٲ��ָ� �׸��� �׸� �� �ְ� �ȴ�. ������ turn�� ������� �ʾҴ�.
		// �� �׸��ǿ� �����ϱ� ���ؼ� turn�� gameOn ������ ��� 1�̾�� �ϴ� ���̴�.
		
	};
}
