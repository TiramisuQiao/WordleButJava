import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class WordList {
    /**
     * The maximum number of words in the word list.
     */
    private static final int MAX_WORDS = 100010;
    /**
     * The length of the word list.
     * This variable represents the number of words in the word list.
     * The word list is used in various methods for generating random words,
     * querying word frequencies, and inserting words into a trie data structure.
     */
    private static final int WORD_LIST_LENGTH = 14855;
    private static int[][] wordListTrie = new int[MAX_WORDS][26];
    /**
     * This variable represents the frequency of words in a word list.
     * The index of the array corresponds to the position of a word in the word list.
     * The value at each index represents the frequency of that word.
     */
    private static int[] wordFrequency = new int[MAX_WORDS];
    /**
     * Represents an index of a word in a word list.
     * The wordIndex variable is used to keep track of the index of a word in a word list. It is a private static variable,
     * meaning it is associated with the WordList class itself rather than with any specific instance of the class. This
     * allows easy access to the wordIndex across different methods within the WordList class.
     *     * The initial value of wordIndex is 0, indicating that the first word in the word list has an index of 0. As words are
     * read and inserted into the wordListTrie data structure, the wordIndex is incremented to maintain the correct index
     * for the next word.
     */
    private static int wordIndex = 0;

    /**
     * Reads words from a file and stores them in an array.
     *
     * @param pathname the path to the file
     * @return an array of words read from the file
     */
    private static String[] readWord(String pathname) {
        String[] words = new String[WORD_LIST_LENGTH];
        int index = 0;

        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine() && index < words.length) {
                String word = scanner.nextLine().trim();
                if (word.length() == 5) {
                    words[index++] = word;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * Generates a random word from a word list.
     *
     * @return a random word
     */
    public static String RandomWord() {
        String[] words = readWord("wordlist.txt");
        Random random = new Random();
        int index = random.nextInt(WORD_LIST_LENGTH);
        String answerWord = words[index];
        if (answerWord.isEmpty()) {
            return RandomWord();
        }
        return answerWord;

    }

    /**
     * Inserts a string into the wordListTrie data structure.
     *
     * @param str the string to be inserted
     */
    private static void insert(char[] str) {
        int p = 0;
        for (char c : str) {
            int u = c - 'a';
            if (wordListTrie[p][u] == 0) {
                wordListTrie[p][u] = ++wordIndex;
            }
            p = wordListTrie[p][u];
        }
        wordFrequency[p]++;
    }

    /**
     * Queries the wordListTrie data structure to determine the frequency of a given word.
     *
     * @param str the word to be queried
     * @return the frequency of the given word, or 0 if the word is not present in the data structure
     */
    private static int query(char[] str) {
        int p = 0;
        for (char c : str) {
            int u = c - 'a';
            if (wordListTrie[p][u] == 0) {
                return 0;
            }
            p = wordListTrie[p][u];
        }
        return wordFrequency[p];
    }

    /**
     * Loads the game by inserting words from a word list file into the wordListTrie data structure.
     * The word list file should contain words of length 5.
     * The word list file should be named "wordlist.txt" and be located in the same directory as the code.
     *
     * @since 1.0
     */
    public static void gameLoader() {
        String[] words = readWord("wordlist.txt");
        for (int i = 0; i < WORD_LIST_LENGTH; i = i + 1) {
            insert(words[i].toCharArray());
        }
    }

    /**
     * Determines whether the user input is a valid word or not by querying the wordListTrie data structure.
     *
     * @param userInput the user input
     * @return true if the user input is a valid word, false otherwise
     */
    public static boolean cyberJudge(String userInput) {
        char[] str = userInput.toCharArray();
        if (query(str) >= 1) {
            return true;
        }
        return false;
    }

}
