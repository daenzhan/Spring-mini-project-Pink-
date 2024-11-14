package Lab5.Project_mini_final_without_Security.controller;

import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import Lab5.Project_mini_final_without_Security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    // MAIN PAGE
    @GetMapping("/main_page")
    public String mainPage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "main-page";
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
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            User u = user.get();
            session.setAttribute("user", u);
            return "redirect:/main_page";
        } else {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";  // Если авторизация не удалась, возвращаем на страницу входа
        }
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
    @GetMapping("users/show")
    public String showUsers(Model model) {
        try {
            List<User> users = userService.show();
            model.addAttribute("users", users);
            return "user-list";
        } catch (Exception e) {
            logger.error("Error while fetching users", e);
            return "error";
        }
    }




    // UPDATE - PUTMAPPING
    @GetMapping("/edit/{user_id}")
    public String showEditForm(@PathVariable("user_id") BigInteger id, Model model) {
        try {
            Optional<User> user = userService.find_by_user_id(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "user-form";
            } else {
                return "error";
            }
        } catch (Exception e) {
            logger.error("Error while fetching user with id " + id, e);
            return "error";
        }
    }

    @PostMapping("/edit/{user_id}")
    public String updateUser(@PathVariable("user_id") BigInteger id, User user) {
        try {
                userService.update(id,user);
                return "redirect:/users/show";

        } catch (Exception e) {
            logger.error("Error while updating user with id " + id, e);
            return "error";
        }
    }



    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") BigInteger id) {
        try {
            userService.delete(id);
            return "redirect:/users/show";
        } catch (Exception e) {
            logger.error("Error while deleting user with id " + id, e);
            return "error";
        }
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