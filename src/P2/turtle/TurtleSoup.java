/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for(int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
            //throw new RuntimeException("implement me!");
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
       // throw new RuntimeException("implement me!");
        return 180.00-360.00/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        //throw new RuntimeException("implement me!");
        for(int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180.00-calculateRegularPolygonAngle(sides));  //注意是右转，所以角度实际上是180-内角
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
        double degree = Math.toDegrees(Math.atan2(targetY-currentY,targetX-currentX));  //当前的degree是连线与x轴的夹角
        degree = (90-degree)-currentBearing;   //转换成与y正向的夹角，再减去初始的面向角度
        if(degree<0) degree +=360;
        return degree;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        //throw new RuntimeException("implement me!");
        ArrayList<Double> bearingList = new ArrayList<Double>();  //每次转后与y轴正方向的夹角
        ArrayList<Double> res = new ArrayList<Double>();
        for (int i = 0; i < xCoords.size()-1; i++) {
            bearingList.add(calculateBearingToPoint(0, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1)));
        }
        res.add(0,bearingList.get(0));
        for (int i = 1; i < xCoords.size()-1; i++) {
            res.add(i,bearingList.get(i)+360.00-bearingList.get(i-1));
            if(bearingList.get(i)>360.00)
                bearingList.set(i, bearingList.get(i-1) - 360);
        }
        return res;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
        Set<Point> shellPoint = new HashSet<Point>();
        Point minPoint = null;
        double nowBearing;
        double nextBearing;
        double preBearing;
        double nextLength;
        Point nowPoint;
        Point nextPoint = null;
//    	Iterator<Point> it = points.iterator();
        if(!points.isEmpty())
        {
            //元素小于3个时，必是凸包直接返回
            if(points.size() <=3)
                return points;

            //求最左下元素
            for(Point point : points)
            {
                if(minPoint == null){
                    minPoint = point;
                    continue;
                }
                if(minPoint.x() > point.x())
                    minPoint = point;
                else if(minPoint.x() == point.x())
                {
                    if(point.y() < minPoint.y())
                        minPoint = point;
                }
            }

            shellPoint.add(minPoint); //最左下元素定时凸包元素，加入集合
            nowPoint = minPoint;
            preBearing = 0; //之前凸包元素指向最近凸包元素的角度（相对与y轴顺时针）
            while(true)
            {
                nextBearing = 360;
                nextLength = Double.MAX_VALUE;
                for(Point point : points)
                {
                    if(point.equals(nowPoint))
                        continue;
                    nowBearing = calculateBearingToPoint(preBearing,(int)nowPoint.x(),(int)nowPoint.y(),(int)point.x(),(int)point.y());
                    if(nextBearing == nowBearing){
                        if(nextLength < (Math.pow(point.x()-nowPoint.x(), 2)+Math.pow(point.y()-nowPoint.y(), 2)))
                        {
                            nextLength = Math.pow(point.x()-nowPoint.x(), 2)+Math.pow(point.y()-nowPoint.y(), 2);
                            nextPoint = point;
                        }
                    }
                    else if(nextBearing > nowBearing) {
                        nextLength = Math.pow(point.x()-nowPoint.x(), 2)+Math.pow(point.y()-nowPoint.y(), 2);
                        nextBearing = nowBearing;
                        nextPoint = point;
                    }
                }
                if(minPoint.equals(nextPoint))
                {
                    break;
                }
                nowPoint = nextPoint;
                preBearing += nextBearing;
                shellPoint.add(nextPoint);
            }

        }
        return shellPoint;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");


    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        drawRegularPolygon(turtle,5,100);

        // draw the window
        turtle.draw();
    }

}
