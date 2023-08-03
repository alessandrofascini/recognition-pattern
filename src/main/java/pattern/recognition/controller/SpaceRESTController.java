package pattern.recognition.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pattern.recognition.entity.ILine;
import pattern.recognition.entity.LineFactory;
import pattern.recognition.entity.Point;
import pattern.recognition.exceptions.ExistingPointException;
import pattern.recognition.exceptions.InvalidCoordinatesException;

import java.util.*;

@RestController
@RequestMapping("/")
public class SpaceRESTController {
    private static final Logger logger = LogManager.getLogger(SpaceRESTController.class);

    private static final List<Point> space = new ArrayList<>();
    private static final Map<Integer, ILine> lines = new HashMap<>();

    @PostMapping(value = "/point", consumes = "application/json")
    public void addPoint(@RequestBody Point point) {
        logger.info("Requested to add a point: {}", point);

        // Check if co-ordinates of the points are valid
        if(Double.isInfinite(point.getX()) || Double.isInfinite(point.getY())) {
            logger.warn("Invalid point co-ordinates! {}", point);
            throw new InvalidCoordinatesException("Invalid point co-ordinates!");
        }
        // Check whether the point already exists
        if(space.contains(point)) {
            logger.warn("Point already exists! {}", point);
            throw new ExistingPointException("Cannot add this point " + point + "! Already exists!");
        }

        // visitedPoint is a set of points that already checks whether the new point belongs to the same line
        Set<Point> visitedPoint = new HashSet<>();

        for (Point value : space) {
            if(visitedPoint.contains(value)) {
                continue;
            }
            visitedPoint.add(value);
            ILine line = LineFactory.createLine(value, point);
            lines.merge(line.hashCode(), line, (line1, line2) -> {
                visitedPoint.addAll(line1.getPoints());
                line1.merge(line2);
                return line1;
            });
        }

        space.add(point);
        logger.info("Point added successfully");
    }

    @GetMapping(value = "/space", produces = "application/json")
    public List<Point> getSpace() {
        logger.info("Get space");
        return space;
    }

    @GetMapping(value="/lines/{n}", produces = "application/json")
    public List<Set<Point>> getLines(@PathVariable("n") int n) {
        logger.info("Get all lines passing through at least {} points", n);

        if(space.size() < n || space.size() == 1) {
            logger.warn("Not enough points to find a line");
            return Collections.emptyList();
        }

        List<Set<Point>> result = new ArrayList<>();
        lines.forEach((integer, line) -> {
            if(line.size() >= n) {
                result.add(line.getPoints());
            }
        });

        logger.info("Founded {} lines", result.size());

        return result;
    }

    @DeleteMapping(value = "/space")
    public void clearSpace() {
        space.clear();
        lines.clear();
        logger.info("Cleared space");
    }
}
