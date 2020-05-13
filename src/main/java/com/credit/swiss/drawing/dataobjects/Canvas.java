package com.credit.swiss.drawing.dataobjects;

public class Canvas {

    private char[][] canvas;

    private static final int BORDER_HEIGHT = 2;
    private static final int BORDER_WEIGHT = 2;

    private static final char HORIZONTAL_LINE = '-';
    private static final char VERTICAL_LINE = '|';
    private static final char EMPTY_SPACE = ' ';

    private int width = -1;
    private int height = 1;

    public Canvas(int w, int h) {
        width = w;
        height = h;

        canvas = new char[h + BORDER_HEIGHT][w + BORDER_WEIGHT];
        drawBordersAndClean();
    }

    private void drawBordersAndClean() {

        // clean canvas
        for (int i = 0; i < height + BORDER_HEIGHT; i++) {
            for (int j = 0; j < width + BORDER_WEIGHT; j++) {
                canvas[i][j] = EMPTY_SPACE;
            }
        }

        // draw horizontal lines
        for (int i = 0; i < width + BORDER_WEIGHT; i++) {
            canvas[0][i] = HORIZONTAL_LINE;
            canvas[height + 1][i] = HORIZONTAL_LINE;
        }

        // draw vertical lines
        for (int i = 1; i < height + 1; i++) {
            canvas[i][0] = VERTICAL_LINE;
            canvas[i][width + 1] = VERTICAL_LINE;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void drawDot(int x, int y, char symbol) {
        if (isDrawingNotHitBorder(x, y)) {
            canvas[y][x] = symbol;
        }
    }

    public boolean isSpaceIsEmpty(int x, int y) {
        return canvas[y][x] == EMPTY_SPACE;
    }

    public boolean isPointOutsideCanvas(int x, int y) {
        return x < 0 || x > width || y < 0 || y > height;
    }

    public char getDotColour(int x, int y) {
        if (isPointOutsideCanvas(x, y)) {
            return Character.MAX_HIGH_SURROGATE;
        }

        return canvas[y][x];
    }

    public boolean isDotAvailableForColouring(int x, int y) {
        return isDrawingNotHitBorder(x, y) && ( isSpaceIsEmpty(x, y) || getDotColour(x, y) != 'x' );
    }

    public boolean drawDotIfSpaceIsEmpty(int x, int y, char symbol) {
        if (isDotAvailableForColouring(x, y)) {
            canvas[y][x] = symbol;
            return true;
        }
        return false;
    }

    public boolean isDrawingNotHitBorder(int x, int y) {
        char colour = getDotColour(x, y);
        if (colour != Character.MAX_HIGH_SURROGATE) {
            return canvas[y][x] != VERTICAL_LINE && canvas[y][x] != HORIZONTAL_LINE;
        }

        return false;
    }

    public void paint() {
        int i = 0;
        while (i < canvas.length) {
            for (int j = 0; j < canvas[i].length; j++) {
                System.out.print(canvas[i][j]);
            }
            System.out.println();
            i++;
        }
    }

}
