package Lab5.Project_mini_final_without_Security.controller;

import Lab5.Project_mini_final_without_Security.model.Task;
import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.TaskRepository;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import Lab5.Project_mini_final_without_Security.service.TaskService;
import Lab5.Project_mini_final_without_Security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder password_end;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public UserController(UserService userService, UserRepository userRepository,
                          PasswordEncoder password_end, TaskService taskService,
                          TaskRepository taskRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.password_end = password_end;
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }



    // MAIN PAGE
    @GetMapping("/main_page/{username}")
    public String getUserPage(@PathVariable("username") String username, Model model) {
        User u = userService.findUserByUsername(username);

        if (u != null) {
            model.addAttribute("user", u);
            return "main-page";  // Название страницы, на которой будет доступен объект user
        } else {
            return "redirect:/error";  // Перенаправление на страницу с ошибкой, если пользователя нет
        }
    }










    //REGISTER - LOGIN - SAVE
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setCreate_of(LocalDateTime.now());
        userService.create(user);
        return "login";
    }


    // LOGIN

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login"; // Имя HTML-шаблона для страницы входа
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && password_end.matches(password, user.get().getPassword())) {
            User u = user.get();
            session.setAttribute("user", u);
            return "redirect:/main_page/" + u.getUser_id();
        } else {
            model.addAttribute("error", "Неверный логин или пароль!");
            return "login";
        }
    }


//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam("username") String username,
//                        @RequestParam("password") String password, Model model, HttpSession session) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent() && password_end.matches(password, user.get().getPassword())) {
//            User u = user.get();
//            session.setAttribute("user", u);
//            return "redirect:/main_page";
//        } else {
//            model.addAttribute("error", "неверный логин или пароль!");
//            return "login";
//        }
//    }


    //     LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password, Model model) {
