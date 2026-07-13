package src.model;

public class Magazine extends Item {

    public Integer issueNumber;

    public Magazine(Integer id, String title, boolean isAvailable, Integer issueNumber) {

        super(id, title, isAvailable);
        this.issueNumber = issueNumber;
    }

    @Override
    public void displayInfo() {

    }

}
