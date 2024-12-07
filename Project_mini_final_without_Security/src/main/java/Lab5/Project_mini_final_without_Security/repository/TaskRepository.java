package Lab5.Project_mini_final_without_Security.repository;

import Lab5.Project_mini_final_without_Security.model.Task;
import Lab5.Project_mini_final_without_Security.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TaskRepository extends MongoRepository <Task,ObjectId>  {
    @Query("{'user_id': ?0}")
    List<Task> findByUserId(ObjectId user_id);

    @Query("{'user_id': ?0, 'title': {'$regex': ?1, '$options': 'i'}}")
    List<Task> findByUser_idAndTitleContaining(ObjectId userId, String title);

    @Query("{'user_id': ?0}")
    Page<Task> findByUserId_(ObjectId userId, Pageable pageable);

    @Query("{ 'user_id' : ?0, 'title' : { $regex: ?1, $options: 'i' }, 'status' : ?2 }")
    List<Task> findByUser_idAndTitleContainingAndStatus(ObjectId user_id, String title, String status);

    @Query("{ 'user_id' : ?0, 'status' : ?1 }")
    List<Task> findByUser_idAndStatus(ObjectId user_id, String status);


}