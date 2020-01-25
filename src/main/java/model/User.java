package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_registration")
    @CreationTimestamp
    private Date dateOfRegistration;


    @ManyToMany(mappedBy = "follows")
    private Set<User> followers = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "follows_followers",
            joinColumns = {@JoinColumn(name = "follows_id")},
            inverseJoinColumns = {@JoinColumn(name = "followed_id")})

    private Set<User> follows = new HashSet<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followed) {
        this.followers = followed;
    }

    public Set<User> getFollows() {
        return follows;
    }

    public void setFollows(Set<User> follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", followed=" + followers +
                ", follows=" + follows +
                '}';
    }

    public static class UserBuilder {

        private String login;
        private String name;
        private String lastName;
        private String password;
        private String email;


        public static UserBuilder getBuilder() {
            return new UserBuilder();
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder surname(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            User user = new User();
            user.setName(this.name);
            user.setLastName(this.lastName);
            user.setLogin(this.login);
            user.setEmail(this.email);
            user.setPassword(this.password);
            return user;
        }

    }


}
