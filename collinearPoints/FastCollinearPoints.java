import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;
    private final ArrayList<Point> endps;
    private final ArrayList<Double> slopes;


    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException();
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException();
            }
        }
        Point[] copy = points.clone();
        Arrays.sort(copy);
        ArrayList<LineSegment> lines = new ArrayList<>();
        endps = new ArrayList<>();
        slopes = new ArrayList<>();
        int len = copy.length;
        for (int i = 0; i < len - 3; i++) {
            Point p = copy[i];
            Comparator<Point> c = p.slopeOrder();
            Point[] a = copy.clone();
            sort(copy.clone(), a, i + 1, len - 1, c);
            int count = 1;
            double tempS = p.slopeTo(a[i + 1]);
            for (int k = i + 2; k < len; k++) {
                if (p.slopeTo(a[k]) == tempS) {
                    count++;
                } else {
                    if (count >= 3) {
                        if (check(a[k - 1], tempS)) {
                            lines.add(new LineSegment(p, a[k - 1]));
                            endps.add(a[k - 1]);
                            slopes.add(tempS);
                        }
                    }
                    count = 1;
                    tempS = p.slopeTo(a[k]);

                }
            }
            if (count >= 3) {
                if (check(a[len - 1], tempS)) {
                    lines.add(new LineSegment(p, a[len - 1]));
                    endps.add(a[len - 1]);
                    slopes.add(tempS);
                }
            }
        }
        lineSegments = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            lineSegments[i] = lines.get(i);
        }

    }

    private boolean check(Point p, double s) {
        for (int m = 0; m < endps.size(); m++) {
            if (endps.get(m) == p && (slopes.get(m) == s))
            {
                return false;
            }

        }
        return true;
    }

    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> c) {
//        for (int k = lo; k <= hi; k++)
//            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                aux[k] = a[j++];
            else if (j > hi)
                aux[k] = a[i++];
            else if (c.compare(a[i], a[j]) > 0)
                aux[k] = a[j++];
            else
                aux[k] = a[i++];
        }

    }
    private static void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> c) {
        if (hi <= lo) {
            return;

        }
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid, c);
        sort(aux, a, mid + 1, hi, c);
        if (c.compare(a[mid+1], a[mid]) >= 0)
        {
            for (int i = lo ; i <= hi; i++)
                aux[i] = a[i];
            return;
        }
        merge(a, aux, lo, mid, hi, c);
    }

    public int numberOfSegments() {
        return lineSegments.length;

    }

    public LineSegment[] segments() {
        return lineSegments.clone();

    }
    public static void main(String[] args) {

//        args = new String[]{"insert file path here"};
        In in = new In("9.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
