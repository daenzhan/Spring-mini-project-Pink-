package Lab5.Project_mini_final_without_Security.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "categories")
public class Category {
    @Id
    private BigInteger category_id;

    private String name;
    private String description;

    @DBRef
    private List<BigInteger> tasks;


    public Category (){}

    // задачи я не включила в конструктор - чтобы сначала создать категорию, а уж потом добавлять
    public Category(BigInteger category_id, String name, String description) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
    }

    public BigInteger getCategory_id() {
        return category_id;
    }

    public void setCategory_id(BigInteger category_id) {
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

    public List<BigInteger> getTasks() {
        return tasks;
    }

    public void setTasks(List<BigInteger> tasks) {
        this.tasks = tasks;
    }


}
