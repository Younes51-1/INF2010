package Point;

import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

public class Point2dTest {
    @Test
    void ctorSplit() {
        Point2d point = new Point2d(1.0, 2.0);

        assertEquals(point.X(), 1.0);
        assertEquals(point.Y(), 2.0);
    }

    @Test
    void ctorVector() {
        Point2d point = new Point2d(new Double[] {1.0, 2.0});

        assertEquals(point.X(), 1.0);
        assertEquals(point.Y(), 2.0);
    }

    @Test
    void scalarRotation() {
        Point2d point = new Point2d(1.0, 2.0);
        Point2d sameReference = point.rotate(Math.toRadians(90));

        assertEquals(point, new Point2d(-2.0, 1.0));
        assertSame(sameReference, point);
    }

    @Test
    void scalarDivision() {
        Point2d point = new Point2d(1.0, 2.0);
        Point2d sameReference = point.divide(2.0);

        assertEquals(point, new Point2d(0.5, 1.0));
        assertSame(sameReference, point);
    }

    @Test
    void scalarMultiplication() {
        Point2d point = new Point2d(1.0, 2.0);
        Point2d sameReference = point.multiply(2.0);

        assertEquals(point, new Point2d(2.0, 4.0));
        assertSame(sameReference, point);
    }

    @Test
    void scalarAddition() {
        Point2d point = new Point2d(1.0, 2.0);
        Point2d sameReference = point.add(2.0);

        assertEquals(point, new Point2d(3.0, 4.0));
        assertSame(sameReference, point);
    }

    @Test
    void pointTranslation() {
        Double[] vector = new Double[] { 1.0, 2.0 };
        Double[] translation = new Double[] {1.0, 1.0};

        SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(vector));
        SimpleMatrix simpleTranslation = new SimpleMatrix(TestUtil.toPrimitive(translation));
        SimpleMatrix expected = simplePoint.plus(simpleTranslation);

        Point2d point = new Point2d(vector);
        Point2d sameReference = point.translate(new Point2d(translation));

        TestUtil.isEqual(expected, new Double[] {point.X(), point.Y()});
        assertSame(sameReference, point);
    }

    @Test
    void matrixRotation() {
        Double[] vector = {1.0, 5.0};
        Double[][] rotation = {
                {0.0, -1.0},
                {1.0, 0.0},
        };

        SimpleMatrix simplePoint = new SimpleMatrix(TestUtil.toPrimitive(vector));
        SimpleMatrix simpleRotation = new SimpleMatrix(TestUtil.toPrimitive(rotation));
        SimpleMatrix expected = simpleRotation.mult(simplePoint.transpose()).transpose();

        Point2d point = new Point2d(vector);
        Point2d sameReference = point.rotate(Math.toRadians(90));

        TestUtil.isEqual(expected, new Double[] {point.X(), point.Y()});
        assertSame(sameReference, point);
    }

    @Test
    void cloneDeepCopy() {
        Point2d point = new Point2d(1.0, 2.0);
        Point2d deepCopy = point.clone();

        assertEquals(point, deepCopy);
        assertNotSame(point, deepCopy);

        point.translate(new Point2d(1.0, 1.0));
        assertNotEquals(point, deepCopy);
    }
}