package Shape;

import Point.Point2d;

import java.util.Collection;

public class Ellipse extends BaseShape {
    /** TODO
     * Create a filled Ellipse that is centered on (0, 0)
     * @param widthDiameter Width of the Ellipse
     * @param heightDiameter Height of the Ellipse
     */
    public Ellipse(Double widthDiameter, Double heightDiameter) {
        super();
        for (double angle = 0.0; angle <= 360.0; angle += 0.1) {
            this.add(new Point2d(widthDiameter * Math.cos(Math.toRadians(angle)),
                                 heightDiameter * Math.sin(Math.toRadians(angle))));
        }
    }

    /** TODO
     * Create a filled Ellipse that is centered on (0,0)
     * @param dimensions 2D point containing the width and height of the Ellipse
     */
    public Ellipse(Point2d dimensions) {
        this(dimensions.X(), dimensions.Y());
    }

    /**
     * Create an Ellipse from a given collection of 2D points
     * @param coords Collection of 2D points
     */
    private Ellipse(Collection<Point2d> coords) {
        super(coords);
    }

    /** TODO
     * Translate the Ellipse by the given 2D point
     * @param point The 2D point by which to translate
     * @return The translated Ellipse
     */
    @Override
    public Ellipse translate(Point2d point) {
        for (Point2d coords : this.getCoords())
            coords.translate(point);
        return this;
    }

    /** TODO
     * Rotate the Ellipse by the given angle (In Radians)
     * @param angle The angle by which to rotate
     * @return The rotated Ellipse
     */
    @Override
    public Ellipse rotate(Double angle) {
        for (Point2d coords : this.getCoords())
            coords.rotate(angle);
        return this;
    }

    /** TODO
     * @return Deep Copy of the Ellipse
     */
    @Override
    public Ellipse clone() {
        return (Ellipse) super.clone();
    }
}