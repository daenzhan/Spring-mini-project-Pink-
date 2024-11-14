package Lab5.Project_mini_final_without_Security.repository;

import Lab5.Project_mini_final_without_Security.model.Task;
import Lab5.Project_mini_final_without_Security.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TaskRepository extends MongoRepository <Task,BigInteger>  {
    List<Task> findByUser(User user);
}