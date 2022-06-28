import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterviewTest {
    @Test
    void empty() {
        assertEquals(0, Interview.lastBox(new int[]{}));
    }

    @Test
    void simple() {
        assertEquals(0, Interview.lastBox(new int[]{1,1}));
    }

    @Test
    void case0() throws IOException {
        assertEquals(0, Interview.lastBox(new int[]{3,2,1,4}));
    }

    @Test
    void case1() throws IOException {
        assertEquals(1, Interview.lastBox(new int[]{5,6,7,8,10,11}));
    }

    @Test
    void case2() throws IOException {
        assertEquals(4, Interview.lastBox(new int[]{11,7}));
    }

    @Test
    void case3() throws IOException {
        assertEquals(100, Interview.lastBox(new int[]{100}));
    }

    @Test
    void case4() throws IOException {
        assertEquals(1, Interview.lastBox(new int[]{5,6,7,8,10,11,38,23,1,21,1,3,17}));
    }

    @Test
    void case5() throws IOException {
        assertEquals(16, Interview.lastBox(new int[]{17,17,18}));
    }

}