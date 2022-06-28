package Point;

import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PointOperatorTest {
    @Test
    void translate2D() {
        Double[] point2D = new Double[] {1.0, 2.0};
        PointOperator.translate(point2D, new Double[] {1.0, 1.0});

        assertArrayEquals(new Double[] {2.0, 3.0}, point2D);
    }

    @Test
    void anyDimensionTranslate() {
        final int maxDim = 6;

        for (int dimCount = 1; dimCount <= maxDim; ++dimCount) {
            Double[] point = new Double[dimCount];
            Arrays.fill(point, 1.0);

            Double[] translate = new Double[dimCount];
            for (int i = 0; i < dimCount; ++i) translate[i] = (double) i;

            SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(point));
            SimpleMatrix simpleTranslate = new SimpleMatrix(TestUtil.toPrimitive(translate));
            SimpleMatrix expected = simplePoint.plus(simpleTranslate);

            PointOperator.translate(point, translate);

            TestUtil.isEqual(expected, point);
        }
    }

    @Test
    void rotate2D() {
        Double[] point2D = new Double[] {1.0, 2.0};
        PointOperator.rotate(point2D, new Double[][] {{1.0, 0.0}, {1.0, 0.0}});

        assertArrayEquals(new Double[] {1.0, 1.0}, point2D);
    }

    @Test
    void anyDimensionRotate() {
        final int maxDim = 6;

        for (int dimCount = 1; dimCount <= maxDim; ++dimCount) {
            Double[] point = new Double[dimCount];
            Arrays.fill(point, 1.0);

            Double[][] rotate = new Double[dimCount][dimCount];
            for (int i = 0; i < dimCount; ++i){
                Arrays.fill(rotate[i], 0.0);
                rotate[i][i] = (double) i + 1;
            }

            SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(point));
            SimpleMatrix simpleRotate = new SimpleMatrix(TestUtil.toPrimitive(rotate));
            SimpleMatrix expected = simplePoint.mult(simpleRotate);

            PointOperator.rotate(point, rotate);

            TestUtil.isEqual(expected, point);
        }
    }

    @Test
    void divide2D() {
        Double[] point2D = new Double[] {1.0, 2.0};
        PointOperator.divide(point2D, 2.0);

        assertArrayEquals(new Double[] {0.5, 1.0}, point2D);
    }

    @Test
    void anyDimensionDivide() {
        final int maxDim = 6;
        final double scalar = 5.0;

        for (int dimCount = 1; dimCount <= maxDim; ++dimCount) {

            Double[] point = new Double[dimCount];
            for (int i = 0; i < dimCount; ++i) point[i] = (double) i;

            SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(point));
            SimpleMatrix expected = simplePoint.divide(scalar);

            PointOperator.divide(point, scalar);

            TestUtil.isEqual(expected, point);
        }
    }

    @Test
    void multiply2D() {
        Double[] point2D = new Double[] {1.0, 2.0};
        PointOperator.multiply(point2D, 2.0);

        assertArrayEquals(new Double[] {2.0, 4.0}, point2D);
    }

    @Test
    void anyDimensionMultiply() {
        final int maxDim = 6;
        final double scalar = 5.0;

        for (int dimCount = 1; dimCount <= maxDim; ++dimCount) {

            Double[] point = new Double[dimCount];
            for (int i = 0; i < dimCount; ++i) point[i] = (double) i;

            SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(point));
            SimpleMatrix expected = simplePoint.scale(scalar);

            PointOperator.multiply(point, scalar);

            TestUtil.isEqual(expected, point);
        }
    }

    @Test
    void add2D() {
        Double[] point2D = new Double[] {1.0, 2.0};
        PointOperator.add(point2D, 2.0);

        assertArrayEquals(new Double[] {3.0, 4.0}, point2D);
    }

    @Test
    void anyDimensionAdd() {
        final int maxDim = 6;
        final double scalar = 5.0;

        for (int dimCount = 1; dimCount <= maxDim; ++dimCount) {

            Double[] point = new Double[dimCount];
            for (int i = 0; i < dimCount; ++i) point[i] = (double) i;

            SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(point));
            SimpleMatrix expected = simplePoint.plus(scalar);

            PointOperator.add(point, scalar);

            TestUtil.isEqual(expected, point);
        }
    }
}