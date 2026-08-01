package src.model;

import java.util.List;

public class User {

    private String name;
    private Integer age;
    private List<BorrowRecord> borrowHistory;

    public User(String name, Integer age, List<BorrowRecord> borrowHistory) {
        this.name = name;
        this.age = age;
        this.borrowHistory = borrowHistory;
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
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", borrowHistory=" + borrowHistory +
                '}';
    }
}
