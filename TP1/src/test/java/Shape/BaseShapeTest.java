package Shape;

import Point.Point2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseShapeTest {
    @Test
    public void defaultCtorCreatesEmptyCoords() {
        BaseShape empty = new BaseShape();
        assertEquals(0, empty.getCoords().size());
    }

    @Test
    void ctorDoesDeepCopy() {
        Collection<Point2d> coords = new ArrayList<>(List.of(
                new Point2d(0.0, 0.0),
                new Point2d(1.0, 1.0)
        ));
        BaseShape shape = new BaseShape(coords);

        assertNotSame(coords.iterator().next(), shape.getCoords().iterator().next());

        coords.remove(new Point2d(1.0, 1.0));
        assertNotEquals(coords, shape.getCoords());
    }

    @Test
    void addPoint() {
        Point2d addedRef = new Point2d(0.0, 0.0);

        BaseShape shape = new BaseShape();
        shape.add(addedRef);

        Point2d withinShape = shape.getCoords().iterator().next();
        assertEquals(addedRef, withinShape);
        assertNotSame(addedRef, withinShape);
    }

    @Test
    void addShape() {
        BaseShape shape = new BaseShape();
        BaseShape added = new BaseShape(List.of(
                new Point2d(0.0, 0.0),
                new Point2d(1.0, 1.0)
        ));

        shape.add(added);

        Collection<Point2d> coords = shape.getCoords();
        Collection<Point2d> addedCoords = added.getCoords();

        assertEquals(addedCoords, coords);
        assertNotSame(addedCoords.iterator().next(), coords.iterator().next());
    }

    @Test
    void addCollection() {
        Point2d reference = new Point2d(0.0, 0.0);
        Collection<Point2d> added = List.of(
                reference,
                new Point2d(1.0, 1.0)
        );

        BaseShape shape = new BaseShape();
        shape.addAll(added);

        Collection<Point2d> coords = shape.getCoords();
        assertEquals(added, coords);
        assertNotSame(reference, coords.iterator().next());
    }

    @Test
    void removePoint() {
        Point2d kept = new Point2d(0.0, 0.0);
        Point2d removed = new Point2d(1.0, 1.0);

        BaseShape shape = new BaseShape(new ArrayList<>(List.of(
                kept,
                removed
        )));
        shape.remove(removed);

        assertEquals(List.of(kept), shape.getCoords());
    }

    @Test
    void removeShape() {
        Collection<Point2d> kept = List.of(
                new Point2d(0.0, 0.0),
                new Point2d(3.0, 3.0)
        );
        Collection<Point2d> removed = List.of(
                new Point2d(2.0, 2.0),
                new Point2d(4.0, 4.0)
        );
        Collection<Point2d> allCoords = new ArrayList<>(kept);
        allCoords.addAll(removed);

        BaseShape shape = new BaseShape(allCoords);
        shape.remove(new BaseShape(removed));

        assertEquals(kept, shape.getCoords());
    }

    @Test
    void removeCollection() {
        Collection<Point2d> kept = List.of(
                new Point2d(0.0, 0.0),
                new Point2d(3.0, 3.0)
        );
        Collection<Point2d> removed = List.of(
                new Point2d(2.0, 2.0),
                new Point2d(4.0, 4.0)
        );
        Collection<Point2d> allCoords = new ArrayList<>(kept);
        allCoords.addAll(removed);

        BaseShape shape = new BaseShape(allCoords);
        shape.removeAll(removed);

        assertEquals(kept, shape.getCoords());
    }

    @Test
    void translate() {
        BaseShape shape = new BaseShape(List.of(
                new Point2d(1.0, 0.0),
                new Point2d(2.0, 2.0)
        ));
        shape.translate(new Point2d(1.0, 1.0));

        Collection<Point2d> translated = List.of(
                new Point2d(2.0, 1.0),
                new Point2d(3.0, 3.0)
        );
        assertEquals(translated, shape.getCoords());
    }

    @Test
    void rotate() {
        BaseShape shape = new BaseShape(List.of(
                new Point2d(1.0, 0.0),
                new Point2d(2.0, 2.0)
        ));
        shape.rotate(Math.toRadians(90));

        Collection<Point2d> rotated = List.of(
                new Point2d(0.0, 1.0),
                new Point2d(-2.0, 2.0)
        );
        assertEquals(rotated, shape.getCoords());
    }

    @Test
    void getMaxX() {
        BaseShape shape = new BaseShape();
        assertEquals(-Double.MAX_VALUE, shape.getMaxX());

        double maxX = 2;
        shape.addAll(List.of(
                new Point2d(1.0, 5.0),
                new Point2d(maxX, 3.0)
        ));

        assertEquals(maxX, shape.getMaxX());
    }

    @Test
    void getMaxY() {
        BaseShape shape = new BaseShape();
        assertEquals(-Double.MAX_VALUE, shape.getMaxY());

        double maxY = 5;
        shape.addAll(List.of(
                new Point2d(1.0, maxY),
                new Point2d(2.0, 3.0)
        ));

        assertEquals(maxY, shape.getMaxY());
    }

    @Test
    void getMaxCoord() {
        BaseShape shape = new BaseShape();
        assertEquals(new Point2d(-Double.MAX_VALUE, -Double.MAX_VALUE), shape.getMaxCoord());

        Point2d max = new Point2d(2.0, 5.0);
        shape.addAll(List.of(
                new Point2d(1.0, max.Y()),
                new Point2d(max.X(), 3.0)
        ));

        assertEquals(max, shape.getMaxCoord());
    }

    @Test
    void getMinX() {
        BaseShape shape = new BaseShape();
        assertEquals(Double.MAX_VALUE, shape.getMinX());

        double minX = 1;
        shape.addAll(List.of(
                new Point2d(minX, 5.0),
                new Point2d(2.0, 3.0)
        ));

        assertEquals(minX, shape.getMinX());
    }

    @Test
    void getMinY() {
        BaseShape shape = new BaseShape();
        assertEquals(Double.MAX_VALUE, shape.getMinY());

        double minY = 3;
        shape.addAll(List.of(
                new Point2d(1.0, 5.0),
                new Point2d(2.0, minY)
        ));

        assertEquals(minY, shape.getMinY());
    }

    @Test
    void getMinCoord() {
        BaseShape shape = new BaseShape();
        assertEquals(new Point2d(Double.MAX_VALUE, Double.MAX_VALUE), shape.getMinCoord());

        Point2d min = new Point2d(1.0, 3.0);
        shape.addAll(List.of(
                new Point2d(min.X(), 5.0),
                new Point2d(2.0, min.Y())
        ));

        assertEquals(min, shape.getMinCoord());
    }

    @Test
    void getCoordsShouldReturnShallowCopy() {
        BaseShape empty = new BaseShape();
        Collection<Point2d> emptyCoords = empty.getCoords();

        assertEquals(0, emptyCoords.size());

        Collection<Point2d> coords = List.of(
                new Point2d(1.0, 0.0)
        );
        BaseShape notEmpty = new BaseShape(coords);
        Collection<Point2d> firstCopy = notEmpty.getCoords();
        Collection<Point2d> secondCopy = notEmpty.getCoords();

        assertEquals(coords, firstCopy);
        assertNotSame(firstCopy, secondCopy);
        assertSame(firstCopy.iterator().next(), secondCopy.iterator().next());
    }

    @Test
    void cloneCoordsShouldReturnDeepCopy() {
        BaseShape empty = new BaseShape();
        Collection<Point2d> emptyCoords = empty.cloneCoords();

        assertEquals(0, emptyCoords.size());

        Collection<Point2d> coords = List.of(
                new Point2d(1.0, 0.0)
        );
        BaseShape notEmpty = new BaseShape(coords);
        Collection<Point2d> firstCopy = notEmpty.cloneCoords();
        Collection<Point2d> secondCopy = notEmpty.cloneCoords();

        assertEquals(coords, firstCopy);
        assertNotSame(firstCopy, secondCopy);
        assertNotSame(firstCopy.iterator().next(), secondCopy.iterator().next());
    }

    @Test
    void cloneShouldReturnDeepCopy() {
        BaseShape initial = new BaseShape(List.of(
                new Point2d(2.0, 3.0)
        ));
        BaseShape clone = initial.clone();

        assertNotSame(clone, initial);

        Collection<Point2d> clonedCoords = clone.getCoords();
        Collection<Point2d> initialCoords = initial.getCoords();

        assertEquals(clonedCoords, initialCoords);
        assertNotSame(clonedCoords, initialCoords);
        assertNotSame(clonedCoords.iterator().next(), initialCoords.iterator().next());
    }
}