package model.concrets;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Branch {

    private Integer id;
    private String branchId;
    private String name;
    private String address;
    private Map<String, List<BookCopy>> bookCopies;

    public Branch(Integer id, String branchId, String name, String address, Map<String, List<BookCopy>> bookCopies) {
        this.id = id;
        this.branchId = branchId;
        this.name = name;
        this.address = address;
        this.bookCopies = bookCopies;
    }

    public Map<String, List<BookCopy>> getBookCopies() {
        return bookCopies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBookCopies(Map<String, List<BookCopy>> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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
                "branchId=" + branchId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
