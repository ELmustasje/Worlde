package wordle.model.word;

import no.uib.inf102.wordle.model.Dictionary;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAnswerTest {

    private Random random = new Random();

    private Dictionary dictionary = new Dictionary();

    private final String LEGAL_WORD = "arise";

    @Test
    public void allCorrectFeedback() {
        WordleAnswer answer = new WordleAnswer(LEGAL_WORD, dictionary);

        WordleWord feedback = answer.makeGuess(LEGAL_WORD);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.CORRECT, c.answerType);
        }
    }

    @Test
    public void allWrongPositionFeedback() {
        String answerString = "coast";
        String wrongPositionAnswerString = "tacos";
        WordleAnswer answer = new WordleAnswer(answerString, dictionary);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.MISPLACED, c.answerType);
        }
    }

    @Test
    public void allWrongFeedback() {
        String answerString = "coast";
        String wrongPositionAnswerString = "hurry";
        WordleAnswer answer = new WordleAnswer(answerString, dictionary);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        for (WordleCharacter c : feedback) {
            assertEquals(AnswerType.WRONG, c.answerType);
        }
    }

    @Test
    public void partialCorrectFeedback() {
        String answerString = "carry";
        String wrongPositionAnswerString = "hurry";
        WordleAnswer answer = new WordleAnswer(answerString, dictionary);

        WordleWord feedback = answer.makeGuess(wrongPositionAnswerString);
        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i < 2) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            else {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
    }

    @Test
    public void correctSubstitutesWrongPosition() {
        WordleAnswer answer = new WordleAnswer("beast", dictionary);
        WordleWord feedback = answer.makeGuess("adapt");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType, "There is an 'a' in the answer, but it is located later at index 2. This 'a' should be WRONG.");
            }
            if (i == 1) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
    }

    @Test
    public void isPossibleWordRocks() {
        WordleAnswer answer = new WordleAnswer("rocks", dictionary);
        WordleWord feedback = answer.makeGuess("sores");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType, "The first 's' should be WRONG. There is only one 's' in 'rocks'. The second 's' should be CORRECT.");
            }
            if (i == 1) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.MISPLACED, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("rocks", feedback));
        assertFalse(WordleWord.isPossibleWord("sores", feedback));
    }

    @Test
    public void isPossibleWordPoppyGuess() {
        WordleAnswer answer = new WordleAnswer("upper", dictionary);
        WordleWord feedback = answer.makeGuess("poppy");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.MISPLACED, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("upper", feedback));
        assertFalse(WordleWord.isPossibleWord("poppy", feedback));
    }

    @Test
    public void isPossibleWordApoopGuess() {
        WordleAnswer answer = new WordleAnswer("poppy", dictionary);
        WordleWord feedback = answer.makeGuess("apoop");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.MISPLACED, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.MISPLACED, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.MISPLACED, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("poppy", feedback));
        assertFalse(WordleWord.isPossibleWord("apoop", feedback));
    }

     @Test
    public void isPossibleWordMommy() {
        WordleAnswer answer = new WordleAnswer("mommy", dictionary);
        WordleWord feedback = answer.makeGuess("money");

        int i = 0;
        for (WordleCharacter c : feedback) {
            if (i == 0) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 1) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            if (i == 2) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 3) {
                assertEquals(AnswerType.WRONG, c.answerType);
            }
            if (i == 4) {
                assertEquals(AnswerType.CORRECT, c.answerType);
            }
            i++;
        }
        assertTrue(WordleWord.isPossibleWord("mommy", feedback));
        assertTrue(WordleWord.isPossibleWord("mossy", feedback));
    }

    @Test
    public void canCreateLegalWords() {
        for (String legalAnswerWord : dictionary.getAnswerWordsList()) {
            assertDoesNotThrow(() -> new WordleAnswer(legalAnswerWord, dictionary), "This word was not legal: " + legalAnswerWord);
        }
    }

    
    @Test
    public void cannotGuessNonsenseWords() {
        WordleAnswer answer = new WordleAnswer(LEGAL_WORD, dictionary);
        for (int i = 0; i < 1000; i++) {
            assertThrows(IllegalArgumentException.class, () -> answer.makeGuess(createNonsenseWord()));
        }
    }

    /**
     * Creates a word with a random jumble of characters
     * 
     * @return nonsense word
     */
    private String createNonsenseWord() {
        int a = 97;
        int z = 122;
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dictionary.WORD_LENGTH; i++) {
                char c = (char) random.nextInt(a, z);
                sb.append(c);
            }
            String word = sb.toString();
            if (!dictionary.getGuessWordsList().contains(word))
                return word;
        }
    }


}
