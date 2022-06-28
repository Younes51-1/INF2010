import Alphabet.Alphabet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlphabetTest {
    @Test
    void simpleEnglishLanguage() {
        String[] simpleEnglish = {"arc", "are", "ark", "bark", "cake", "car", "ear"};
        Character[] alphabet = {'a', 'b', 'c', 'e', 'k', 'r'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(simpleEnglish).toArray());
    }

    @Test
    void Language1() {
        String[] language1 = {"baa", "abcd", "abca", "cab", "cad"};
        Character[] alphabet = {'b', 'd', 'a', 'c'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(language1).toArray());
    }

    @Test
    void Language2() {
        String[] language2 = {"caa", "aaa", "aab"};
        Character[] alphabet = {'c', 'a', 'b'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(language2).toArray());
    }

    @Test
    void Language3() {
        String[] language3 = {"wrt", "wrf", "er", "ett", "rftt"};
        Character[] alphabet = {'w', 'e', 'r', 't', 'f'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(language3).toArray());
    }

    @Test
    void Language4() {
        String[] language4 = {"!!k", "!dtge", "!stge", "s!k", "sd!", "st!", "ttt", "ttk", "gk!", "eet", "ekt"};
        Character[] alphabet = {'!', 'd', 's', 't', 'g', 'e', 'k'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(language4).toArray());
    }

    @Test
    void Language5() {
        String[] language5 = {"]?4", "]]b", "{4?", "{4{", "4?{", "4?b", "b]]"};
        Character[] alphabet = {'?', ']', '{', '4', 'b'};
        assertArrayEquals(alphabet, Alphabet.lexicalOrder(language5).toArray());
    }
}
