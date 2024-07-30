package ma.study.effective.java.service;

import ma.study.effective.java.entity.User;
import ma.study.effective.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerAdmin(String username, String password, String address, String phoneNumber) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }
        User user = User.createAdmin(username, passwordEncoder.encode(password), address, phoneNumber);
        return userRepository.save(user);
    }

    public User registerUser(String username, String password, String address, String phoneNumber) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }
        User user = User.createUser(username, passwordEncoder.encode(password), address, phoneNumber);
        return userRepository.save(user);
    }

    public User registerGuest(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }
        User user = User.createGuest(username);
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(userRepository.findAll());
    }
}
