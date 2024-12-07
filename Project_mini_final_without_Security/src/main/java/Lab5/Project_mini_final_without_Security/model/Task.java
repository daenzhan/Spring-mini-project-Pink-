package Lab5.Project_mini_final_without_Security.model;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
@Document(collection = "tasks")
public class Task {
    @Id
    private ObjectId task_id;

    private String title;
    private String description;
    private LocalDateTime due_date;
    private String status;
    private String priority;
    private String formattedDueDate;

    private ObjectId user_id;  // связь с пользователем

    @DBRef
    private User user;

    @DBRef
    private Category category;

    private ObjectId category_id;
    private String category_name;

    public Task() {}

    public Task(ObjectId task_id, String title, String description, LocalDateTime due_date,
                String status, String priority, ObjectId user_id, User user, ObjectId category_id,
                Category category, String category_name) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.status = status;
        this.priority = priority;
        this.user_id = user_id;
        this.user = user;
        this.category_id = category_id;
        this.category = category;
        this.category_name = category_name;
    }

    public ObjectId getTask_id() {
        return task_id;
    }

    public void setTask_id(ObjectId task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ObjectId getUser_id() {
        return user_id;
    }

    public void setUser_id(ObjectId user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ObjectId getCategory_id() {
        return category_id;
    }

    public void setCategory_id(ObjectId category_id) {
        this.category_id = category_id;
    }

    public String getFormattedDueDate() {
        return formattedDueDate;
    }

    public void setFormattedDueDate(String formattedDueDate) {
        this.formattedDueDate = formattedDueDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
