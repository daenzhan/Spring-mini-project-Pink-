package Lab5.Project_mini_final_without_Security.security;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя по имени пользователя из репозитория
        Lab5.Project_mini_final_without_Security.model.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Возвращаем объект UserDetails, который реализует Spring Security
        return User.withUsername(user.getUsername())
                .password(user.getPassword())  // Пароль должен быть закодированным
                .roles("USER")  // Добавьте нужные роли
                .build();
    }
}
