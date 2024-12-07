package Lab5.Project_mini_final_without_Security.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Document(collection = "categories")
public class Category {
    @Id
    private ObjectId category_id;

    private String name;
    private String description;

    @DBRef
    private List<ObjectId> tasks;


    public Category (){}

    // задачи я не включила в конструктор - чтобы сначала создать категорию, а уж потом добавлять

    public Category(ObjectId category_id, String name, String description, List<ObjectId> tasks) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public ObjectId getCategory_id() {
        return category_id;
    }

    public void setCategory_id(ObjectId category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ObjectId> getTasks() {
        return tasks;
    }

    public void setTasks(List<ObjectId> tasks) {
        this.tasks = tasks;
    }
}
