package pattern.recognition.entity;

import java.util.Set;

public interface ILine {
    void addIfBelongs(Point x);
    Set<Point> getPoints();
    int size();
    boolean belongsToTheLine(Point x);
    ILine merge(ILine iLine);
}
