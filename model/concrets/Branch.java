package model.concrets;

import java.util.Objects;

public class Branch {

    private Integer branchId;
    private String name;
    private String address;

    public Branch(Integer branchId, String name, String address) {
        this.branchId = branchId;
        this.name = name;
        this.address = address;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
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
        return Objects.equals(getBranchId(), branch.getBranchId()) &&
                Objects.equals(getName(), branch.getName()) &&
                Objects.equals(getAddress(), branch.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBranchId(), getName(), getAddress());
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
