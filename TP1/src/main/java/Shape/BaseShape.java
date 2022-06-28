package Shape;

import Point.Point2d;

import java.util.*;

public class BaseShape implements Cloneable {
    private final Collection<Point2d> coords;

    /** TODO
     * Create a BaseShape with empty coordinates
     */
    public BaseShape() {
        this.coords =  new ArrayList<>();
    }

    /** TODO
     * Create a BaseShape from a collection of 2D points
     * @param coords The collection of 2D points
     */
    public BaseShape(Collection<Point2d> coords) {
        this();
        this.addAll(coords);
    }

    /** TODO
     * Add a deep copy of the 2D point to the Shape
     * @param coord 2D point to add
     * @return Updated BaseShape
     */
    public BaseShape add(Point2d coord) {
        this.coords.add(coord.clone());
        return this;
    }

    /** TODO
     * Add a deep copy of the 2D points of the shape to the current shape
     * @param shape Shape to add to the current shape
     * @return Updated BaseShape
     */
    public BaseShape add(BaseShape shape) {
        return this.addAll(shape.getCoords());
    }

    /** TODO
     * Add a deep copy of the points in the collection to the shape
     * @param coords Collections of point to add
     * @return Updated BaseShape
     */
    public BaseShape addAll(Collection<Point2d> coords) {
        for (Point2d point : coords)
            this.add(point);
        return this;
    }

    /** TODO
     * Remove the 2D point from the shape
     * @param coord Point to remove
     * @return Updated BaseShape
     */
    public BaseShape remove(Point2d coord) {
        this.coords.remove(coord);
        return this;
    }

    /** TODO
     * Remove the 2D points in the shape from the current shape
     * @param shape Shape containing the points to remove
     * @return Updated BaseShape
     */
    public BaseShape remove(BaseShape shape) {
        return this.removeAll(shape.getCoords());
    }

    /** TODO
     * Remove the 2D points in the collection from the current shape
     * @param coords Collection of 2D points to remove
     * @return Updated BaseShape
     */
    public BaseShape removeAll(Collection<Point2d> coords) {
        this.coords.removeAll(coords);
        return this;
    }

    /** TODO
     * Return a shallow copy of the coordinates of the shape
     * @return Shallow copy of all coordinates contained by this BaseShape
     */
    public Collection<Point2d> getCoords() {
        return new ArrayList<>(this.coords);
    }

    /** TODO
     * Create and return a deep copy of the coordinates of the shape
     * @return Deep copy of all coordinates contained by this BaseShape
     */
    public Collection<Point2d> cloneCoords() {
        Collection<Point2d> deepCopy = new ArrayList<>();
        for (Point2d point : coords)
            deepCopy.add(point.clone());
        return deepCopy;
    }

    /** TODO
     * Translate all points in a shape by a given vector
     * @param translateVector Vector by which to translate
     * @return Translated shape
     */
    public BaseShape translate(Point2d translateVector) {
        for (Point2d point : this.coords)
            point.translate(translateVector);
        return this;
    }

    /** TODO
     * Rotate all points in a shape by a given angle
     * @param angle Angle by which to rotate
     * @return Rotated shape
     */
    public BaseShape rotate(Double angle) {
        for (Point2d point : this.coords)
            point.rotate(angle);
        return this;
    }

    /** TODO
     * @return Maximum X coordinate of the shape
     */
    public Double getMaxX() {
        double maxX = -Double.MAX_VALUE;
        for (Point2d point : this.coords) {
            if (point.X() > maxX)
                maxX = point.X();
        }
        return maxX;
    }

    /** TODO
     * @return Maximum Y coordinate of the shape
     */
    public Double getMaxY() {
        double maxY = -Double.MAX_VALUE;
        for (Point2d point : this.coords) {
            if (point.Y() > maxY)
                maxY = point.Y();
        }
        return maxY;
    }

    /** TODO
     * @return 2D Point containing the maximum X and Y coordinates of the shape
     */
    public Point2d getMaxCoord() {
        return new Point2d(getMaxX(), getMaxY());
    }

    /** TODO
     * @return Minimum X coordinate of the shape
     */
    public Double getMinX() {
        double minX = Double.MAX_VALUE;
        for (Point2d point : this.coords) {
            if (point.X() < minX)
                minX = point.X();
        }
        return minX;
    }

    /** TODO
     * @return Minimum Y coordinate of the shape
     */
    public Double getMinY() {
        double minY = Double.MAX_VALUE;
        for (Point2d point : this.coords) {
            if (point.Y() < minY)
                minY = point.Y();
        }
        return  minY;
    }

    /** TODO
     * @return 2D point containing the minimum X and Y coordinate of the shape
     */
    public Point2d getMinCoord() {
        return new Point2d(getMinX(), getMinY());
    }

    /** TODO
     * @return Deep copy of the current shape
     */
    public BaseShape clone() {
        return new BaseShape(this.cloneCoords()); }
}