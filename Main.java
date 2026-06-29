import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner generalScanner = new Scanner(System.in);

        // -----------------------------------------------------------------------------------------
        // Task 1

        System.out.print("Please enter a number to check: ");

        int number = generalScanner.nextInt();

        if (number > 0) {
            System.out.println(number + " is a positive number");
        } else if (number < 0) {
            System.out.println(number + " is a negative number");
        } else {
            System.out.println(number + " is a zero");
        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 2

        System.out.print("Please enter a number (1-7) to check the day of the week: ");

        int dayOfWeek = generalScanner.nextInt();

        String[] weekDays = {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        };

        if (dayOfWeek >= 1 && dayOfWeek <= 7) {
            System.out.println("Day of the week: " + weekDays[dayOfWeek - 1]);
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 7.");
        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 3

        // Task-da range tam bildirilməmişdi ona görə aşağıdakı şərtlərə əsasən qəbul edəcəm.
        // 1. Daxil ediləcək rəqəm müsbət və 0 - 100 intervalında olmalıdır.
        // 2. Saynağa 0-dan başlanacaq.

        System.out.print("Please enter a number (1-100) to print console: ");

        int providedNumberForPrint = generalScanner.nextInt();

        if (providedNumberForPrint >= 0 && providedNumberForPrint <= 100) {
            for (int i = 1; i < providedNumberForPrint; i += 2) {
                System.out.println(i);
            }
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 100.");
        }

        // for enter cleaning
        generalScanner.nextLine();


        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 4

        System.out.print("Please enter a number to check palindrome: ");

        String providedPalindromeRaw = generalScanner.nextLine();
        char[] providedPalindrome = providedPalindromeRaw.toCharArray();
        int providedPalindromeLength = providedPalindromeRaw.length();

        boolean isPalindrome = true,
                isIncreasing = true,
                isDecreasing = true,
                invalidProvidedNumber = false;

        if (providedPalindromeRaw.isBlank()) {
            System.out.println("Provided number cannot be blank.");
        } else {
            for (int i = 0; i < providedPalindromeLength; i++) {

                if (!Character.isDigit(providedPalindrome[i])) {
                    invalidProvidedNumber = true;
                    break;
                }

                if (i < providedPalindromeLength - 1) {
                    if (providedPalindrome[i] > providedPalindrome[i + 1]) {
                        isIncreasing = false;
                    }

                    if (providedPalindrome[i] < providedPalindrome[i + 1]) {
                        isDecreasing = false;
                    }
                }

                if (providedPalindrome[i] != providedPalindrome[providedPalindromeLength - 1 - i]) {
                    isPalindrome = false;
                }
            }

            if (invalidProvidedNumber) {
                System.out.println("Please enter digits only.");
            } else {
                System.out.println(isPalindrome ? "Palindrome" : "Not a palindrome");

                if (isIncreasing) {
                    System.out.println("The digits are in increasing order.");
                } else if (isDecreasing) {
                    System.out.println("The digits are in decreasing order.");
                } else {
                    System.out.println("The digits are neither in increasing nor decreasing order.");
                }
            }

        }

        // -----------------------------------------------------------------------------------------



        // -----------------------------------------------------------------------------------------
        // Task 5

        System.out.print("Please enter your age: ");
        int age = generalScanner.nextInt();

        // enter key cleaning
        generalScanner.nextLine();

        System.out.print("Please enter your gender (M/F): ");
        String gender = generalScanner.nextLine(); // trim, upper or lower necessary

        int permittedMinAge = 18;

        if (age <= 0 || age > 200) {
            System.out.println("Invalid age.");
        } else if (age < permittedMinAge) {
            System.out.println("Access denied");
        } else {

            if (gender.equals("M")) {
                System.out.println("Male access granted");
            } else if (gender.equals("F")) {
                System.out.println("Female access granted");
            } else {
                System.out.println("Invalid gender.");
            }

        }

        // -----------------------------------------------------------------------------------------



        // -----------------------------------------------------------------------------------------
        // Task 6

        System.out.print("Please enter a number (1-100) to fibonacci list: ");

        int fibonacciLength = generalScanner.nextInt();

        if (fibonacciLength < 1 || fibonacciLength > 100) {
            System.out.println("Please enter a number between 1 and 100.");
        } else {

            int[] fibonacci = new int[fibonacciLength];

            fibonacci[0] = 0;

            if (fibonacciLength > 1) {
                fibonacci[1] = 1;
            }

            for (int i = 2; i < fibonacciLength; i++) {
                fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            }

            for (int i = 0; i < fibonacciLength; i++) {
                System.out.print(fibonacci[i] + " ");
            }
        }

        System.out.println();

        // -----------------------------------------------------------------------------------------



        // -----------------------------------------------------------------------------------------
        // Task 7

        System.out.print("Please enter a number (0-12) to calculate factorial: ");

        int factorialLength = generalScanner.nextInt();
        int startValue = 1;

        if (factorialLength < 0 || factorialLength > 12) {
            if (factorialLength < 0) {
                System.out.println("Factorial does not exist for negative numbers.");
            }

            System.out.println("Please enter a number between 0 and 12.");
        } else {

            for (int i = 1; i <= factorialLength; i++) {
                startValue *= i;
            }

            System.out.println("Factorial of " + fibonacciLength + " is " + startValue);
        }

        // -----------------------------------------------------------------------------------------



        // -----------------------------------------------------------------------------------------
        // Task 8

        System.out.print("Please enter a number (1-1000) to check strong number: ");

        int providedFactorialNumber = generalScanner.nextInt();
        char[] providedFactorialArray = Integer.toString(providedFactorialNumber).toCharArray();

        if (providedFactorialNumber < 0 || providedFactorialNumber > 1000) {
            if (providedFactorialNumber < 0) {
                System.out.println("Factorial does not exist for negative numbers.");
            }

            System.out.println("Please enter a number between 1 and 1000.");
        } else {

            int totalSum = 0;

            for (int digitOfNumber : providedFactorialArray) {
                startValue = 1;

                for (int j = 1; j <= digitOfNumber; j++) {
                    startValue *= j;
                }

                totalSum += startValue;
            }

            if (totalSum == providedFactorialNumber) {
                System.out.println(totalSum + " is a strong number");
            } else {
                System.out.println(totalSum + " is not a strong number");
            }
        }

        // -----------------------------------------------------------------------------------------


        generalScanner.close();

    }

}
