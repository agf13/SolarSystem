package service;

import entities.AstronomicObject;
import exceptions.WrongInput;
import repository.Repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Service {
    Repository repository;

    public Service(String fileName) throws FileNotFoundException, WrongInput {
        repository = new Repository(fileName);
    }

    /**
        def: calculates and returns the position of the given AstronomicObject at the time given in the input file
        in: object (of type: AstronomicObject)
        out: an ArrayList of double, containning the x coordinate on pozition 0 and y coordinate on pozition 1

     */
    public ArrayList<Double> calculateNewCoordinates(AstronomicObject object){
        ArrayList<Double> result = new ArrayList<Double>();

        //if object has the direction as static, it means it does not revolve around anything. For this case, I presume
        //the object is not moving
        if(object.getDirection() == AstronomicObject.Direction.STATIC){
            result.add(object.getX());
            result.add(object.getY());
            return result;
        }

        //calculate how much the object has moved on its orbit
        double distanceCovered = object.getVelocity() * (AstronomicObject.getTargetTime() - AstronomicObject.getReferenceTime());


        //calculate how much the object moved relative to its initial position
        double relativeDistance = distanceCovered - (int)(distanceCovered/object.getTrajectoryDistance()) * object.getTrajectoryDistance();

        //calculate the angle at which the satelite is now
        double initialAngle = getInitialAngle(object);
        double finalAngle = getFinalAngle(object, initialAngle, relativeDistance);

        //calculate the target x and y coordinates (coordinates of the parent + sin(finalAngle) pr cos(finalAngle))
        double sign = 1;
        if(object.getDirection() == AstronomicObject.Direction.CLOCKWISE){
            sign = -1;
        }
        AstronomicObject parent = repository.get(object.getParentName());
        double targetX = parent.getX() + Math.cos(finalAngle) * sign * object.getDistanceToParent();
        double targetY = parent.getY() + Math.sin(finalAngle) * sign * object.getDistanceToParent();

        //return pair
        result.add(targetX);
        result.add(targetY);
        return result;
    }

    /*
        def: calculates the initial angle of the satelite
     */
    public double getInitialAngle(AstronomicObject object){
        AstronomicObject parent = repository.get(object.getParentName());

        //get the arcsin value of the satelite in degrees, given the coordinates of the parent as the origin of a trigonometric circle
        double distanceOnY = object.getY() - parent.getY();
        double arcsinValue = distanceOnY / object.getDistanceToParent();
        double initialAngle = Math.toDegrees(Math.asin(arcsinValue));

        //find if the satelite is at the right or left of the parent (imagining a trigonometric circle)
        if(parent.getX() <= object.getX()){
            //it means the satelite is at the right, or at the same x-coordinate
            return initialAngle;
        }
        else{
            //at the left side, we must substract the calulated angle from 180 degrees
            double pi = 180;
            return (pi - initialAngle);
        }
    }

    /*
        def: calculates the angle of the satelite and returns it
     */
    public double getFinalAngle(AstronomicObject object, double initialAngle, double relativeDistance){
        double angleToAdd = (relativeDistance / object.getTrajectoryDistance()) * 360;
        if(angleToAdd >= 360)
            return ((initialAngle + angleToAdd) - 360);
        return angleToAdd;
    }

    /*
        def: returns all AstronomicObject entities
     */
    public ArrayList<AstronomicObject> getAll(){
        return repository.getAll();
    }
}
