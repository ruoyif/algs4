import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private final LineSegment[] lineSegments;
    private final ArrayList<LineSegment> lines;

    public BruteCollinearPoints(Point[] points) {
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
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        this.points = pointsCopy;
        lines = new ArrayList<>();
        iteration();
        lineSegments = new LineSegment[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            lineSegments[i] = lines.get(i);
        }

    }
    private void iteration() {
        int len = points.length;
        for (int i = 0; i < len-3; i++) {
            for (int j = i+1; j < len-2; j++) {
                for (int k = j+1; k < len-1; k++) {
                    for (int p = k+1; p < len; p++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                            double tempS = points[j].slopeTo(points[k]);
                            if (tempS == points[k].slopeTo(points[p])) {
                                lines.add(new LineSegment(points[i], points[p]));
                                }
                            }

                        }
                    }
                }

            }
    }

    public int numberOfSegments() {
        return lines.size();
    }
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

}
