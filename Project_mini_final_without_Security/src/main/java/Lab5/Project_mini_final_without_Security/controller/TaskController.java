
import Lab5.Project_mini_final_without_Security.model.Category;
import Lab5.Project_mini_final_without_Security.model.Task;
import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.CategoryRepository;
import Lab5.Project_mini_final_without_Security.repository.TaskRepository;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import Lab5.Project_mini_final_without_Security.service.TaskService;
import Lab5.Project_mini_final_without_Security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller // Используем Controller для работы с HTML и Thymeleaf
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserService userService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


}



//@RestController // Используем RestController для работы с JSON
//@RequestMapping("/api") // Базовый путь для API
//public class TaskController {
//
//    private final TaskService taskService;
//    private final UserService userService;
//
//    public TaskController(TaskService taskService, UserService userService) {
//        this.taskService = taskService;
//        this.userService = userService;
//    }
//
//    // GET-запрос для получения задач пользователя
//    @GetMapping("/tasks/{user_id}")
//    public ResponseEntity<?> getUserTasks(@PathVariable("user_id") ObjectId user_id) {
//        Optional<User> user_db = userService.find_by_user_id(user_id);
//        if (user_db.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("error", "Пользователь не найден!"));
//        }
//
//        User user = user_db.get();
//        List<Task> tasks = taskService.findByUserId(user_id);
//
//        Map<String, Object> response = Map.of(
//                "user_id", user.getUser_id(),
//                "user_name", user.getUsername(),
//                "tasks", tasks
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    // POST-запрос для создания новой задачи
//    @PostMapping("/tasks/{user_id}")
//    public ResponseEntity<?> createTask(@PathVariable("user_id") ObjectId user_id, @RequestBody Task task) {
//        Optional<User> user_db = userService.find_by_user_id(user_id);
//        if (user_db.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Map.of("error", "Пользователь не найден!"));
//        }
//
//        User user = user_db.get();
//        task.setUser(user); // Привязываем задачу к пользователю
//        taskService.save(task); // Сохраняем задачу
//
//        Map<String, Object> response = Map.of(
//                "message", "Задача успешно добавлена!",
//                "task", task
//        );
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Ответ с созданной задачей и статусом 201
//    }
//}

//
//@Controller
//public class TaskController {
//
//    private final TaskService taskService;
//    private final UserService userService;
//
//    public TaskController(TaskService taskService, UserService userService) {
//        this.taskService = taskService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/tasks/{user_id}")
//    public String getUserTasks(@PathVariable("user_id") BigInteger user_id, Model model) {
//        Optional<User> user_db = userService.find_by_user_id(user_id);
//        if (user_db.isEmpty()) {
//            model.addAttribute("error", "пользователь не найден!");
//            return "error";
//        }
//        User user = user_db.get();
//        List<Task> tasks = taskService.findByUserId(user_id);
//        model.addAttribute("user_id", user.getUser_id());
//        model.addAttribute("tasks", tasks);
//        return "user-tasks";
//    }



//    @GetMapping("/tasks")
//    public String getTasks(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            return "redirect:/login";
//        }
//        List<Task> tasks = taskService.findByUser(user);
//
//        model.addAttribute("tasks", tasks);
//
//        return "user-tasks";
//    }





















/*
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private HttpSession httpSession;

    public TaskController(TaskService taskService, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/tasks/{user_id}")
    public String showUserTasks(@PathVariable("user_id") BigInteger user_id, Model model) {
        if (user_id != null) {
            List<Task> tasks = taskService.get_list_taks_by_user_id(user_id);
            model.addAttribute("tasks", tasks);
            return "user-tasks";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> create(@RequestBody Task t) {
        Task created_t = taskService.create_task(t);

        Optional<Category> c_db = categoryRepository.findById(t.getCategory_id());
        if (c_db.isPresent()) {
            Category c = c_db.get();

            if (c.getTasks() == null) {
                c.setTasks(new ArrayList<>());
            }

            c.getTasks().add(created_t);
            categoryRepository.save(c);
        }

        Optional<User> u_db = userRepository.findById(t.getUser_id());
        if (u_db.isPresent()) {
            User u = u_db.get();

            if (u.getTasks() == null) {
                u.setTasks(new ArrayList<>());
            }

            u.getTasks().add(created_t);
            userRepository.save(u);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(created_t);
    }






    @PutMapping("/update/{task_id}")
    public ResponseEntity<Task> update(@PathVariable BigInteger task_id, @RequestBody Task t, BigInteger user_id) {
        try {
            Task updated_t = taskService.update_tasks(t, user_id);
            if (updated_t == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(updated_t, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{task_id}")
    public ResponseEntity<Void> delete(@PathVariable BigInteger task_id,@RequestBody BigInteger user_id) {
        taskService.delete_task(task_id, user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/logout")
    public String logout() {
        // Удаляем user_id из сессии
        httpSession.invalidate(); // Очистка сессии
        return "redirect:/users/login"; // Перенаправляем на страницу логина
    }
}




*/

/*
    // create
    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody Task t) {
        Task created_t = taskService.create_task(t);

        Optional<Category> c_db = categoryRepository.findById(t.getCategory_id());
        if (c_db.isPresent()) {
            Category c = c_db.get();

            if (c.getTasks() == null) {
                c.setTasks(new ArrayList<>());
            }

            c.getTasks().add(created_t);
            categoryRepository.save(c);
        }

        Optional<User> u_db = userRepository.findById(t.getUser_id());
        if (u_db.isPresent()) {
            User u = u_db.get();

            if (u.getTasks() == null) {
                u.setTasks(new ArrayList<>());
            }

            u.getTasks().add(created_t);
            userRepository.save(u);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(created_t);
    }

    // show
    @GetMapping("/show_user_tasks/{user_id}")
    public ResponseEntity<List<Task>> show (@PathVariable BigInteger user_id) {
        User user = new User();
        user.setUser_id(user_id);
        List<Task> tasks = taskService.get_list_taks_by_user_id(user);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // update
    @PutMapping("/update/{task_id}")
    public ResponseEntity<Task> update(@PathVariable BigInteger task_id, @RequestBody Task t, BigInteger user_id) {
        try {
            Task updated_t = taskService.update_tasks(t, user_id);
            if (updated_t == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(updated_t, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // delete
    @DeleteMapping("/delete/{task_id}")
    public ResponseEntity<Void> delete(@PathVariable BigInteger task_id,@RequestBody BigInteger user_id) {
        taskService.delete_task(task_id, user_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
    */

