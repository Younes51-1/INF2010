package Letter;

import Point.Point2d;
import Shape.*;

public final class LetterFactory {
    final static Double maxHeight = 150.0;
    final static Double maxWidth = maxHeight / 2.5;
    final static Double halfMaxHeight = maxHeight / 2;
    final static Double halfMaxWidth = maxWidth / 2;
    final static Double stripeThickness = maxHeight / 8;
    final static Double halfStripeThickness = stripeThickness / 2;


    /** TODO
     * Create the letter T graphically
     * @return BaseShape containing the letter T
     */
    public static BaseShape create_T() {
        Rectangle topBar = new Rectangle(halfMaxWidth, halfStripeThickness / 2)
                .rotate(Math.toRadians(180)).translate(new Point2d(0.0, -halfMaxHeight));

        return new Rectangle(halfStripeThickness / 2, halfMaxHeight - 4.5)
                .add(topBar);
    }

    /** TODO
     * Create the letter E graphically
     * @return BaseShape containing the letter E
     */
    public static BaseShape create_E() {
        Rectangle topBar = new Rectangle(halfMaxWidth, halfStripeThickness / 2 )
                .rotate(Math.toRadians(180))
                .translate(new Point2d( stripeThickness + 6, -halfMaxHeight + 4.5));

        Rectangle middleBar = new Rectangle(halfMaxWidth, halfStripeThickness / 2 )
                .rotate(Math.toRadians(180))
                .translate(new Point2d(stripeThickness + 6  , 0.0));

        Rectangle baseBar = new Rectangle(halfMaxWidth, halfStripeThickness / 2 )
                .rotate(Math.toRadians(180))
                .translate(new Point2d(stripeThickness + 6 , halfMaxHeight - 4.5));

        return new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .add(topBar)
                .add(middleBar)
                .add(baseBar);
    }

    /** TODO
     * Create the letter O graphically
     * @return BaseShape containing the letter O
     */
    public static BaseShape create_O() {
        return new Ellipse(stripeThickness, halfMaxHeight);
    }

    /** TODO
     * Create the letter C graphically
     * @return BaseShape containing the letter C
     */
    public static BaseShape create_C() {
        Rectangle rightToRemove = new Rectangle(halfMaxWidth, 0.75 * halfMaxHeight )
                .translate(new Point2d(0.65 * halfMaxWidth, 0.0));

        return new Ellipse(stripeThickness, halfMaxHeight)
                .remove(rightToRemove);
    }

    /** TODO
     * Create the letter A graphically
     * @return BaseShape containing the letter A
     */
    public static BaseShape create_A() {
        Rectangle leftBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .rotate(Math.toRadians(350))
                .translate(new Point2d(stripeThickness * 1.5, 0.0));

        Rectangle middleBar = new Rectangle(halfStripeThickness / 2, halfStripeThickness)
                .rotate(Math.toRadians(90))
                .translate(new Point2d(stripeThickness /2, 0.0));

        return new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .rotate(Math.toRadians(10))
                .add(leftBar)
                .add(middleBar);
    }

    /** TODO
     * Create the letter V graphically
     * @return BaseShape containing the letter V
     */
    public static BaseShape create_V() {
        Rectangle rightBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .rotate(Math.toRadians(10))
                .translate(new Point2d(1.5 * stripeThickness, 0.0));

        return new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .rotate(Math.toRadians(350))
                .add(rightBar);
    }

    /** TODO
     * Create the letter N graphically
     * @return BaseShape containing the letter N
     */
    public static BaseShape create_N() {
        Rectangle rightBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .translate(new Point2d( maxWidth - 5, 0.0));

        Rectangle middleBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .rotate(Math.toRadians(340))
                .translate(new Point2d(halfMaxWidth, 0.0));

        return new Rectangle(halfStripeThickness / 2, halfMaxHeight)
                .add(rightBar)
                .add(middleBar);
    }

    /** TODO
     * Create the letter M graphically
     * @return BaseShape containing the letter M
     */
    public static BaseShape create_M() {
        Rectangle rightBar = new Rectangle(halfStripeThickness / 2,halfMaxHeight)
                .translate(new Point2d(halfMaxWidth + 35, 0.0));

        Rectangle leftTopBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight / 2)
                .rotate(Math.toRadians(340))
                .translate(new Point2d(halfMaxWidth - 9, -halfMaxHeight / 2));

        Rectangle rightTopBar = new Rectangle(halfStripeThickness / 2, halfMaxHeight / 2)
                .rotate(Math.toRadians(20))
                .translate(new Point2d(2 * halfMaxWidth - 11, -halfMaxHeight / 2));

        return new Rectangle(halfStripeThickness / 2,halfMaxHeight)
                .add(rightBar)
                .add(leftTopBar)
                .add(rightTopBar);
    }
}