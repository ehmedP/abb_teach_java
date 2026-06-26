public class Main {

    public static void main(String[] args) {

        // Task 1

        int x = -7;

        // if condition usage

        if (x > 0) {
            System.out.println("Müsbət");
        } else if (x < 0) {
            System.out.println("Mənfi");
        } else {
            System.out.println("Sıfır");
        }

        // ternary usage

        System.out.println(
                x > 0 ? "Müsbət" :
                        x < 0 ? "Mənfi" : "Sıfır"
        );


        // Task 2

        // if condition usage

        int a = 15, b = 28;

        if (a > b) {
            System.out.println(a);
        } else if (a < b) {
            System.out.println(b);
        } else {
            System.out.println("Bərabərdir");
        }

        // ternary usage

        System.out.println(
                a > b ? a :
                        b > a ? b : "Bərabərdir"
        );


        // Task 3

        int n = 12;

        if (n == 0) {

            System.out.println("Sıfır");

        } else if (n % 2 == 0) {

            if (n > 0) {
                System.out.println("Müsbət və Cüt");
            } else {

                System.out.println("Mənfi və Cüt");
            }
        } else {

            if (n > 0) {
                System.out.println("Müsbət və Tək");
            } else {
                System.out.println("Mənfi və Tək");
            }

        }


        // Task 4

        int x2 = 10, y = 4;
        char op = '+';

        switch (op) {
            case '+':
                System.out.println(x2 + "+" + y + " = " + (x2 + y));
                break;
            case '-':
                System.out.println(x2 + "-" + y + " = " + (x2 - y));
                break;
            case '*':
                System.out.println(x2 + "*" + y + " = " + (x2 * y));
                break;
            case '/':
                if (y == 0) {
                    System.out.println("Sıfıra bölmə əməliyyatı edilə bilməz.");
                } else {
                    System.out.println(x2 + "/" + y + " = " + (x2 / y));
                }
                break;
            default:
                System.out.println("Invalid operation");
        }


        // Task 5

        int ay = 11;

        switch (ay) {
            case 3, 4, 5:
                System.out.println("Yaz");
                break;

            case 6, 7, 8:
                System.out.println("Yay");
                break;

            case 9, 10, 11:
                System.out.println("Payız");
                break;

            case 12, 1, 2:
                System.out.println("Qış");
                break;

            default:
                System.out.println("Invalid number of month");
        }

        // Alternative case (enum)
        // Dersde bunuda gostermisdiniz, taskda qeyd edilmemisdi sadece alternativ olaraq yazdim


        int month = 11;
        Season season;

        switch (month) {
            case 3, 4, 5:
                season = Season.SPRING;
                break;

            case 6, 7, 8:
                season = Season.SUMMER;
                break;

            case 9, 10, 11:
                season = Season.AUTUMN;
                break;

            case 12, 1, 2:
                season = Season.WINTER;
                break;

            default:
                System.out.println("Invalid month.");
                return;
        }

        System.out.println(season.getLabel());

    }

    public enum Season {
        SPRING("Yaz"),
        SUMMER("Yay"),
        AUTUMN("Payız"),
        WINTER("Qış");

        private final String label;

        Season(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

}
