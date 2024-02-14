import java.util.Scanner;
import java.util.regex.Pattern;
import java.time.Instant;
import java.time.Duration;

public class Game {
    public static void main(String[] args) {
        WordList.gameLoader();
        showWelcomeStr();
        while (getUserChoice()) {
            GameBody();
        }
        System.out.println("Bye!");
    }
    /**
     * Represents the main game logic of the Wordle game.
     * This method allows the user to play the Wordle game, where they have 6 attempts to guess a 5-letter word.
     * The method prompts the user for their guess, checks its validity, and provides feedback based on the correctness of the guess.
     * If the user guesses the word within the allowed attempts, a success message is displayed along with the time taken to guess.
     * If the user exceeds the attempts, a failure message is displayed along with the correct word and the time taken to guess.
     */
    private static void GameBody(){
        showGameStart();
        int count = 0;
        String answerWord = GenerateRandomWord();
        Instant timerStart = Instant.now();
        while (true) {
            gameCountPrompt(count);
            String userWord = readUserInput();
            int isValid = JudgeWord(userWord, answerWord);
            if (isValid == 1) {
                Instant timerEnd = Instant.now();
                Duration timeElapsed = Duration.between(timerStart, timerEnd);
                showSuccessWord(count, timeElapsed);
                showWelcomeStr();
                break;
            } else if (isValid == 0) {
                System.out.println();
            } else {
                int[] TrueRecord;
                int[] InsideRecord;
                TrueRecord = CheckLetterTrue(userWord, answerWord);
                InsideRecord = CheckLetterInside(userWord, answerWord, TrueRecord);
                showResultInRound(userWord, TrueRecord, InsideRecord, count);
                count = count + 1;
                if (count == 6) {
                    Instant timerEnd = Instant.now();
                    Duration timeElapsed = Duration.between(timerStart, timerEnd);
                    showFailedWord(answerWord, timeElapsed);
                    showWelcomeStr();
                    break;
                }
            }
        }
    }
    /**
     * Displays the welcome message for the Wordle game.
     */
    private static void showWelcomeStr() {
        System.out.println("=======================================");
        System.out.println("Welcome to Wordle ! What you want to do ?");
        System.out.println("=======================================");
        System.out.println("( 1 ) Play");
        System.out.println("( 2 ) Quit");
    }

