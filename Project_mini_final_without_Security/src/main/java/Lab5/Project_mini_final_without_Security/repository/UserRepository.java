package Lab5.Project_mini_final_without_Security.repository;

import Lab5.Project_mini_final_without_Security.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends MongoRepository <User, BigInteger>  {
    Optional<User> findByUsername(String username);

}