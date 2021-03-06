import java.util.Comparator;
import java.lang.Math;
import java.util.Arrays;

public class ivq6 {

    public static class Point implements Comparable<Point> {
        private final int mX;
        private final int mY;

        public Point(int x, int y) {
            this.mX = x;
            this.mY = y;
        }

        public String toString() {
            return "Point <X,Y> :: <" + this.mX + ", " + this.mY + ">";
        }

        public boolean equals(Point p) {
            return this.compareTo(p) == 0;
        }

        public int compareTo(Point p) {
            // -1 : this < p
            //  0 : this == p
            //  1 : this > p

            if (this.mY == p.mY) {
                if (this.mX == p.mX) {
                    return 0;
                }
                else if (this.mX > p.mX) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (this.mY > p.mY) {
                return 1;
            }
            else {
                return -1;
            }
        }

        public double slope(Point p) {
            int diffX = p.mX - this.mX;
            int diffY = p.mY - this.mY; 

            if (diffX == 0) {
                if (diffY >= 0) {
                    return Double.POSITIVE_INFINITY;
                }
                else {
                    return Double.NEGATIVE_INFINITY;
                }
            }

            if (diffY == 0) {
                return 0.0;
            }

            return ((double)diffY) / ((double)(diffX));
        }

        public Comparator<Point> slopeOrder() {
            return new CollinearOrder();
        }

        private class CollinearOrder implements Comparator<Point> {
            public int compare(Point a, Point b) {
                // -1 : slope (this, a) < slope (this, b)
                //  0 : this === p
                //  1 : slope (this, a) > slope (this, b)
                double slopeA = Point.this.slope(a);
                double slopeB = Point.this.slope(b);

                if (slopeA == slopeB) {
                    // Roughly equal.
                    return 0;
                }
                else if (slopeA < slopeB) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        }
    }

    public static int getMaxCollinearPoints(Point [] points) {
        int maxCollinearPoints = 0;
        
        if (points == null || points.length == 0) {
            return 0;
        }
        else if (points.length == 1) {
            return 1;
        }
        else if (points.length == 2) {
            return 1;
        }

        // Should sort based off the comparable.
        Arrays.sort(points);

        // Now the points should be ordered based off of y-axis order then x-axis as the minor.
        Point pivot = null;
        for (int pivotIndex = 0; pivotIndex < points.length; pivotIndex++) {
            pivot = points[pivotIndex];

            // Now need to build the rest of the points, excluding the pivot.
            Point [] subPoints = new Point[points.length - 1];
            for (int subPointIndex = 0; subPointIndex < pivotIndex; subPointIndex++) {
                subPoints[subPointIndex] = points[subPointIndex];
            }

            for (int subPointIndex = pivotIndex + 1; subPointIndex < points.length; subPointIndex++) {
                subPoints[subPointIndex - 1] = points[subPointIndex];
            }

            // At this point the subpoints array has been built, sort with the comparator.
            Arrays.sort(subPoints, pivot.slopeOrder());

            // Now need to count the number of collinear points (don't care about total only about the longest found)
            double slopeA = 0.0, slopeB = 0.0;
            int count = 2;
            maxCollinearPoints = 2;
            slopeA = pivot.slope(subPoints[0]);
            for (int index = 1; index < subPoints.length; index++) {
                slopeB = pivot.slope(subPoints[index]);
                if (Math.abs(slopeA - slopeB) < 0.000001) {
                    // Same slope.
                    count++;

                    // Compare the number of collinear points to the max and if greater save.
                    if (count > maxCollinearPoints) {
                        maxCollinearPoints = count;
                    }
                }
                else {
                    count = 2;
                }
                slopeA = slopeB;
            }    
        }

        return maxCollinearPoints;
    }

    public static void main(String [] args) {
        Point [] points = new Point [100];

        for (int rowIndex = 10; rowIndex > 0; rowIndex--) {
            for (int colIndex = 10; colIndex > 0; colIndex--) {
                points[(10 * (rowIndex - 1)) + colIndex - 1] = new Point(rowIndex - 1, colIndex - 1);
            }
        }

        // Arrays.sort(points);
        // Ensuring that the sort is working properly.
        // assert (points[0].equals(new Point(0,0)));

        System.out.println(getMaxCollinearPoints(points));
    }
}