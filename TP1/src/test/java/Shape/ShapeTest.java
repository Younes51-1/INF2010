package Shape;

import Point.Point2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeTest {
    @Test
    void squareRectangle() {
        Square square = new Square(5.0);
        Rectangle rectangle = new Rectangle(5.0, 5.0);
        rectangle.remove(square);

        assertEquals(rectangle.getCoords().size(), 0.0);
    }

    @Test
    void circleEllipse() {
        Circle circle = new Circle(5.0);
        Ellipse ellipse = new Ellipse(5.0, 5.0);
        ellipse.remove(circle);

        assertEquals(ellipse.getCoords().size(), 0.0);
    }

    @Test
    void ellipseDimCtor() {
        Ellipse ellipse = new Ellipse(new Point2d(5.0, 5.0));
        assertTrue(ellipse.getCoords().size() >= 16);
    }

    @Test
    void rectangleDimCtor() {
        BaseShape rect = new Rectangle(new Point2d(5.0, 5.0));
        assertTrue(rect.getCoords().size() >= 25);
    }

    @Test
    void rotatingEllipse() {
        Ellipse ellipse = new Ellipse(new Point2d(3.0, 2.0));
        ellipse.rotate(Math.toRadians(90));

        assertNotEquals(ellipse.getCoords(), new Ellipse(new Point2d(3.0, 2.0)));
    }
}
