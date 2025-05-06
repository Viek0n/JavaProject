
import BLL.ExamBLL;
import DAL.ExamStructDAL;
import DTO.ExamStructDTO;
import GUI.TakeExam;






public class main {
    public static void main(String[] args) {
        /*ExamStructDTO examStruct = new ExamStructDTO();
        ExamStructDAL examStructDAL = new ExamStructDAL();
        examStruct = examStructDAL.get("CT1");
        System.out.print(examStruct);*/
        
        /*QuestionDAL questionDAL = new QuestionDAL();
        QuestionDTO quest = questionDAL.getByID("CH1");
        System.out.print(quest);*/

        /*QuestionBLL questionBLL = new QuestionBLL();
        QuestionDTO quest = questionBLL.get("CH2");
        quest.getAns().get(0).setID(Enums.AnswerID.A);
        quest.getAns().get(1).setID(Enums.AnswerID.B);
        quest.getAns().get(2).setID(Enums.AnswerID.C);
        quest.getAns().get(3).setID(Enums.AnswerID.D);
        System.out.print(quest);
        questionBLL.update(quest);*/

        //MainFrame mainFrame = new MainFrame();

        ExamStructDTO x = new ExamStructDAL().get("CT1");
        TakeExam user = new TakeExam(new ExamBLL().gen("Sys", x));
    }
}