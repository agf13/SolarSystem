package tests;

import entities.AstronomicObject;
import exceptions.WrongInput;
import repository.Repository;
import service.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class ServiceTests {
    String fileName;
    Service service;

    public ServiceTests(String fileName){
        this.fileName = fileName;

        try {
            service = new Service(fileName);
        }
        catch(WrongInput e){
            System.out.println(e.getMessage());
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void getAll_noInput_returnsObjects(){
        assert (service.getAll().size() == 4);
//        double T0 = AstronomicObject.getReferenceTime();
//        double T1 = AstronomicObject.getTargetTime();
//        System.out.println(Double.toString(T0) + " " + Double.toString(T1));
//        service.getAll().stream().forEach(x -> System.out.println(x.toString()));
    }

    public void initialAngle_objectOnSameYCoordinate_0degrees(){
        AstronomicObject object = service.getAll().get(0); //the S1 object from resources/testConfiguration.txt
        assert(service.getInitialAngle(object) == 0);
    }
    public void initialAngle_objectOnSameXCoordinate_90degrees(){
        AstronomicObject object = service.getAll().get(1); //the S2 object from resources/testConfiguration.txt
        assert(service.getInitialAngle(object) == 90);
    }

    public void finalAngle_firstBodyFromFIle_0degres(){
        AstronomicObject object = service.getAll().get(0); //the S1 object from resources/testConfiguration.txt

        double distanceCovered = object.getVelocity() * (AstronomicObject.getTargetTime() - AstronomicObject.getReferenceTime());
        double relativeDistance = distanceCovered - (int)(distanceCovered/object.getTrajectoryDistance()) * object.getTrajectoryDistance();

        int angle = (int)(service.getFinalAngle(object, service.getInitialAngle(object), relativeDistance));
        assert(angle == 0 || angle == 1 || angle == 360 || angle == 359); //1 degree error from aproximation to int
    }

    public void finalAngle_secondBodyFromFile_90degres(){
        AstronomicObject object = service.getAll().get(1); //the S1 object from resources/testConfiguration.txt

        double distanceCovered = object.getVelocity() * (AstronomicObject.getTargetTime() - AstronomicObject.getReferenceTime());
        double relativeDistance = distanceCovered - (int)(distanceCovered/object.getTrajectoryDistance()) * object.getTrajectoryDistance();

        int angle = (int)(service.getFinalAngle(object, service.getInitialAngle(object), relativeDistance));
        assert(angle == 90 || angle == 89 || angle == 91); //1 degree error from aproximation to int
    }

    public void allTests(){
        getAll_noInput_returnsObjects();
        initialAngle_objectOnSameXCoordinate_90degrees();
        initialAngle_objectOnSameYCoordinate_0degrees();
        finalAngle_firstBodyFromFIle_0degres();
        finalAngle_secondBodyFromFile_90degres();
    }
}
