package ma.study.effective.java.controller;

import ma.study.effective.java.entity.Role;
import ma.study.effective.java.entity.User;
import ma.study.effective.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public User registerAdmin(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String address,
                              @RequestParam String phoneNumber) {
        return userService.registerAdmin(username, password, address, phoneNumber);
    }

    @PostMapping("/admin/register/user")
    @PreAuthorize("hasRole('ADMIN')")
    public User registerUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String address,
                             @RequestParam String phoneNumber) {
        return userService.registerUser(username, password, address, phoneNumber);
    }

    @PostMapping("/public/register/guest/{username}")
    public User registerGuest(@PathVariable String username) {
        return userService.registerGuest(username);
    }

    @GetMapping("/admin/users/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
