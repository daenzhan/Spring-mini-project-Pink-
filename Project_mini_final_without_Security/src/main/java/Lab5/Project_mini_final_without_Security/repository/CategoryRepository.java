package Lab5.Project_mini_final_without_Security.repository;

import Lab5.Project_mini_final_without_Security.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
}