import BLL.QuestionBLL;
import DTO.AnswerDTO;
import DTO.QuestionDTO;
import MICS.Enums;
public class main {
    public static void main(String[] args) {
        QuestionBLL.updateQuestion(new QuestionDTO(2,"841107C1", "Sys", Enums.DifficultValue.DE, "testing 2", new AnswerDTO("D", "C", "B", "A", 1)));
        //QuestionBLL.deleteQuestion(5);
        //QuestionBLL.addQuestion(new QuestionDTO("841107C1", "Sys", Enums.DifficultValue.DE, "testing", new AnswerDTO("A", "B", "C", "D", 1)));
    }
}
