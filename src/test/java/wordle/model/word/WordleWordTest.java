package wordle.model.word;

import no.uib.inf102.wordle.model.Dictionary;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static no.uib.inf102.wordle.model.word.AnswerType.*;
import static no.uib.inf102.wordle.model.word.AnswerType.WRONG;
import static org.junit.jupiter.api.Assertions.*;

public class WordleWordTest {

    private Dictionary dictionary = new Dictionary();
    private final List<String> WORDLE_WORDS = dictionary.getGuessWordsList();
    private Random random = new Random();

    @Test
    public void createFullWord() {
        AnswerType[] feedback = { WRONG, CORRECT, WRONG, MISPLACED, WRONG };
        String word = "arise";
        WordleWord wordleWord = new WordleWord(word, feedback);
        int letterCount = 0;
        for (WordleCharacter wordleChar : wordleWord) {
            letterCount++;
        }
        assertEquals(word.length(), letterCount, "The number of letters in the word was wrong.");
        assertEquals(word, wordleWord.getWordString(),
                "The word returned by getWordString() must be the same as what was passed to the constructor.");
    }

    @Test
    public void sameLength() {
        String word = "hello";
        AnswerType[] shortFeedback = { WRONG, CORRECT, MISPLACED, WRONG };
        assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, shortFeedback));
        AnswerType[] longFeedback = { WRONG, WRONG, CORRECT, MISPLACED, WRONG, CORRECT };
        assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, longFeedback));
    }

    @Test
    public void noBlank() {
        String word = "hello";
        AnswerType[] feedback = { WRONG, CORRECT, BLANK, MISPLACED, WRONG };
        assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, feedback));
    }

    @Test
    public void noNull() {
        String word = "hello";
        AnswerType[] feedback = { WRONG, CORRECT, null, MISPLACED, WRONG };
        assertThrows(IllegalArgumentException.class, () -> new WordleWord(word, feedback));
    }

    @Test
    public void nonsenseWordsAreIllegal() {
        AnswerType[] feedback = { WRONG, CORRECT, WRONG, MISPLACED, WRONG };
        for (int i = 0; i < 1000; i++) {
            WordleWord word = new WordleWord(createNonsenseWord(), feedback);
            assertFalse(dictionary.isLegalGuess(word.getWordString()));
        }
    }

    /**
     * Creates a word with a random jumble of characters
     * 
     * @return nonsense word
     */
    private String createNonsenseWord() {
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dictionary.WORD_LENGTH; i++) {
                char c = (char) random.nextInt('a', 'z' + 1);
                sb.append(c);
            }
            String word = sb.toString();
            if (!WORDLE_WORDS.contains(word))
                return word;
        }
    }

    @Test
    public void matchAll() {
        String word = "graph";
        AnswerType[] correctFeedback = { CORRECT, CORRECT, CORRECT, CORRECT, CORRECT };
        AnswerType[] wrongFeedback = { CORRECT, CORRECT, WRONG, CORRECT, CORRECT };
        WordleWord correct = new WordleWord(word, correctFeedback);
        assertTrue(correct.allMatch());
        WordleWord wrong = new WordleWord(word, wrongFeedback);
        assertFalse(wrong.allMatch());
    }

    @Test
    public void testContains() {
        String word = "hello";
        AnswerType[] feedback = { WRONG, CORRECT, CORRECT, MISPLACED, WRONG };
        WordleWord wordleWord = new WordleWord(word, feedback);
        assertTrue(wordleWord.contains('h'));
        assertTrue(wordleWord.contains('e'));
        assertTrue(wordleWord.contains('l'));
        assertTrue(wordleWord.contains('o'));
        assertFalse(wordleWord.contains('a'));
        assertFalse(wordleWord.contains('c'));
        assertFalse(wordleWord.contains('f'));
        assertFalse(wordleWord.contains('z'));
        assertFalse(wordleWord.contains(' '));
    }

    @Test
    public void testEquals() {
        String word = "hello";
        AnswerType[] correctFeedback = { CORRECT, CORRECT, CORRECT, CORRECT, CORRECT };
        AnswerType[] wrongFeedback = { MISPLACED, CORRECT, WRONG, CORRECT, WRONG };
        WordleWord correct = new WordleWord(word, correctFeedback);
        WordleWord wrong = new WordleWord(word, wrongFeedback);
        assertTrue(correct.equals(new WordleWord(word, correctFeedback)));
        assertTrue(wrong.equals(new WordleWord(word, wrongFeedback)));
        assertFalse(correct.equals(wrong));
        assertTrue(wrong.equals(wrong));
        assertTrue(correct.equals(correct));
        assertFalse(correct.equals(null));
        assertFalse(correct.equals(word));
    }
}
