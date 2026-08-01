package src.model;

import java.util.List;
import java.util.Objects;

public class User {

    private static Integer nextId = 1;

    private final Integer id;
    private String name;
    private Integer age;
    private List<BorrowRecord> borrowHistory;

    public User(String name, Integer age, List<BorrowRecord> borrowHistory) {
        this.id = nextId++;

        this.name = name;
        this.age = age;
        this.borrowHistory = borrowHistory;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<BorrowRecord> getBorrowHistory() {
        return borrowHistory;
    }

    public void setBorrowHistory(List<BorrowRecord> borrowHistory) {
        this.borrowHistory = borrowHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", borrowHistory=" + borrowHistory +
                '}';
    }
}
