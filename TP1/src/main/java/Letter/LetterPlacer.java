package Letter;

import Point.Point2d;
import Shape.BaseShape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LetterPlacer {
    private final static int enlargeFactor = 3;
    private final static double padding = 20.0;
    private final static int maxColor = 256;

    private int numberOfLines = 1;
    private final Collection<Collection<Point2d>> shapes = new ArrayList<>();
    private BaseShape previousLetter = null;

    /**
     * Adds the letter to the image
     * @param letter The character representing the letter to add
     * @throws Exception The letter does not exist
     */
    public void placeNext(char letter) throws Exception {
        BaseShape nextLetter = getNextLetter(letter);

        Double horizontalPadding = previousLetter != null ? previousLetter.getMaxX() + padding : padding;

        previousLetter = insertShape(nextLetter, new Point2d(
                horizontalPadding,
                (numberOfLines - 1) * LetterFactory.maxHeight + numberOfLines * padding));
    }

    /**
     * Adds the letter and positions it on another line in the image
     * @param letter The character representing the letter to add
     * @throws Exception The letter does not exist
     */
    public void placeNextln(char letter) throws Exception {
        BaseShape nextLetter = getNextLetter(letter);
        previousLetter = insertShape(nextLetter, new Point2d(padding,
                        numberOfLines++ * LetterFactory.maxHeight + numberOfLines * padding));
    }

    /**
     * Creates the graphical version of a letter
     * @param letter The character representing the letter to create
     * @return BaseShape containing the graphical version of the letter
     * @throws Exception The letter does not exist
     */
    private BaseShape getNextLetter(char letter) throws Exception {
        return switch (letter) {
            case 'A' -> LetterFactory.create_A();
            case 'C' -> LetterFactory.create_C();
            case 'E' -> LetterFactory.create_E();
            case 'M' -> LetterFactory.create_M();
            case 'N' -> LetterFactory.create_N();
            case 'O' -> LetterFactory.create_O();
            case 'T' -> LetterFactory.create_T();
            case 'V' -> LetterFactory.create_V();
            default -> throw new Exception("Cette lettre n'est pas valide: " + letter);
        };
    }

    /**
     * Create a random RGB color
     * @return int containing an RGB color in the format 0xRRGGBB
     */
    private static int getRandomRGB() {
        Random rand = new Random();
        return  rand.nextInt(maxColor) * 0x010000 +
                rand.nextInt(maxColor) * 0x000100 +
                rand.nextInt(maxColor);
    }

    /**
     *
     * @return The color white 0xFFFFFF
     */
    private static int getWhite() {
        return 0xFFFFFF;
    }

    /**
     * Translates the shape in the right position for the image
     * @param nextLetter The shape to add
     * @param nextPosition Where we should add the shape
     * @return The translated shape that was just added to the image
     */
    private BaseShape insertShape(BaseShape nextLetter, Point2d nextPosition) {
        Point2d center = new Point2d(-(nextLetter.getMaxX() + nextLetter.getMinX())/2,
                -(nextLetter.getMaxY() + nextLetter.getMinY())/2);

        nextLetter = nextLetter.translate(center);
        nextLetter = nextLetter.translate(nextLetter.getMaxCoord())
                .translate(nextPosition);

        shapes.add(nextLetter.getCoords());

        return nextLetter;
    }

    /**
     * Creates an image containing the graphical message
     * @param fileName The name of the image file to create
     * @param saveInWhite Should the image be in color or GrayScale
     * @return The image file containing the message
     * @throws IOException Can't save image
     */
    public File saveImage(String fileName, boolean saveInWhite) throws IOException {
        Point2d max = new BaseShape(shapes.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())).getMaxCoord();

        int[][] pixels = new int[(int)(max.Y() + padding)][(int)(max.X() + padding)];
        for (Collection<Point2d> coords : shapes) {
            int color = saveInWhite ? getWhite() : getRandomRGB();
            for (Point2d point : coords) {
                enlarge(pixels, (int)Math.round(point.Y()), (int)Math.round(point.X()), color);
            }
        }

        BufferedImage img = new BufferedImage(pixels[0].length, pixels.length,
                BufferedImage.TYPE_INT_RGB);

        int[] flatPixels = Arrays.stream(pixels)
                .flatMapToInt(Arrays::stream)
                .toArray();

        img.setRGB(0, 0, pixels[0].length, pixels.length,
                flatPixels, 0, pixels[0].length);

        File imgFile = new File(fileName + ".jpg");
        ImageIO.write(img, "jpg", imgFile);

        return imgFile;
    }

    /**
     * Scales up each point by a predetermined factor and create the corresponding pixels
     * @param pixels Array containing the pixels of the image
     * @param y Y coordinate of the point to enlarge
     * @param x X coordinate of the point to enlarge
     * @param color The color in which the pixels should be created
     */
    private void enlarge(int[][] pixels, int y, int x, int color) {
        for (int i = -enlargeFactor; i <= enlargeFactor; ++i) {
            for (int j = -enlargeFactor; j <= enlargeFactor; ++j) {
                try {
                    pixels[j + y][i + x] = color;
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }
        }
    }
}
