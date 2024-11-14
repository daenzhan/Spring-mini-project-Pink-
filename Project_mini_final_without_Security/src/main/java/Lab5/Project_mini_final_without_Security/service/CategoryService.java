package Lab5.Project_mini_final_without_Security.service;

import Lab5.Project_mini_final_without_Security.model.Category;
import Lab5.Project_mini_final_without_Security.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // create
    public Category create (Category c){
        return categoryRepository.save(c);
    }

    // edit
    public Category edit (Category c){
        Optional<Category> c_db = categoryRepository.findById(c.getCategory_id());
        if(c_db.isPresent()){
            Category exist_c = c_db.get();
            exist_c.setCategory_id(c.getCategory_id());
            exist_c.setName(c.getName());
            exist_c.setDescription(c.getDescription());
            exist_c.setTasks(c.getTasks());
            return categoryRepository.save(exist_c);
        }
        return null;
    }

    // show
    public List<Category> show (){
        return categoryRepository.findAll();
    }

    //delete
    public void delete (BigInteger category_id){
        Optional<Category> c_db = categoryRepository.findById(category_id);
        if (c_db.isPresent()){
            Category exist_c = c_db.get();
            categoryRepository.delete(exist_c);
        }
    }
}
