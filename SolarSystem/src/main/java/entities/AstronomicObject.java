package entities;

import com.sun.org.apache.bcel.internal.util.BCELComparator;

import java.awt.*;

public class AstronomicObject {
    static double gravitationalConstant = 0.00000000006674; //value givent in (cube meters)/(kilograms * (squared seconds)) or (m^3)/(kg * (s^2))

    String name;
    String parentName; //the name of the origin/parent
    double x,y;
    double radius, mass;
    Direction direction;

    double velocity;
    double distanceToParent;
    double trajectoryDistance;

    enum Direction{
        CLOCKWISE,
        COUNTERCLOCKWISE,
        STATIC, //this is for those origins which are not satellites as well, or for those which does not have an origin assigned to them
    }

    /*
        def: default constructor
    */
    public AstronomicObject(){
        this.name = "";
        this.parentName = "";
        this.x = 0;
        this.y = 0;
        this.radius = 0;
        this.mass = 0;
        this.direction = Direction.STATIC;

        this.velocity = 0;
        this.distanceToParent = 0;
        this.trajectoryDistance = 0;
    }

    /*
        def: constuctor for astronomic objects with every attribute except the direction known
    */
    public AstronomicObject(String name, double x, double y, double radius, double mass){
        this.name = name;
        this.parentName = "";
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.direction = Direction.STATIC;

        this.velocity = 0;
        this.distanceToParent = 0;
        this.trajectoryDistance = 0;
    }

    /*
        def: constructor for astronomic objects if we also know their direction from the beginning
    */
    public AstronomicObject(String name, double x, double y, double radius, double mass, Direction direction){
        this.name = name;
        this.parentName = "";
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.direction = direction;

        this.velocity = 0;
        this.distanceToParent = 0;
        this.trajectoryDistance = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getDistanceToParent() {
        return distanceToParent;
    }

    public void setDistanceToParent(double distanceToParent) {
        this.distanceToParent = distanceToParent;
    }

    public static double getGravitationalConstant() {
        return gravitationalConstant;
    }

    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setTrajectoryDistance(double trajectoryDistance){
        this.trajectoryDistance = trajectoryDistance;
    }

    public double getTrajectoryDistance() {
        return trajectoryDistance;
    }

    public String toString()
    {
        return ("Planet: " + name + ", x: " + x + ", y: " + y +
                ", radius: " + radius + ", mass: " + mass + ", direction: " + direction + ", velocity: " + velocity +
                ", distanceToOrigin: " + distanceToParent + ", trajectoryDistance: " + trajectoryDistance +
                ", parent planet: " + parentName);
    }
}
