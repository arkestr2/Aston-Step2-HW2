package model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "age", nullable = false)
    private int age;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public User() {}

    public User(String name, String email, int age) {
        setName(name);
        setEmail(email);
        setAge(age);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank() || name.length() > 254) {
            throw new IllegalArgumentException("Некорректная длина или пустой name");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || email.length() > 254) {
            throw new IllegalArgumentException("Некорректная длина или пустой email");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Невалидный email");
        }
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18 || age > 150) {
            throw new IllegalArgumentException("Невалидный возраст");
        }
        this.age = age;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                '}';
    }
}
