import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class InterviewTest {
    @Test
    void simple01() {
        // No banned word
        Pair answer = Interview.findMostCommonValidWord("le soleil le soleil le soleil le", new String[] {""});
        Pair expected = new Pair("le", 4);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple02() {
        // 1 banned word
        Pair answer = Interview.findMostCommonValidWord("le soleil le soleil le soleil le", new String[] {"le"});
        Pair expected = new Pair("soleil", 3);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple03() {
        // all words banned
        Pair answer = Interview.findMostCommonValidWord("le soleil le soleil le soleil le", new String[] {"le", "soleil"});
        Pair expected = new Pair(null, null);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple04() {
        // If 2(or more) words have the same frequency, should return one based on alphabetical order
        Pair answer = Interview.findMostCommonValidWord("le la le la le la", new String[] {});
        Pair expected = new Pair("la", 3);

        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple05() {
        // no words phrase
        Pair answer = Interview.findMostCommonValidWord("", new String[] {"le", "soleil"});
        Pair expected = new Pair(null, null);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple06() {
        // result should be in lower case when the word is capital
        Pair answer = Interview.findMostCommonValidWord("LE soleil LE soleil LE soleil", new String[] {"soleil"});
        Pair expected = new Pair("le", 3);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple07() {
        // algorithm should not be case sensitive Ex: LE = Le = lE = le
        Pair answer = Interview.findMostCommonValidWord("LE soleil Le soleil lE soleil le le", new String[] {});
        Pair expected = new Pair("le", 5);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple08() {
        // algorithm should not be case sensitive Ex: LE = Le = lE = le
        Pair answer = Interview.findMostCommonValidWord("LE soleil Le soleil lE soleil le", new String[] {"Le"});
        Pair expected = new Pair("soleil", 3);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple09() {
        // algorithm should ignore banned words not present
        Pair answer = Interview.findMostCommonValidWord("LE soleil Le soleil lE soleil le", new String[] {"squid", "game"});
        Pair expected = new Pair("le", 4);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void simple10() {
        // repeated stop word
        Pair answer = Interview.findMostCommonValidWord("LE soleil Le soleil lE soleil le", new String[] {"Le", "le"});
        Pair expected = new Pair("soleil", 3);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void input0() throws Exception {
        String[][] input = readInput(0);

        Pair answer = Interview.findMostCommonValidWord(input[0][0], input[1]);
        Pair expected = new Pair("vise", 88);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void input1() throws Exception {
        String[][] input = readInput(1);

        Pair answer = Interview.findMostCommonValidWord(input[0][0], input[1]);
        Pair expected = new Pair("backlash", 101);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void input2() throws Exception {
        String[][] input = readInput(2);

        Pair answer = Interview.findMostCommonValidWord(input[0][0], input[1]);
        Pair expected = new Pair("moonlight", 146);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    @Test
    void input3() throws Exception {
        String[][] input = readInput(3);

        Pair answer = Interview.findMostCommonValidWord(input[0][0], input[1]);
        Pair expected = new Pair("basketball", 4720);
        assertEquals(expected.first, answer.first);
        assertEquals(expected.second, answer.second);
    }

    String[][] readInput(Integer inputNumber) throws IOException {
        String fileName = String.format("./src/test/resources/input%d.txt", inputNumber);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String[] phrase = new String[]{reader.readLine()};
        String[] banned = reader.readLine().split(" ");
        return new String[][] {phrase, banned};
    }
}