    /**
     * Retrieves the user's choice from the console.
     * The method prompts the user for input and validates the choice.
     * If the choice is valid (either "1" or "2"), the method returns true.
     * If the choice is invalid, the method displays an error message and prompts for input again.
     *
     * @return true if the user chooses to play the game, false if they choose to quit
     */
    private static boolean getUserChoice() {
        String number;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            number = scanner.nextLine();
            switch (number) {
                case "1":
                    return true;
                case "2":
                    return false;
                default:
                    System.out.println("Sorry ! Please input the number 1 or 2");
                    showWelcomeStr();
            }
        }

    }

    /**
     * Reads the user input from the console and returns it as a lowercase string.
     *
     * @return the user input as a lowercase string
     */
    private static String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        String InputWord;
        InputWord = scanner.nextLine();
        return InputWord.toLowerCase();

    }

    /**
     * Generates a random word from a word list.
     *
     * @return a randomly selected word from the word list
     */
    private static String GenerateRandomWord() {
// For Debug Use:
//        String word;
//        word = WordList.RandomWord();
//        System.out.println(word);
//        return word;
        return WordList.RandomWord();
    }

    /**
     * Displays the instructions and start message for the game.
     */
    private static void showGameStart() {
        System.out.println("Instruction");
        System.out.println("The [letter] shows it is the letter in right position.");
        System.out.println("The (letter) shows it is the right letter in wrong position.");
        System.out.println("Otherwise, the letter is not in the word.");
        System.out.println("=======================================");
        System.out.println("Let ’ s go ! You have 6 attempts to guess !");
        System.out.println("=======================================");
    }

    /**
     * Checks each letter of the input word against the corresponding letter of the answer word
     * and records whether the letters match or not.
     *
     * @param InputWord The word entered by the user.
     * @param AnswerWord The correct word to compare against the user's input.
     * @return An array of integers representing the match record for each letter.
     *         A value of 1 indicates a match, while a value of 0 indicates a mismatch.
     */
    private static int[] CheckLetterTrue(String InputWord, String AnswerWord) {
        int[] TrueRecord = new int[5];
        for (int i = 0; i < 5; i = i + 1) {
            TrueRecord[i] = 0;
            if (InputWord.charAt(i) == AnswerWord.charAt(i)) {
                TrueRecord[i] = 1;
            }
        }

        return TrueRecord;
    }

    /**
     * Checks each letter of the input word against the corresponding letter of the answer word
     * and records whether the letters match or not.
     *
     * @param InputWord The word entered by the user.
     * @param AnswerWord The correct word to compare against the user's input.
     * @param TrueRecord An array of integers representing the match record for each letter.
     *                  A value of 1 indicates a match, while a value of 0 indicates a mismatch.
     * @return An array of integers representing whether each letter of the input word is inside the answer word.
     *         A value of 1 indicates the letter is inside, while a value of 0 indicates it is not inside.
     */
    private static int[] CheckLetterInside(String InputWord, String AnswerWord, int[] TrueRecord) {
        int[] InsideRecord = new int[5];
        for (int i = 0; i < 5; i = i + 1) {
            if (TrueRecord[i] == 1) {
                continue;
            }
            for (int j = 0; j < 5; j = j + 1) {
                if (i == j) {
                    continue;
                }
                if (InputWord.charAt(i) == AnswerWord.charAt(j)) {
                    InsideRecord[i] = 1;
                    break;
                }
            }
        }
        return InsideRecord;
    }

    /**
     * Shows the result of a round in the Wordle game.
     *
     * @param InputWord     The word entered by the user.
     * @param TrueRecord    An array of integers representing the match record for each letter.
     *                      A value of 1 indicates a match, while a value of 0 indicates a mismatch.
     * @param InsideRecord  An array of integers representing whether each letter of the input word is inside the answer word.
     *                      A value of 1 indicates the letter is inside, while a value of 0 indicates it is not inside.
     * @param count         The current round count.
     */
    private static void showResultInRound(String InputWord, int[] TrueRecord, int[] InsideRecord, int count) {
        gameCountPrompt(count);
        for (int i = 0; i < 5; i = i + 1) {
            if (TrueRecord[i] == 1) {
                System.out.print("[");
                System.out.print(InputWord.charAt(i));
                System.out.print("]");
                System.out.print(" ");
            } else if (InsideRecord[i] == 1) {
                System.out.print("(");
                System.out.print(InputWord.charAt(i));
                System.out.print(")");
                System.out.print(" ");
            } else {
                System.out.print(" ");
                System.out.print(InputWord.charAt(i));
                System.out.print(" ");
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * Displays the current game count prompt.
     *
     * @param count The current game count.
     */
    private static void gameCountPrompt(int count) {
        count = count + 1;
        System.out.print("[ " + count + " / " + "6 ]");
    }

    /**
     * Checks the validity of an input word and compares it to an answer word.
     *
     * @param InputWord   The word entered by the user.
     * @param AnswerWord  The correct word to compare against the user's input.
     * @return 0 if the input word is invalid, 1 if the input word matches the answer word,
     *         -1 if the input word is valid but doesn't match the answer word.
     */
    private static int JudgeWord(String InputWord, String AnswerWord) {
        if (InputWord.contains(" ")) {
            System.out.println("No blank character !");
            return 0;
        }
        if (!Pattern.matches("[a-zA-Z]+", InputWord)) {
            System.out.println("Not A WORD ! Please just input the English word !");
            return 0;
        }
        if (InputWord.length() < 5) {
            System.out.println("Too short word ! ");
            return 0;
        }
        if (InputWord.length() > 5) {
            System.out.println("Too long word !");
            return 0;
        }
        if (!WordList.cyberJudge(InputWord)) {
            System.out.println("Not a Valid Word!");
            return 0;
        }
        if (InputWord.equals(AnswerWord)) {
            return 1;
        }

        return -1;
    }

    /**
     * Displays a message indicating that the user failed to guess the word.
     *
     * @param AnswerWord   The correct word that the user failed to guess.
     * @param timeElapsed  The duration it took for the user to play the game.
     */
    private static void showFailedWord(String AnswerWord, Duration timeElapsed) {
        System.out.println("Oooops ! It seems you can not guess the word !");
        System.out.println("The answer is : " + AnswerWord);
        showTimeUse(timeElapsed);
        System.out.println("Be confident ! Try another game !");
    }

    /**
     * Displays a success message along with the time taken to guess the word.
     *
     * @param count       The current round count.
     * @param timeElapsed The duration it took for the user to guess the word.
     */
    private static void showSuccessWord(int count, Duration timeElapsed) {
        count = count + 1;
        System.out.println("Very Good!");
        System.out.println("This is your grade: ");
        System.out.println("Tries: " + count);
        showTimeUse(timeElapsed);
    }

    /**
     * Displays the time elapsed in a user-friendly format.
     *
     * @param timeElapsed The duration representing the time elapsed.
     */
    private static void showTimeUse(Duration timeElapsed) {
        long hours = timeElapsed.toHours();
        timeElapsed = timeElapsed.minusHours(hours);
        long minutes = timeElapsed.toMinutes();
        timeElapsed = timeElapsed.minusMinutes(minutes);
        long seconds = timeElapsed.getSeconds();
        if (hours == 0 & minutes == 0) {
            System.out.printf("Time Use: %d s%n", seconds);
        } else if (hours == 0) {
            System.out.printf("Time Use: %d min %d s%n", minutes, seconds);
        } else {
            System.out.printf("Time Use: %d h %d min %d s%n", hours, minutes, seconds);
        }
    }
}
