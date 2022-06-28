import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import KthSmallestElement.KthSmallestElement;

class KthSmallestElementTest {
    private static Integer[][] readInput(String problemNumber) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/test/resources/KthSmallestElement/inputs/input" + problemNumber + ".csv"));

        ArrayList<Integer[]> matrix = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String listString = scanner.nextLine();
            Integer[] row = Arrays
                    .stream(listString.split(","))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);

            matrix.add(row);
        }

        return matrix.toArray(Integer[][]::new);
    }

    @Test
    void inputsValidity() {
        assertNull(KthSmallestElement.findKthSmallestElement(null, 0));

        int onlyElement = 1;
        Integer[][] oneValueMatrix = {
                {onlyElement}
        };

        assertNull(KthSmallestElement.findKthSmallestElement(oneValueMatrix, -1));
        assertNull(KthSmallestElement.findKthSmallestElement(oneValueMatrix, 1));

        Integer firstElement = KthSmallestElement.findKthSmallestElement(oneValueMatrix, 0);
        assertNotNull(firstElement);
        assertEquals(onlyElement, firstElement);
    }

    @Test
    void simpleSquareShape() {
        int kthSmallestElement = 7;
        Integer[][] simpleSquareMatrix = {
                {1,2,4},
                {3,5,kthSmallestElement},
                {6,8,9}
        };

        Integer result = KthSmallestElement.findKthSmallestElement(simpleSquareMatrix, 6);

        assertNotNull(result);
        assertEquals(kthSmallestElement, result);
    }

    @Test
    void simpleRectangleShape() {
        int kthSmallestElement = 10;
        Integer[][] simpleSquareMatrix = {
                {1, 2, 4,   7},
                {3, 5, 8,   kthSmallestElement},
                {6, 9, 11,  12}
        };

        Integer result = KthSmallestElement.findKthSmallestElement(simpleSquareMatrix, 9);

        assertNotNull(result);
        assertEquals(kthSmallestElement, result);
    }

    @Test
    void complexMatrix() {
        int kthSmallestElement = 9;
        Integer[][] simpleSquareMatrix = {
                {1, 3,                  4},
                {2, 7,                  7},
                {6, kthSmallestElement, 10}
        };

        Integer result = KthSmallestElement.findKthSmallestElement(simpleSquareMatrix, 7);

        assertNotNull(result);
        assertEquals(kthSmallestElement, result);
    }

    @Test
    void input00() throws FileNotFoundException {
        Integer[][] matrix = readInput("00");
        Integer result = KthSmallestElement.findKthSmallestElement(matrix, 555);

        assertNotNull(result);
        assertEquals(1863, result);
    }

    @Test
    void input01() throws FileNotFoundException {
        Integer[][] matrix = readInput("01");
        Integer result = KthSmallestElement.findKthSmallestElement(matrix, 1000);

        assertNotNull(result);
        assertEquals(2329, result);
    }

    @Test
    void input02() throws FileNotFoundException {
        Integer[][] matrix = readInput("02");
        Integer result = KthSmallestElement.findKthSmallestElement(matrix, 4799);

        assertNotNull(result);
        assertEquals(5891, result);
    }

    @Test
    void input03() throws FileNotFoundException {
        Integer[][] matrix = readInput("03");
        Integer result = KthSmallestElement.findKthSmallestElement(matrix, 17800);

        assertNotNull(result);
        assertEquals(14837, result);
    }

    @Test
    void input04() throws FileNotFoundException {
        Integer[][] matrix = readInput("04");
        Integer result = KthSmallestElement.findKthSmallestElement(matrix, 30155);

        assertNotNull(result);
        assertEquals(20433, result);
    }
}
