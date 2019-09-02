package part2.week2.project;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private double[][] energy;
    private int[][] picture;
    private int rows;
    private int cols;
    private boolean isTranspose = false;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture pic) {
        if (pic == null) {
            throw new IllegalArgumentException("input null");
        }
        rows = pic.height();
        cols = pic.width();
        picture = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                picture[i][j] = pic.getRGB(j, i);
            }
        }
        energy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                energy[i][j] = calculateEnergy(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        checkTranspose(false);
        Picture ret = new Picture(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ret.set(j, i, new Color(picture[i][j], true));
            }
        }
        return ret;
    }

    // width of current picture
    public int width() {
        return isTranspose ? rows : cols;
    }

    // height of current picture
    public int height() {
        return isTranspose ? cols : rows;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (isTranspose) {
            int tmp = x;
            x = y;
            y = tmp;
        }
        if (x < 0 || x >= cols || y < 0 || y >= rows)
            throw new IllegalArgumentException("X or Y is out of range");
        return energy[y][x];

    }

    private double calculateEnergy(int y, int x) {
        if (y == 0 || x == 0 || y == rows - 1 || x == cols - 1) return 1000;
        double xSquare = calculateSquare(picture[y][x - 1], picture[y][x + 1]);
        double ySquare = calculateSquare(picture[y - 1][x], picture[y + 1][x]);
        return Math.sqrt(xSquare + ySquare);
    }

    private double calculateSquare(int rgbA, int rgbB) {
        int redDiff = getRed(rgbA) - getRed(rgbB);
        int greenDiff = getGreen(rgbA) - getGreen(rgbB);
        int blueDiff = getBlue(rgbA) - getBlue(rgbB);
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    private void transposePicture() {
        int[][] pictureT = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pictureT[j][i] = picture[i][j];
            }
        }
        picture = pictureT;
    }

    private void transposeEnergy() {
        double[][] energyT = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                energyT[j][i] = energy[i][j];
            }
        }
        energy = energyT;
    }

    private void transpose() {
        transposePicture();
        transposeEnergy();
        int tmp = rows;
        rows = cols;
        cols = tmp;
    }

    private void checkTranspose(boolean shouldTranspose) {
        if (isTranspose != shouldTranspose) {
            transpose();
            isTranspose = shouldTranspose;
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return doFindVerticalSeam(true);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return doFindVerticalSeam(false);
    }

    private int[] doFindVerticalSeam(boolean shouldTranspose) {
        checkTranspose(shouldTranspose);
        if (rows == 1) return new int[] { 0 };
        double[][] distTo = new double[rows][cols];
        int[][] pathTo = new int[rows][cols + 2];
        for (int i = 1; i < rows - 1; i++) {
            double leftTopFrom = Double.POSITIVE_INFINITY;
            double topFrom = 0, rightTopFrom = distTo[i - 1][0];
            int minPos;
            for (int j = 0; j < cols - 1; j++) {
                topFrom = rightTopFrom;
                rightTopFrom = distTo[i - 1][j + 1];
                minPos = j;
                if (leftTopFrom < topFrom) {
                    minPos = rightTopFrom < leftTopFrom ? j + 1 : j - 1;
                } else if (rightTopFrom < topFrom) {
                    minPos = j + 1;
                }
                distTo[i][j] = distTo[i - 1][minPos] + energy[i][j];
                pathTo[i][j] = minPos;
                leftTopFrom = topFrom;
            }
            topFrom = rightTopFrom;
            minPos = leftTopFrom < topFrom ? cols - 2 : cols - 1;
            distTo[i][cols - 1] = distTo[i - 1][minPos] + energy[i][cols - 1];
            pathTo[i][cols - 1] = minPos;
        }
        int[] seam = new int[rows];
        double[] search = distTo[rows - 2];
        int lowerBound = 0;
        double minVal = search[0];
        for (int i = 1; i < cols; i++) {
            if (search[i] < minVal) {
                minVal = search[i];
                lowerBound = i;
            }
        }
        seam[rows - 1] = lowerBound;
        seam[rows - 2] = lowerBound;
        for (int i = rows - 3; i >= 0; i--) {
            lowerBound = pathTo[i + 1][lowerBound];
            seam[i] = lowerBound;
        }
        return seam;
    }

    private void precheck(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("input null");
        if (cols <= 1 || rows != seam.length)
            throw new IllegalArgumentException("width <= 1 or seam length wrong");
        int pre = seam[0];
        for (int s : seam) {
            if (s < 0 || s >= cols || Math.abs(s - pre) > 1)
                throw new IllegalArgumentException("seam not valid on " + s + "," + pre);
            pre = s;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        doRemoveVerticalSeam(seam, true);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        doRemoveVerticalSeam(seam, false);
    }

    private void doRemoveVerticalSeam(int[] seam, boolean shouldTranspose) {
        checkTranspose(shouldTranspose);
        precheck(seam);
        for (int i = 0; i < rows; i++) {
            int srcPos = seam[i] + 1;
            System.arraycopy(energy[i], srcPos, energy[i], srcPos - 1, cols - srcPos);
            System.arraycopy(picture[i], srcPos, picture[i], srcPos - 1, cols - srcPos);
        }
        cols--;
        for (int row = 1; row < rows - 1; row++) {
            int col = seam[row];
            if (col > 0) energy[row][col - 1] = calculateEnergy(row, col - 1); // Left
            if (col < cols) energy[row][col] = calculateEnergy(row, col); // Right
        }
    }

    // unit testing (optional)

    // public static void main(String[] args) {
    //     Tester.testAll();
    // }
}
