package src.model;

public class Magazine extends Item {

    public Integer issueNumber;

    public Magazine(Integer id, String title, boolean isAvailable, Integer issueNumber) {

        super(id, title, isAvailable);
        this.issueNumber = issueNumber;
    }

    @Override
    public void displayInfo() {

        System.out.println();
        System.out.println("---------------------------- Magazine Info ----------------------------");
        System.out.printf("ID        : %d%n", getId());
        System.out.printf("Title     : %s%n", getTitle());
        System.out.printf("Issue #   : %d%n", issueNumber);
        System.out.printf("Status    : %s%n", getIsAvailable() ? "Available" : "Checked out");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println();
    }

}
