public class Question {
   String[] answer = new String [50];
   
   public Question() {
      answer[1]    = "����";
      answer[2]    = "�����";
      answer[3]    = "���б�";
      answer[4]    = "������";
      answer[5]    = "������";
      answer[6]    = "��Ƽ���⽺";
      answer[7]    = "�̱�";
      answer[8]    = "������";
      answer[9]    = "��ġ�";
      answer[10]    = "������";
      answer[11]    = "�߰��";
      answer[12]    = "���긮��";
      answer[13]    = "�ܾ簣";
      answer[14]    = "������";
      answer[15]    = "����";
      answer[16]    = "�⵶��";
      answer[17]    = "����";
      answer[18]    = "���Ҷ�";
      answer[19]    = "����";
      answer[20]    = "�ҳ�ô�";
      answer[21]    = "�ػ���";
      answer[22]    = "�Ұ���";
      answer[23]    = "����ģ��";
      answer[24]    = "����";
      answer[25]    = "����Ʋ";
      answer[26]    = "������";
      answer[27]    = "���";
      answer[28]    = "�ŵ���";
      answer[29]    = "�ؿ��";
      answer[30]    = "�䵵��";      
   }
   
   public String setQuestion() {
      int stage = (int)(Math.random()*30+1);
      String question = answer[stage];
      System.out.println("@@@@" + question);
      return question;
   }
   
}