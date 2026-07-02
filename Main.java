import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner generalScanner = new Scanner(System.in);

        // -----------------------------------------------------------------------------------------
        // Task 1: Palindrome Check

        System.out.print("Please enter a text to check palindrome: ");

        String palindromeCheckText = generalScanner.nextLine();

        System.out.println(isTextPalindrome(palindromeCheckText));

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 2: Group Anagrams

        String[] anagramArray = readStringArray(generalScanner);

        String[][] groupedAnagrams = groupByAnagram(anagramArray);

        System.out.println(Arrays.deepToString(groupedAnagrams));

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 3: Longest Common Prefix

        String[] mostLongestPrefixArray = readStringArray(generalScanner);

        String mostLongestPrefix = getMostLongestPrefixByArray(mostLongestPrefixArray);

        System.out.println("Most longest prefix: " + mostLongestPrefix);

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 4: Longest Unique Substring

        System.out.print("Please enter a text to get most longest unique substring: ");
        String substringInput = generalScanner.nextLine();

        String mostLongestRepeatedWord = getLongestUniqueSubstring(substringInput);

        System.out.println("Most longest unique substring: " + mostLongestRepeatedWord + " and count: " + mostLongestRepeatedWord.length());

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 5: Run-Length Encoding / Decoding

        // Encode

        System.out.print("Please enter a text to encode by run length: ");
        String runLenDecodedText = generalScanner.nextLine();

        String runLenEncodedTextProcessed = encodeTextByRunLength(runLenDecodedText);

        System.out.println("Run-Length Encoded Text: " + runLenEncodedTextProcessed);

        // Decode

        System.out.print("Please enter a text to decode by run length: ");
        String runLenEncodedText = generalScanner.nextLine();

        String runLenDecodedTextProcessed = decodeTextByRunLength(runLenEncodedText);

        System.out.println("Run-Length Decoded Text: " + runLenDecodedTextProcessed);

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 6: Letter Counting Map

        System.out.print("Please enter a text to counting by letters: ");

        String countLettersText = generalScanner.nextLine();

        String[][] counted2DArray = getLetterCountMap(countLettersText);

        System.out.println(Arrays.deepToString(counted2DArray));

        // -----------------------------------------------------------------------------------------


        // -----------------------------------------------------------------------------------------
        // Task 7: Filter Words by Prefix

        System.out.print("Please enter the prefix: ");
        String prefix = generalScanner.nextLine();

        String[] prefixFilteredArray = readStringArray(generalScanner);
        String[] filteredArrayByPrefix = getWordsByPrefix(prefixFilteredArray, prefix);

        System.out.println(Arrays.deepToString(filteredArrayByPrefix));

        // -----------------------------------------------------------------------------------------


        generalScanner.close();

    }

    public static boolean isTextPalindrome(String palindromeCheckText) {

        String palindromeCheckTextCleaned = palindromeCheckText
                .toLowerCase()
                .replace(",", "")
                .replace(".", "")
                .replace("!", "")
                .replace("?", "")
                .replace(";", "")
                .replace(":", "")
                .replace(" ", "");

        int palindromeCheckTextLength = palindromeCheckTextCleaned.length();

        for (int i = 0; i < palindromeCheckTextLength / 2; i++) {

            if (palindromeCheckTextCleaned.charAt(i) != palindromeCheckTextCleaned.charAt(palindromeCheckTextLength - i - 1)) {
                return false;
            }

        }

        return true;
    }

    public static String[][] groupByAnagram(String[] anagrams) {

        if (anagrams == null || anagrams.length == 0) {
            return new String[0][0];
        }

        // burda 2 ferqli yanasma ile etmek olur optimalliq ucun, ya verilmis array pozaraq yəni visit olanda eyer qruplanirsa bunu silirik arrayden ki tekrar muraciet olmasin,
        // ve ya menim indi etdiyim yanasma ile visited map metodu, bu usulda array modify olmur ve optimizasiyada qorunur.
        boolean[] visited = new boolean[anagrams.length];

        String[][] result = new String[anagrams.length][];
        int groupCount = 0;

        for (int i = 0; i < anagrams.length; i++) {

            if (visited[i]) {
                continue;
            }

            String[] tempGroup = new String[anagrams.length];
            int wordCount = 0;

            tempGroup[wordCount++] = anagrams[i];
            visited[i] = true; // cari iterasiyada muraciet

            for (int j = i + 1; j < anagrams.length; j++) {

                if (!visited[j] && isAnagramIgnoreCase(anagrams[i], anagrams[j])) {
                    tempGroup[wordCount++] = anagrams[j];
                    visited[j] = true; // qrupa salinibsa muraciet olunub demek
                }

            }

            result[groupCount++] = Arrays.copyOf(tempGroup, wordCount); // gereksiz yaddas istifadesini buraxmaq ucun
        }

        return Arrays.copyOf(result, groupCount);
    }

    public static boolean isAnagramIgnoreCase(String text1, String text2) {

        if (text1 == null || text2 == null) {
            return false;
        }

        if (text1.length() != text2.length()) {
            return false;
        }

        char[] sortedChars1 = text1.toLowerCase().toCharArray();
        char[] sortedChars2 = text2.toLowerCase().toCharArray();

        Arrays.sort(sortedChars1);
        Arrays.sort(sortedChars2);

        return Arrays.equals(sortedChars1, sortedChars2);
    }

    public static String[] readStringArray(Scanner scanner) {
        return readStringArray(scanner, "Please enter a number of massive: ");
    }

    public static String[] readStringArray(Scanner scanner, String message) {

        System.out.print(message);

        int length = scanner.nextInt();

        if (length < 0) {
            return new String[0];
        }

        scanner.nextLine();

        String[] array = new String[length];

        for (int i = 0; i < length; i++) {
            System.out.print("Please enter word [" + i + "]: ");
            array[i] = scanner.nextLine();
        }

        return array;
    }

    public static String getMostLongestPrefixByArray(String[] wordArray) {

        if (wordArray == null || wordArray.length == 0) {
            return "";
        }

        if (wordArray.length == 1) {
            return wordArray[0];
        }

        String firstWord = wordArray[0];

        // Burada char ile edilen index yoxlamasi startWith methodu ile de edile bilerdi eslinde,
        // amma men char massiv uzerinden yoxlamaq daha optimal oldugunu dusunduyum ucun bu forma etdim.

        for (int i = 0; i < firstWord.length(); i++) {

            char currentChar = firstWord.charAt(i);

            for (int j = 1; j < wordArray.length; j++) {

                if (i >= wordArray[j].length() || wordArray[j].charAt(i) != currentChar) {
                    return firstWord.substring(0, i);
                }
            }
        }

        return firstWord;
    }

    public static String getLongestUniqueSubstring(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder longestSubstring = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            StringBuilder currentSubstring = new StringBuilder();

            for (int j = i; j < text.length(); j++) {

                char currentChar = text.charAt(j);

                // iki for daxilinde indexOf istifade etmek o(n^3) zaman notasiyasi yaradir, ancaq hazirki kecilenler ile bu forma ede bilirem
                if (currentSubstring.indexOf(String.valueOf(currentChar)) != -1) {
                    break;
                }

                currentSubstring.append(currentChar);

                // conditionlarda bildirilmemisdi ona gore men ilk tapdigimi saxlayacam
                // əgər uzunlugu 3 olan 2 ferqli ardicilliq varsa bunlardan ilki ile davam edecem

                if (currentSubstring.length() > longestSubstring.length()) {
                    longestSubstring = currentSubstring;
                }
            }
        }

        return longestSubstring.toString();
    }

    public static String encodeTextByRunLength(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder encodedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            int counter = 1;
            char currentChar = text.charAt(i);

            while (i + 1 < text.length() && currentChar == text.charAt(i + 1)) {
                counter++;
                i++;
            }

            encodedText.append(currentChar);
            encodedText.append(counter);

        }

        return encodedText.toString();
    }

    public static String decodeTextByRunLength(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);

            if (!Character.isLetter(ch)) {
                continue;
            }

            int j = i + 1;

            StringBuilder num = new StringBuilder();

            while (j < text.length() && Character.isDigit(text.charAt(j))) {
                num.append(text.charAt(j));
                j++;
            }

            result.append(String
                    .valueOf(ch)
                    .repeat(Integer.parseInt(num.toString()))
            );

            i = j - 1;
        }

        return result.toString();
    }

    public static String[][] getLetterCountMap(String text) {

        if (text == null || text.isEmpty()) {
            return new String[0][0];
        }

        // regex kecmemisik ona gore replace methodu ile temizleyirem
        // regexleri kecenden sonra bunlari replaceAll ve regex istifadesi ile deyismek olar
        char[] letters = text.toLowerCase()
                .replace(" ", "")
                .replace(",", "")
                .replace(".", "")
                .replace("!", "")
                .replace("?", "")
                .replace(":", "")
                .replace(";", "")
                .toCharArray();

        Arrays.sort(letters);

        String[][] result = new String[letters.length][2];
        int groupCount = 0;

        for (int i = 0; i < letters.length; i++) {

            char current = letters[i];
            int count = 1;

            while (i + 1 < letters.length && letters[i] == letters[i + 1]) {
                count++;
                i++;
            }

            result[groupCount][0] = String.valueOf(current);
            result[groupCount][1] = String.valueOf(count);

            groupCount++;
        }

        return Arrays.copyOf(result, groupCount);
    }

    public static String[] getWordsByPrefix(String[] wordsArray, String prefix) {

        if (wordsArray == null || wordsArray.length == 0) {
            return new String[0];
        }

        String[] filteredArray = new String[wordsArray.length];
        int count = 0;

        for (String word : wordsArray) {

            if (word != null && word.startsWith(prefix)) {
                filteredArray[count++] = word;
            }
        }

        return Arrays.copyOf(filteredArray, count);
    }
}