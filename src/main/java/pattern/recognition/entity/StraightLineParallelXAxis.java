package pattern.recognition.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
* This class represents a straight line parallel to the x-axis
* */
public class StraightLineParallelXAxis implements ILine {
    private double x;

    private final Set<Point> points = new HashSet<>();

    public StraightLineParallelXAxis(Point a, Point b) {
        double deltaX = a.getX() - b.getX();
        if (deltaX == 0.0) {
            this.x = a.getX();
            this.points.add(a);
            this.points.add(b);
        }
    }

    @Override
    public void addIfBelongs(Point x) {
        if (this.belongsToTheLine(x)) {
            this.points.add(x);
        }
    }

    @Override
    public Set<Point> getPoints() {
        return this.points;
    }

    @Override
    public int size() {
        return this.points.size();
    }

    @Override
    public boolean belongsToTheLine(Point x) {
        return this.x == x.getX();
    }

    @Override
    public ILine merge(ILine iLine) {
        if(this.hashCode() != iLine.hashCode()) {
            return this;
        }
        this.points.addAll(iLine.getPoints());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StraightLineParallelXAxis)) return false;
        StraightLineParallelXAxis that = (StraightLineParallelXAxis) o;
        return Double.compare(x, that.x) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x);
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
