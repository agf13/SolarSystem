package repository;

import entities.AstronomicObject;
import exceptions.WrongInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Repository {
    ArrayList<AstronomicObject> repositoryOfBodies;
    String filePath;

    /*
        def: Constructor of the repository.
            The fileName should be found in the resources directory (src\main\resources)
    */
    public Repository(String fileName) throws FileNotFoundException, WrongInput{
        this.repositoryOfBodies = new ArrayList<AstronomicObject>();
        this.filePath = "src\\main\\resources\\" + fileName;
        readFromFile();
    }

    /**
        def: it reads the information from the file with the name given in the constructor
            The information read is:
                - first the referrenceTime-seconds and the targetTime-seconds (the one for which we need to calculate the position);
                - then the astronomic objects with their attributes (name, position-meters, radius-meters, mass-kilograms)
                - finally the relations between the objects (satelite name, parent name, direction of movement)
     */
    private void readFromFile() throws FileNotFoundException, WrongInput{
        File file = new File(filePath);
        try {
            //get the file
            Scanner fileScanner = new Scanner(file);

            //read the time values T0 and T1
            if(!fileScanner.hasNextLine()) {
                throw new WrongInput();
            }
            String[] timeValues = fileScanner.nextLine().split(" ");
            if(timeValues.length != 2){
                throw new WrongInput();
            }
            double T0 = Double.parseDouble(timeValues[0]);
            double T1 = Double.parseDouble(timeValues[1]);
            AstronomicObject.setReferenceTime(T0);
            AstronomicObject.setTargetTime(T1);

            //read the rest of the lines
            while(fileScanner.hasNextLine()){
                String[] line = fileScanner.nextLine().split(" ");
                //when the line contain information about astronomic bodies, the number of attributes should be 4
                if(line.length == 5){
                    String name = line[0];
                    double x = Double.parseDouble(line[1]);
                    double y = Double.parseDouble(line[2]);
                    double radius = Double.parseDouble(line[3]);
                    double mass = Double.parseDouble(line[4]);
                    AstronomicObject object = new AstronomicObject(name, x, y, radius, mass);
                    repositoryOfBodies.add(object);
                }
                //when the line contains informations about the relation between objects, the number of attributes should be 3
                else if(line.length == 3){
                    String nameSatelite = line[0];
                    String nameOrigin = line[1];
                    String direction = line[2];
                    makeTheConnection(nameSatelite, nameOrigin, direction);
                }
                else{
                    throw new WrongInput("Wrong number of attributes on the line!");
                }
            }
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found!");
        }
        catch(WrongInput e){
            throw new WrongInput(e.getMessage());
        }
        catch(NumberFormatException e){
            throw new WrongInput("Wrong input, number is not a double!");
        }

    }

    /*
        def: it sets the fields which requiers a known parent to the AstronomicObject with name = sateliteName
        in: satelite(of type: String)
            origin(of type: String)
            direction(of type: String)
        out: -
    */
    public void makeTheConnection(String sateliteName, String originName, String direction) throws WrongInput{
        //get the object
        AstronomicObject object = get(sateliteName);
        if(object == null || get(originName) == null)
            return;

        //set the object parent name
        object.setParentName(originName);

        //set the object distanceToParent and trajectoryDistance
        calculateDistanceToParent(object);

        //set the object velocity
        calculateVelocity(object);

        //set the object direction
        if(direction.equals("CLOCKWISE") || direction.equals("CW")){
            object.setDirection(AstronomicObject.Direction.CLOCKWISE);
        }
        else if(direction.equals("COUNTERCLOCKWISE") || direction.equals("CCW")){
            object.setDirection(AstronomicObject.Direction.COUNTERCLOCKWISE);
        }
        else if(direction.equals("STATIC")){
            object.setDirection(AstronomicObject.Direction.STATIC);
        }
        else{
            throw new WrongInput("The given direction is not valid");
        }

    }

    /*
        def: sets the distanceToParent field of the AstronomicObject, given the parentName
        in: object(of type: AstronomicObject)
            parentName(of type: String)
        out: -
    */
    private void calculateDistanceToParent(AstronomicObject object){
        String parentName = object.getParentName();

        double childX = object.getX();
        double childY = object.getY();

        AstronomicObject parent = get(parentName);
        double parentX = parent.getX();
        double parentY = parent.getY();

        double distanceOnX = childX - parentX;
        double distanceOnY = childY - parentY;
        double distance = Math.sqrt(distanceOnX*distanceOnX + distanceOnY*distanceOnY);

        object.setDistanceToParent(distance);

        double trajectoryDistance = Math.PI * 2 * distance;
        object.setTrajectoryDistance(trajectoryDistance);
    }

    /*
        def: sets the velocity field of the given AstronomicObject
     */
    private void calculateVelocity(AstronomicObject object){
        String parentName = object.getParentName();

        //BigDecimal gravitationalConstant = AstronomicObject.getGravitationalConstant();
        double radius  = object.getDistanceToParent();
        double parentMass = get(parentName).getMass();
//        BigDecimal radius = new BigDecimal(object.getDistanceToParent());
//        BigDecimal parentMass = new BigDecimal(object.getMass());

        //double velocity = Math.sqrt((parentMass / radius) * );
        double velocitySquared = parentMass/radius;
        BigDecimal velocitySquaredBig = new BigDecimal(velocitySquared);
        BigDecimal tenToEleven = new BigDecimal("100000000000");
        velocitySquaredBig.divide(tenToEleven);
//        velocitySquared = velocitySquared * 6.674 /100000000 /1000; //6.674 * 10^(-11) - value form gravitationl constant
        velocitySquared = tenToEleven.doubleValue();

        double velocity = Math.sqrt(velocitySquared);

        object.setVelocity(velocity);
    }

    /**
        def: adds an object of type AstronomicObject to the repo
        in: object (of type: AstronomicObject)
        out: 1 - added | 0 - dublicate found
    */
    public int add(AstronomicObject object){
        if(occurencesFound(object) != 0){
            return 0;
        }
        repositoryOfBodies.add(object);
        return 1;
    }

    /**
        def: returns the number of occurences of the given astronomic object
        in: object (of type: AstronimicObject)
        out: number of occurences (of type: int)
    */
    public int occurencesFound(AstronomicObject object){
        Set<AstronomicObject> resultSet = repositoryOfBodies.stream().filter(element -> element.equalName(object)).collect(Collectors.toSet());
        return resultSet.size();
    }

    /**
        def: removes an object form the repository based on its name
        in: object(of type: AstronomicObject)
        out: 1 - success | 0 - not found
     */
    public int remove(AstronomicObject object){
        for(AstronomicObject element : repositoryOfBodies){
            if(element.equalName(object)){
                repositoryOfBodies.remove(element);
                return 1;
            }
        }
        return 0;
    }

    /**
        def: updates the fields of the object with the same name as the one given as an arument, with the fields of the argument
            object
        in: object(type of: AstronomicObject)
        out: 1 - success | 0 -not found

    */
    public int update(AstronomicObject object){
        for(AstronomicObject element : repositoryOfBodies){
            if(element.equalName(object)){
                repositoryOfBodies.remove(element);
                repositoryOfBodies.add(object);
                return 1;
            }
        }
        return 0;
    }

    /**
     def: returns the AstronomicObject with the given name if found, or null otherwise
     in: name(of type: String)
     out: AstronomicObject - if found | null - if not found
     */
    public AstronomicObject get(String name){
        for(AstronomicObject element : repositoryOfBodies){
            if(element.getName().equals(name)){
                return element;
            }
        }
        return null;
    }
    /**
     def: returns the repositoryOfBodies
     */
    public ArrayList<AstronomicObject> getAll(){
        return repositoryOfBodies;
    }

    /**
        def: returns all satelites of a given origin/parent AstronomicObject
        in: parentName (of type: String)
        out: list of Satelites if found any | an empty list of satelites if found none
    */
    public ArrayList<AstronomicObject> getAllSatelites(String parentName){
        ArrayList<AstronomicObject> result = new ArrayList<AstronomicObject>();
        for(AstronomicObject element : repositoryOfBodies){
            if(element.getParentName() == parentName)
                result.add(element);
        }
        return result;
    }

}
