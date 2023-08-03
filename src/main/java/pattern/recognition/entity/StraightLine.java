package pattern.recognition.entity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
* This class represent a generic straight line
* */
public class StraightLine implements ILine {
    // angular coefficient
    public final BigDecimal m;
    // the y-intercept of the line
    public final BigDecimal q;
    private static final MathContext mc = new MathContext(7, RoundingMode.FLOOR);

    private final Set<Point> points = new HashSet<>();

    public StraightLine(Point a, Point b) {
        this.m = this.getAngularCoefficient(a, b);
        this.q = this.calculateYIntercept(a);
        points.add(a);
        points.add(b);
    }

    private BigDecimal getAngularCoefficient(Point a, Point b) {
        return BigDecimal.valueOf(a.getY() - b.getY())
                .divide(BigDecimal.valueOf(a.getX() - b.getX()), mc);
    }

    private BigDecimal calculateYIntercept(Point a) {
        // a.getY() - this.m*a.getX()
        return BigDecimal.valueOf(a.getX())
                .multiply(this.m, mc)
                .negate(mc)
                .add(BigDecimal.valueOf(a.getY()), mc);
    }

    @Override
    public boolean belongsToTheLine(Point x) {
        BigDecimal y = BigDecimal.valueOf(x.getY());
        return this.m
                .multiply(BigDecimal.valueOf(x.getX()), mc)
                .add(this.q, mc)
                .compareTo(y) == 0;
    }

    @Override
    public void addIfBelongs(Point x) {
        if(this.belongsToTheLine(x)) {
            points.add(x);
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
        if (!(o instanceof StraightLine)) return false;
        StraightLine that = (StraightLine) o;
        return Objects.equals(m, that.m) && Objects.equals(q, that.q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, q);
    }

    @Override
    public String toString() {
        return points.toString();
    }
}
