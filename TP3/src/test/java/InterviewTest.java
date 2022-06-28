import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterviewTest {

    @Test
    public void iterativeLinearNoElements() {
        Integer[] numbers = {};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertNull(answer);
    }

    @Test
    public void iterativeLinearOnly1Element() {
        Integer[] numbers = {5};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertEquals(5, answer);
    }

    @Test
    public void iterativeLinearAnswerInMiddle() {
        Integer[] numbers = {0, 0, 1, 1, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertEquals(4, answer);
    }

    @Test
    public void iterativeLinearAnswerInBeginning() {
        Integer[] numbers = {0, 1, 1, 4, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertEquals(0, answer);
    }

    @Test
    public void iterativeLinearAnswerInEnd() {
        Integer[] numbers = {0, 0, 1, 1, 3, 3, 5, 5, 55};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertEquals(55, answer);
    }

    @Test
    public void iterativeLinearLargeArray() {
        Integer[] numbers = {0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14, 16, 16, 18, 18, 20, 20, 22, 22, 23, 24, 24, 26, 26, 28, 28, 30, 30, 32, 32, 34, 34, 36, 36, 38, 38, 40, 40, 42, 42, 44, 44, 46, 46, 48, 48, 50, 50, 52, 52, 54, 54, 56, 56, 58, 58, 60, 60, 62, 62, 64, 64, 66, 66, 68, 68};
        Integer answer = Interview.findNonDuplicateIterativeLinear(numbers);
        assertEquals(23, answer);
    }

    @Test
    public void iterativeNoElements() {
        Integer[] numbers = {};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertNull(answer);
    }

    @Test
    public void iterativeOnly1Element() {
        Integer[] numbers = {5};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertEquals(5, answer);
    }

    @Test
    public void iterativeAnswerInMiddle() {
        Integer[] numbers = {0, 0, 1, 1, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertEquals(4, answer);
    }

    @Test
    public void iterativeAnswerInBeginning() {
        Integer[] numbers = {0, 1, 1, 4, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertEquals(0, answer);
    }

    @Test
    public void iterativeAnswerInEnd() {
        Integer[] numbers = {0, 0, 1, 1, 3, 3, 5, 5, 55};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertEquals(55, answer);
    }

    @Test
    public void iterativeLargeArray() {
        Integer[] numbers = {0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14, 16, 16, 18, 18, 20, 20, 22, 22, 23, 24, 24, 26, 26, 28, 28, 30, 30, 32, 32, 34, 34, 36, 36, 38, 38, 40, 40, 42, 42, 44, 44, 46, 46, 48, 48, 50, 50, 52, 52, 54, 54, 56, 56, 58, 58, 60, 60, 62, 62, 64, 64, 66, 66, 68, 68};
        Integer answer = Interview.findNonDuplicateIterative(numbers);
        assertEquals(23, answer);
    }

    @Test
    public void RecursiveLinearNoElements() {
        Integer[] numbers = {};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertNull(answer);
    }

    @Test
    public void RecursiveOnly1Element() {
        Integer[] numbers = {5};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertEquals(5, answer);
    }

    @Test
    public void RecursiveAnswerInMiddle() {
        Integer[] numbers = {0, 0, 1, 1, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertEquals(4, answer);
    }

    @Test
    public void RecursiveInBeginning() {
        Integer[] numbers = {0, 1, 1, 4, 4, 5, 5, 6, 6};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertEquals(0, answer);
    }

    @Test
    public void RecursiveAnswerInEnd() {
        Integer[] numbers = {0, 0, 1, 1, 3, 3, 5, 5, 55};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertEquals(55, answer);
    }

    @Test
    public void RecursiveLargeArray() {
        Integer[] numbers = {0, 0, 2, 2, 4, 4, 6, 6, 8, 8, 10, 10, 12, 12, 14, 14, 16, 16, 18, 18, 20, 20, 22, 22, 23, 24, 24, 26, 26, 28, 28, 30, 30, 32, 32, 34, 34, 36, 36, 38, 38, 40, 40, 42, 42, 44, 44, 46, 46, 48, 48, 50, 50, 52, 52, 54, 54, 56, 56, 58, 58, 60, 60, 62, 62, 64, 64, 66, 66, 68, 68};
        Integer answer = Interview.findNonDuplicateRecursive(numbers);
        assertEquals(23, answer);
    }
}