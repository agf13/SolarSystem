package entities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class AstronomicObject {
    //static BigDecimal gravitationalConstant = new BigDecimal(0.00000000006674, MathContext.DECIMAL32); //value givent in (cube meters)/(kilograms * (squared seconds)) or (m^3)/(kg * (s^2))
    static double referenceTime = -1; //-1 means uninitialized yet. This is the reference time of all the AstronomicObject entities
    static double targetTime = -1; //-1 means uninitialized yet. This is the time for which we want the position of the astronomic body

    String name;
    String parentName; //the name of the origin/parent
    double x,y;
    double radius; //in meters
    double mass;  //in kilograms
    Direction direction; //the direction in which the satelite moves

    double velocity; //in meters/second
    double distanceToParent; //the distance from the center of the satelite to the center of its parent
    double trajectoryDistance; //the distance of the satelite trajectory

    public enum Direction{
        CLOCKWISE,
        COUNTERCLOCKWISE,
        STATIC, //this is for those origins which are not satellites as well, or for those which does not have an origin assigned to them
    }

    /**
        def: default constructor
    */
    public AstronomicObject(){
        this.name = "-";
        this.parentName = "-";
        this.x = 0;
        this.y = 0;
        this.radius = 0;
        this.mass = 0;
        this.direction = Direction.STATIC;

        this.velocity = 0;
        this.distanceToParent = 0;
        this.trajectoryDistance = 0;
    }

    /**
        def: constuctor for astronomic objects with every attribute except the direction known
    */
    public AstronomicObject(String name, double x, double y, double radius, double mass){
        this.name = name;
        this.parentName = "-";
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.direction = Direction.STATIC;

        this.velocity = 0;
        this.distanceToParent = 0;
        this.trajectoryDistance = 0;
    }

    /**
        def: constructor for astronomic objects if we also know their direction from the beginning
    */
    public AstronomicObject(String name, double x, double y, double radius, double mass, Direction direction){
        this.name = name;
        this.parentName = "-";
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public void setDirection(String directionName) {
        if(directionName == "CLOCKWISE" || directionName == "CW")
            this.direction = Direction.CLOCKWISE;
        else if(directionName == "COUNTERCLOCKWISE" || directionName == "CCW")
            this.direction = Direction.COUNTERCLOCKWISE;
        else if(directionName == "STATIC"){
            this.direction = Direction.STATIC;
        }
    }

    public double getDistanceToParent() {
        return distanceToParent;
    }

    public void setDistanceToParent(double distanceToParent) {
        this.distanceToParent = distanceToParent;
    }

    //public static BigDecimal getGravitationalConstant() {
    //    return gravitationalConstant;
    //}

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

    /**
        def: check if 2 astronomic objects have the same name
        in: other (of type AstronomicObject)
    */
    public boolean equalName(AstronomicObject other){
        if(this.name == other.name)
            return true;
        return false;
    }

    public static double getReferenceTime() {
        return referenceTime;
    }

    public static void setReferenceTime(double referenceTime) {
        AstronomicObject.referenceTime = referenceTime;
    }

    public static double getTargetTime() {
        return targetTime;
    }

    public static void setTargetTime(double targetTime) {
        AstronomicObject.targetTime = targetTime;
    }

    public String toString()
    {
        return ("Planet: " + name + ", x: " + x + ", y: " + y +
                ", radius: " + radius + ", mass: " + mass + ", direction: " + direction + ", velocity: " + velocity +
                ", distanceToOrigin: " + distanceToParent + ", trajectoryDistance: " + trajectoryDistance +
                ", parent object: " + parentName);
    }
}
