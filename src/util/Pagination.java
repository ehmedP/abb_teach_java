package src.util;

import src.interfaces.Paginatable;

import java.util.Scanner;

public class Pagination {

    public static final Integer PAGINATION = 10;
    public static final Scanner scanner = new Scanner(System.in);

    public static void paginatedItems(Integer itemCount, Paginatable[] items) {
        Pagination.paginatedItems(itemCount, PAGINATION, items);
    }

    private static void printPage(int page, int paginate, int totalPages, Paginatable[] items, Integer itemCount) {

        int start = (page - 1) * paginate;
        int end = Math.min(start + paginate, itemCount);

        System.out.println();
        System.out.println("------------------------------------- Items (Page " + page + "/" + totalPages + ") ------------------------------------");

        for (int i = start; i < end; i++) {
            items[i].displaySimpleInfo();
        }

        System.out.println("-------------------------------------------------------------------------------------------");
    }

    public static void paginatedItems(Integer itemCount, Integer paginate, Paginatable[] items) {

        if (paginate == null) {
            paginate = PAGINATION;
        }

        if (itemCount == 0) {
            System.out.println("There are no items yet.");
            return;
        }

        int totalPages = (int) Math.ceil((double) itemCount / paginate);
        int currentPage = 1;

        while (true) {

            printPage(currentPage, paginate, totalPages, items, itemCount);

            System.out.print("[N] Next Page, [P] Previous Page, [Q] Back to Menu: ");
            String option = scanner.nextLine().trim();

            if (option.equalsIgnoreCase("N")) {

                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    System.out.println("You are already on the last page.");
                }

            } else if (option.equalsIgnoreCase("P")) {

                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.println("You are already on the first page.");
                }

            } else if (option.equalsIgnoreCase("Q") || option.isEmpty()) {
                return;

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
