package pattern.recognition.entity;

/*
* This factory allows you to create the correct line class
* */
public class LineFactory {
    private LineFactory() {
        // Do Nothing
    }

    public static ILine createLine(Point a, Point b) {
        double deltaX = a.getX() - b.getX();
        if( deltaX == 0 ){
            return new StraightLineParallelXAxis(a, b);
        }
        return new StraightLine(a, b);
    }
}
