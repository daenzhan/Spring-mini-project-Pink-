package Lab5.Project_mini_final_without_Security.service;

import Lab5.Project_mini_final_without_Security.model.Category;
import Lab5.Project_mini_final_without_Security.model.Task;
import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    // create
    public Task create_task (Task t){
        return taskRepository.save(t);
    }

    // show
//    public List<Task> get_list_taks_by_user_id (BigInteger user_id){
//        return taskRepository.(user_id);
//    }

//
//    // update
//    public Task update_tasks (Task t, BigInteger user_id){
//        Optional<Task> t_db = taskRepository.findById(t.getTask_id());
//        if(t_db.isEmpty()){
//            throw new RuntimeException("задача не найдена!");
//        }
//        else{
//            Task exist_t = t_db.get();
//            if(exist_t.getUser_id().equals(user_id)){
//                exist_t.setTitle(t.getTitle());
//                exist_t.setDescription(t.getDescription());
//                exist_t.setDue_date(t.getDue_date());
//                exist_t.setStatus(t.getStatus());
//                exist_t.setPriority(t.getPriority());
//                exist_t.setCategory(t.getCategory());
//                return taskRepository.save(exist_t);
//            }
//            return null;
//        }
//    }
//
//    // delete
//    public void delete_task (BigInteger task_id, BigInteger user_id){
//        Optional <Task> t_db = taskRepository.findById(task_id);
//        if(t_db.isPresent()){
//            Task exist_t = t_db.get();
//            if(exist_t.getUser_id().equals(user_id)){
//                taskRepository.delete(exist_t);
//            }
//        }
//    }

    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

}
