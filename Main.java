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

            for (int i = 1; i <= providedNumberForPrint; i += 2) {
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
                    if (providedPalindrome[i] >= providedPalindrome[i + 1]) {
                        isIncreasing = false;
                    }

                    if (providedPalindrome[i] <= providedPalindrome[i + 1]) {
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
        String gender = generalScanner.nextLine();

        int permittedMinAge = 18;

        if (age <= 0 || age > 200) {
            System.out.println("Invalid age.");
        } else if (age < permittedMinAge) {
            System.out.println("Access denied");
        } else {

            if (gender.equalsIgnoreCase("M")) {
                System.out.println("Male access granted");
            } else if (gender.equalsIgnoreCase("F")) {
                System.out.println("Female access granted");
            } else {
                System.out.println("Invalid gender entered.");
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

            long[] fibonacci = new long[fibonacciLength];

            fibonacci[0] = 0;

            if (fibonacciLength > 1) {
                fibonacci[1] = 1;
            }

            for (int i = 2; i < fibonacciLength; i++) {
                fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            }

            for (int i = 0; i < fibonacciLength; i++) {
                System.out.println(
                        fibonacci[i] +
                                (fibonacci[i] % 2 == 0 ? " -> Even" : " -> Odd")
                );
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

            System.out.println("Factorial of " + factorialLength + " is " + startValue);
        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 8

        System.out.print("Please enter a number (1-1000) to check strong number: ");

        int providedFactorialNumber = generalScanner.nextInt();

        if (providedFactorialNumber < 1 || providedFactorialNumber > 1000) {

            System.out.println("Please enter a number between 1 and 1000.");

        } else {

            int totalSum = 0;
            int tempNumber = providedFactorialNumber;

            while (tempNumber > 0) {

                int digit = tempNumber % 10;

                int factorial = 1;

                for (int i = 1; i <= digit; i++) {
                    factorial *= i;
                }

                totalSum += factorial;

                tempNumber /= 10;
            }

            if (totalSum == providedFactorialNumber) {
                System.out.println(providedFactorialNumber + " is a strong number");
            } else {
                System.out.println(providedFactorialNumber + " is not a strong number");
            }
        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 9

        System.out.print("Please enter a number (1-1000) to check armstrong number: ");

        int providedArmstrongNumber = generalScanner.nextInt();

        if (providedArmstrongNumber < 1 || providedArmstrongNumber > 1000) {

            System.out.println("Please enter a number between 1 and 1000.");

        } else {

            int totalSum = 0;
            int tempNumber = providedArmstrongNumber;
            int numberLength = String.valueOf(providedArmstrongNumber).length();

            while (tempNumber > 0) {

                // Burada math methodunu istifade etmeden-de etmek mumkundur.
                // Bele ki ededi ozu ozune numberLength qeder vurmaq lazimdir.
                // Bunu ise for ile etmek mumkundur. Sadece tekrar for yazmaq istemedim aciqi.

                totalSum += (int) Math.pow(tempNumber % 10, numberLength);

                tempNumber /= 10;
            }

            if (totalSum == providedArmstrongNumber) {
                System.out.println(providedArmstrongNumber + " is an Armstrong number");
            } else {
                System.out.println(providedArmstrongNumber + " is not an Armstrong number");
            }
        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 10

        System.out.print("Please enter array length (1-100): ");
        int arrayLength = generalScanner.nextInt();

        if (arrayLength < 1 || arrayLength > 100) {

            System.out.println("Please enter a number between 1 and 100.");

        } else {

            int[] findMaxMinArray = new int[arrayLength];

            for (int i = 0; i < arrayLength; i++) {
                System.out.print("Enter element [" + (i + 1) + "]: ");
                findMaxMinArray[i] = generalScanner.nextInt();
            }

            int min = findMaxMinArray[0];
            int max = findMaxMinArray[0];

            for (int iterator : findMaxMinArray) {
                if (iterator < min) {
                    min = iterator;
                }

                if (iterator > max) {
                    max = iterator;
                }
            }

            System.out.println("Maximum number: " + max);
            System.out.println("Minimum number: " + min);

        }

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 11

        System.out.print("Please enter array size (1-20): ");
        int arraySize = generalScanner.nextInt();

        if (arraySize < 1 || arraySize > 20) {

            System.out.println("Please enter a number between 1 and 20.");

        } else {

            int[][] array = new int[arraySize][arraySize];

            System.out.println("Please enter the array elements: ");

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print("Element [" + i + "][" + j + "]: ");
                    array[i][j] = generalScanner.nextInt();
                }
            }

            int primaryDiagonalSum = 0;
            int secondaryDiagonalSum = 0;

            for (int i = 0; i < array.length; i++) {
                primaryDiagonalSum += array[i][i];
                secondaryDiagonalSum += array[i][array.length - 1 - i];
            }

            System.out.println("Primary diagonal sum: " + primaryDiagonalSum);
            System.out.println("Secondary diagonal sum: " + secondaryDiagonalSum);
        }


        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 12

        System.out.print("Please enter array size (1-20): ");
        int arraySizeTask12 = generalScanner.nextInt();

        System.out.print("Please enter min print config: ");
        int minPrintConfig = generalScanner.nextInt();

        if (arraySizeTask12 < 1 || arraySizeTask12 > 20) {

            System.out.println("Please enter a number between 1 and 20.");

        } else {

            int[][][] arrayTask12 = new int[arraySizeTask12][arraySizeTask12][arraySizeTask12];

            System.out.println("Please enter the arrayTask12 elements: ");

            for (int i = 0; i < arrayTask12.length; i++) {
                for (int j = 0; j < arrayTask12[i].length; j++) {
                    for (int z = 0; z < arrayTask12[i][j].length; z++) {
                        System.out.print("Element [" + i + "][" + j + "][" + z + "]: ");
                        arrayTask12[i][j][z] = generalScanner.nextInt();
                    }
                }
            }

            for (int i = 0; i < arrayTask12.length; i++) {
                for (int j = 0; j < arrayTask12[i].length; j++) {
                    for (int z = 0; z < arrayTask12[i][j].length; z++) {

                        if (arrayTask12[i][j][z] > minPrintConfig) {
                            System.out.print(arrayTask12[i][j][z] + ", ");
                        }

                    }
                }
            }

            System.out.println();
        }


        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 13

        System.out.print("Please enter array size (1-100): ");
        int arraySizeTask13 = generalScanner.nextInt();

        if (arraySizeTask13 < 1 || arraySizeTask13 > 100) {

            System.out.println("Please enter a number between 1 and 100.");

        } else {

            int[] arrayTask13 = new int[arraySizeTask13];

            System.out.println("Please enter the arrayTask13 elements: ");

            for (int i = 0; i < arrayTask13.length; i++) {
                System.out.print("Element [" + i + "]: ");
                arrayTask13[i] = generalScanner.nextInt();
            }

            // Burda bir neçə fərqli sort methodu tətbiq edilə bilər.
            // Amma men en sade ve menim xosladigim methodu isledecem (Bubble Sort).

            for (int i = 0; i < arrayTask13.length - 1; i++) {

                for (int j = 0; j < arrayTask13.length - 1 - i; j++) {

                    if (arrayTask13[j] > arrayTask13[j + 1]) {

                        int temp = arrayTask13[j];

                        arrayTask13[j] = arrayTask13[j + 1];
                        arrayTask13[j + 1] = temp;
                    }
                }
            }

            for (int i = arrayTask13.length - 1; i >= 0; i--) {
                System.out.print(arrayTask13[i] + " ");
            }
        }


        // -----------------------------------------------------------------------------------------


        generalScanner.close();

    }

}
