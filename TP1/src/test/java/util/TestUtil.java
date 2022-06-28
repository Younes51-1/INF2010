package util;

import static org.junit.jupiter.api.Assertions.*;
import org.ejml.simple.SimpleMatrix;

public final class TestUtil {
    public static double[][] toPrimitive(Double[] input) {
        return toPrimitive(new Double[][] { input });
    }

    public static double[][] toPrimitive(Double[][] input) {
        double[][] output = new double[input.length][input[0].length];
        for (int i = 0; i < output.length; ++i) {
            for (int j = 0; j < output[i].length; ++j) {
                output[i][j] = input[i][j];
            }
        }
        return output;
    }

    public static void isEqual(SimpleMatrix expected, Double[] actual) {
        for (int i = 0; i < actual.length; ++i)
            assertEquals(expected.get(0, i), actual[i], 0.001);
    }
}
