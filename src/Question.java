public class Question {
   String[] answer = new String [50];
   
   public Question() {
      answer[1]    = "경찰";
      answer[2]    = "대통령";
      answer[3]    = "대학교";
      answer[4]    = "박지성";
      answer[5]    = "류현진";
      answer[6]    = "스티브잡스";
      answer[7]    = "미국";
      answer[8]    = "프랑스";
      answer[9]    = "김치찌개";
      answer[10]    = "조선소";
      answer[11]    = "중고생";
      answer[12]    = "코흘리개";
      answer[13]    = "외양간";
      answer[14]    = "유관순";
      answer[15]    = "원빈";
      answer[16]    = "기독교";
      answer[17]    = "군대";
      answer[18]    = "찹쌀떡";
      answer[19]    = "늑대";
      answer[20]    = "소녀시대";
      answer[21]    = "솜사탕";
      answer[22]    = "소개팅";
      answer[23]    = "여자친구";
      answer[24]    = "싸이";
      answer[25]    = "빵셔틀";
      answer[26]    = "공무원";
      answer[27]    = "놀부";
      answer[28]    = "신동엽";
      answer[29]    = "해운대";
      answer[30]    = "밥도둑";      
   }
   
   public String setQuestion() {
      int stage = (int)(Math.random()*30+1);
      String question = answer[stage];
      System.out.println("@@@@" + question);
      return question;
   }
   
}