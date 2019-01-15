import edu.princeton.cs.algs4.Picture;

import java.awt.*;

import static java.lang.StrictMath.sqrt;

public class SeamCarver {
    private Picture picture;
    private double[][] distTo;
    private int[][] edgeTo;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int col, int row) {
        if (col < 0 || col >= width() || row < 0 || row >= height()) throw new IndexOutOfBoundsException();
        int upRow = (row + height() - 1) % height();
        int leftCol = (col + width() - 1) % width();
        Color left = picture.get(leftCol, row);
        Color right = picture.get((col + 1) % width(), row);
        Color up = picture.get(col, upRow);
        Color down = picture.get(col, (row + 1) % height());

        int colGradient = gradient(left, right);
        int rowGradient = gradient(up, down);

        return sqrt(colGradient + rowGradient);

    }

    private int gradient(Color c1, Color c2) {
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        int b1 = c1.getBlue();

        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        int b2 = c2.getBlue();

        return (r2 - r1) * (r2 - r1) + (g2 - g1) * (g2 - g1) + (b2 - b1) * (b2 - b1);

    }

    public int[] findVerticalSeam() {
        initialize();
        for (int row = 0; row < height() - 1; row++) {
            for (int col = 0; col < width(); col++) {
                if (col > 0) {
                    relax(col, row, col - 1, row + 1);
                }

                relax(col, row, col, row + 1);
                if (col < width() - 1) {
                    relax(col, row, col + 1, row + 1);
                }
            }
        }
        double minDist = Double.MAX_VALUE;
        int minCol = -1;
        for (int col = 0; col < width(); col++) {
            if (minDist > distTo[col][height() - 1]) {
                minDist = distTo[col][height() - 1];
                minCol = col;
            }
        }
        int[] seam = new int[height()];
        for (int row = height() - 1; row >= 0; row--) {
            seam[row] = minCol;
            minCol = edgeTo[minCol][row];
        }
        return seam;
    }

    public int[] findHorizontalSeam() {
        Picture original = picture;
        Picture transposed = new Picture(original.height(), original.width());

        for (int row = 0; row < transposed.height(); row++) {
            for (int col = 0; col < transposed.width(); col++) {
                transposed.set(col, row, original.get(row, col));
            }
        }
        picture = transposed;

        int[] seam = findVerticalSeam();

        picture = original;

        return seam;
    }

    private void relax(int col1, int row1, int col2, int row2) {
        if (distTo[col2][row2] > distTo[col1][row1] + energy(col2, row2)) {
            distTo[col2][row2] = distTo[col1][row1] + energy(col2, row2);
            edgeTo[col2][row2] = col1;
        }
    }

    private void initialize() {
        distTo = new double[width()][height()];
        edgeTo = new int[width()][height()];

        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (row == 0)
                    distTo[col][row] = energy(col, row);
                else
                    distTo[col][row] = Double.POSITIVE_INFINITY;
                edgeTo[col][row] = -1;
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width() || width() == 1) throw new java.lang.IllegalArgumentException();

        Picture newPicture = new Picture(width(), height() - 1);
        for (int col = 0; col < newPicture.width(); col++) {
            for (int row = 0; row < newPicture.height() - 1; row++) {
                if (col >= seam[col]) {
                    newPicture.set(col, row, picture.get(col, row + 1));
                } else {
                    newPicture.set(col, row, picture.get(col, row));
                }
            }
        }

        picture = newPicture;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height() || height() == 1) throw new java.lang.IllegalArgumentException();

        Picture newPicture = new Picture(width() - 1, height());
        for (int row = 0; row < newPicture.height(); row++) {
            for (int col = 0; col < newPicture.width() - 1; col++) {
                if (col >= seam[row]) {
                    newPicture.set(col, row, picture.get(col + 1, row));
                } else {
                    newPicture.set(col, row, picture.get(col, row));
                }
            }
        }

        picture = newPicture;
    }

    public static void main(String[] args) {

    }
}