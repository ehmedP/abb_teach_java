package model.concrets;

import enums.CopyStatusEnum;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Branch {

    private static int nextId = 1;

    private final Integer id;
    private String name;
    private String address;

    // bookId -> List of BookCopy
    private Map<Integer, List<BookCopy>> bookCopies;

    public Branch(String name, String address, Map<Integer, List<BookCopy>> bookCopies) {
        this.id = nextId++;

        this.name = name;
        this.address = address;
        this.bookCopies = bookCopies;
    }

    // bookId -> List<BookCopy>
    public Map<Integer, List<BookCopy>> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(Map<Integer, List<BookCopy>> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public BookCopy findAvailableBookCopy(Integer bookId) {
        List<BookCopy> copies = getBookCopies().get(bookId);

        if (copies == null || copies.isEmpty()) {
            return null;
        }

        for (BookCopy copy : copies) {
            if (copy.getStatus() == CopyStatusEnum.AVAILABLE) {
                return copy;
            }
        }

        return null;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(getId(), branch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bookCopies=" + bookCopies +
                '}';
    }
}
