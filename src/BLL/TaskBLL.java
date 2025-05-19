package BLL;

import DAL.TaskDAL;
import DTO.TaskDTO;

import java.util.ArrayList;

public class TaskBLL {
    private TaskDAL taskDAL;

    public TaskBLL() {
        taskDAL = new TaskDAL();
    }

    public ArrayList<TaskDTO> get(String userId){
        return taskDAL.get(userId);
    }

    public ArrayList<TaskDTO> getAll(){
        return taskDAL.getAll();
    }

    public Boolean delete(String userId, String subjectId){
        return taskDAL.delete(userId, subjectId);
    }

    public Boolean add(String userId, String subjectId){
        return taskDAL.add(userId, subjectId);
    }
}
