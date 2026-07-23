package model.concrets;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Branch {

    private String branchId;
    private String name;
    private String address;
    private Map<String, List<BookCopy>> bookCopies;

    public Branch(String branchId, String name, String address, Map<String, List<BookCopy>> bookCopies) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
        this.bookCopies = bookCopies;
    }

    public Map<String, List<BookCopy>> getBookCopies() {
        return bookCopies;
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
        return Objects.equals(getBranchId(), branch.getBranchId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBranchId());
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
