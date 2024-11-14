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
    private BigInteger task_id;

    private String title;
    private String description;
    private LocalDateTime due_date;
    private String status;
    private String priority;

    @DBRef(lazy = false)
    private User user;  // связь с пользователем

    @DBRef(lazy = false)
    private Category category;

    public Task() {}

    public Task(BigInteger task_id, String title, String description,
                LocalDateTime due_date, String status, String priority,
                User user, Category category) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.status = status;
        this.priority = priority;
        this.user = user;
        this.category = category;
    }

    // геттеры и сеттеры
    public BigInteger getTask_id() {
        return task_id;
    }

    public void setTask_id(BigInteger task_id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
