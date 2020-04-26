package console;

import entities.AstronomicObject;
import exceptions.WrongInput;
import service.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class UIConsole {
    String fileName;
    Service service;

    public UIConsole(String fileName){
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


    public void run(){
        if(service == null){
            return;
        }

        service.getAll().stream().forEach(element -> {
            ArrayList<Double> resultCoordinates = new ArrayList<Double>();
            resultCoordinates = service.calculateNewCoordinates(element);
            double newX = resultCoordinates.get(0);
            double newY = resultCoordinates.get(1);
            System.out.println(element.toString());
            System.out.println(element.getName() + ": newX = " + Double.toString(newX) + ", newY = " + Double.toString(newY));
        });
    }


}
