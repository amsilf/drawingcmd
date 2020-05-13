package com.credit.swiss.drawing.dataobjects;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Point)) {
            return false;
        }

        Point point = (Point) obj;
        return x == point.getX() && y == point.getY();
    }
}
