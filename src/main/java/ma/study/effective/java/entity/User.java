package ma.study.effective.java.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    private String address;
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static User createAdmin(String username, String password, String address, String phoneNumber) {
        return User.builder()
                .username(username)
                .password(password)
                .roles(Set.of(Role.ADMIN))
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    public static User createUser(String username, String password, String address, String phoneNumber) {
        return User.builder()
                .username(username)
                .password(password)
                .roles(Set.of(Role.USER))
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    public static User createGuest(String username) {
        return User.builder()
                .username(username)
                .roles(Set.of(Role.GUEST))
                .build();
    }


    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String username;
        private String password;
        private Set<Role> roles;
        private String address;
        private String phoneNumber;

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            if (roles != null && roles.contains(Role.GUEST)) {
                throw new IllegalArgumentException("Phone number cannot be set for GUEST role");
            }
            this.password = password;
            return this;
        }

        public UserBuilder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder address(String address) {
            if (roles != null && roles.contains(Role.GUEST)) {
                throw new IllegalArgumentException("Address cannot be set for GUEST role");
            }
            this.address = address;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            if (roles != null &&roles.contains(Role.GUEST)) {
                throw new IllegalArgumentException("Phone number cannot be set for GUEST role");
            }
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            User user = new User();
            user.username = this.username;
            user.password = this.password;
            user.roles = this.roles;
            user.address = this.address;
            user.phoneNumber = this.phoneNumber;
            return user;
        }
    }

}