//        User user = userRepository.findByUsername(username).orElse(null);
//        if (user != null && user.getPassword().equals(password)) {
//            return "redirect:/main_page";
//        }
//        model.addAttribute("error", "Invalid username or password.");
//        return "login";
//    }




    // USER LIST - SHOW
    @GetMapping("users/show/{user_id}")
    public String showUsers(Model model, @PathVariable("user_id") ObjectId user_id) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            List<User> users = userService.show();
            model.addAttribute("users", users);
            model.addAttribute("user", user.get());
            return "user-list";
        } catch (Exception e) {
            return "error";
        }
    }




    // UPDATE - PUTMAPPING
    @GetMapping("/edit/{user_id}")
    public String showEditForm(@PathVariable("user_id") ObjectId id, Model model) {
        try {
            Optional<User> user = userService.find_by_user_id(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "user-form";
            } else {
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/edit/{user_id}")
    public String updateUser(@PathVariable("user_id") ObjectId user_id, User user) {
        try {
                userService.update(user_id,user);
                return "redirect:/users/show";

        } catch (Exception e) {
            return "error";
        }
    }



    // DELETE
    @GetMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") ObjectId user_id) {
        try {
            userService.delete(user_id);
            return "redirect:/users/show/" + user_id;
        } catch (Exception e) {
            return "error";
        }
    }



    // SHOW TASKS  - GET
    @GetMapping("tasks/show/{user_id}")
    public String getUserTasks(@PathVariable("user_id") ObjectId user_id,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "3") int size,
                               @RequestParam(value = "search", required = false) String search,
                               Model model) {
        Optional<User> user_db = userRepository.findById(user_id);

        if (user_db.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден!");
            return "error";
        }
        User user = user_db.get();
        List<Task> tasksList;
        Page<Task> tasksPage = null;

        if (search != null) {
            tasksList = taskRepository.findByUser_idAndTitleContaining(user_id, search);
        } else {
            tasksPage = taskRepository.findByUserId_(user_id, PageRequest.of(page, size));
            tasksList = tasksPage.getContent();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Task task : tasksList) {
            String formattedDate;
            if (task.getDue_date() != null) {
                formattedDate = task.getDue_date().format(formatter);
            } else {
                formattedDate = "N/A";
            }
            task.setFormattedDueDate(formattedDate);
        }

        model.addAttribute("user", user);
        model.addAttribute("tasks", tasksList);
        model.addAttribute("current_page", page);
        model.addAttribute("total_pages", tasksPage != null ? tasksPage.getTotalPages() : 1);
        model.addAttribute("total_tasks", tasksPage != null ? tasksPage.getTotalElements() : tasksList.size());
        model.addAttribute("search", search);

        return "user-tasks";
    }


//    @GetMapping("tasks/show/{user_id}")
//    public String getUserTasks(@PathVariable("user_id") ObjectId user_id, Model model) {
//
//        Logger logger = LoggerFactory.getLogger(getClass());
//        logger.info("Попытка найти пользователя с id: {}", user_id);
//
//        Optional<User> user_db = userRepository.findById(user_id);
//
//        if (user_db.isEmpty()) {
//            logger.error("Пользователь с id {} не найден", user_id);
//            model.addAttribute("error", "Пользователь не найден!");
//            return "error";
//        }
//        User user = user_db.get();
//        logger.info("Пользователь найден: {}", user.getUsername());
//
//        List<Task> tasks = taskRepository.findByUserId(user_id);
//
//        if (tasks.isEmpty()) {
//            logger.warn("У пользователя {} нет задач", user.getUsername());
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        for (Task task : tasks) {
//            String formattedDate = task.getDue_date() != null ? task.getDue_date().format(formatter) : "N/A";
//            task.setFormattedDueDate(formattedDate);
//        }
//        model.addAttribute("user", user);
//        model.addAttribute("tasks", tasks);
//        return "user-tasks";
//    }




    // CREATE TASK - POST
    @GetMapping("/tasks/create/{user_id}")
    public String showCreateTaskForm(@PathVariable("user_id") ObjectId user_id, Model model) {
        Optional<User> user_db = userService.find_by_user_id(user_id);
        if (user_db.isEmpty()) {
            model.addAttribute("error", "Пользователь не найден!");
            return "error";
        }

        model.addAttribute("user_id", user_id);
        return "task-create";
    }


    @PostMapping("/tasks/create/{user_id}")
    public String createTask(
            @PathVariable("user_id") ObjectId user_id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam String status,
            @RequestParam String priority,
            Model model
    ) {
        Logger logger = LoggerFactory.getLogger(getClass());
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()) {
            logger.error("Пользователь с id {} не найден", user_id);
            model.addAttribute("error", "Пользователь не найден!");
            return "error";
        }
        LocalDateTime dueDateTime = dueDate.atStartOfDay();
        Task task = new Task();
        task.setUser_id(user_id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDue_date(dueDateTime);
        task.setStatus(status);
        task.setPriority(priority);

        taskRepository.save(task);
        logger.info("Задача успешно сохранена: {}", task);

        return "redirect:/tasks/show/" + user_id;
    }







    // UPDATE TASK - PUTMAPPING
    @GetMapping("/tasks/edit/{task_id}")
    public String show_edit_task (@PathVariable("task_id") ObjectId taskId, Model model) {
        Logger logger = LoggerFactory.getLogger(getClass());

        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            logger.error("Задача с id {} не найдена", taskId);
            model.addAttribute("error", "Задача не найдена!");
            return "error";
        }

        model.addAttribute("task", task.get());
        return "task-form";
    }

    @PostMapping("/tasks/edit/{task_id}")
    public String updateTask(
            @PathVariable("task_id") ObjectId task_id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String formattedDueDate,
            @RequestParam String status,
            @RequestParam String priority,
            Model model
    ) {
        Logger logger = LoggerFactory.getLogger(getClass());

        Optional<Task> task = taskRepository.findById(task_id);
        if (task.isEmpty()) {
            logger.error("Задача с id {} не найдена", task_id);
            model.addAttribute("error", "Задача не найдена!");
            return "error";
        }

        Task t = task.get();
        t.setTitle(title);
        t.setDescription(description);
        t.setFormattedDueDate(formattedDueDate);
        t.setStatus(status);
        t.setPriority(priority);

        taskRepository.save(t);
        logger.info("Задача с id {} успешно обновлена", task_id);

        return "redirect:/tasks/show/" + t.getUser_id();
    }


    // DELETE TASKS
    @GetMapping("/tasks/delete/{task_id}")
    public String deleteTask(@PathVariable("task_id") ObjectId task_id) {
        Optional<Task> task_db = taskRepository.findById(task_id);
        Task t = task_db.get();
        taskService.delete_task(task_id);
        return "redirect:/tasks/show/" + t.getUser_id();
    }

}












/*
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository user_repo;

    public UserController(UserService userService, UserRepository user_repo) {
        this.userService = userService;
        this.user_repo = user_repo;
    }

    @PostMapping("/create")
    public User save(@RequestBody User u){
        return userService.create(u);
    }

    @GetMapping("/show")
    public String findAll(Model model) {
        List<User> users = userService.show();
        model.addAttribute("users", users);
        return "user_list";
    }

//    @GetMapping("/show")
//    public List<User> findAll (){
//        return userService.show();
//    }

    @GetMapping("/find_by_id")
    public User find_by_id (@RequestParam Long user_id){
        return userService.find_by_id(user_id);
    }

    @PutMapping("/edit")
    public User update (@RequestBody User u){
        return userService.update(u);
    }

    @DeleteMapping("/delete")
    public void delete (@RequestParam Long user_id){
        userService.delete(user_id);
    }


}

*/