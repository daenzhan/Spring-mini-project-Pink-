package Lab5.Project_mini_final_without_Security.service;

import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // create
    public User create(User u) {
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPassword);
        return userRepository.save(u);
    }

    //find
    public Optional<User> find_by_user_id(ObjectId id) {
        return userRepository.findById(id);
    }

    //show list
    public List<User> show (){
        return userRepository.findAll();
    }


    //update
    public User update(ObjectId id, User u) {
        Optional<User> db_u = userRepository.findById(id);
        if (db_u.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User existUser = db_u.get();
        existUser.setUsername(u.getUsername());
        existUser.setPassword(passwordEncoder.encode(u.getPassword()));
        existUser.setEmail(u.getEmail());
        return userRepository.save(existUser);
    }




    //delete
    public void delete (ObjectId user_id){
        Optional <User> db_u = userRepository.findById(user_id);
        if (db_u.isEmpty()){
            throw new RuntimeException("пользователь не найден!");
        }
        userRepository.delete(db_u.get());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername_(username);
    }



}
