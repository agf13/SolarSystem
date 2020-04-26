package exceptions;

public class WrongInput extends Exception {
    public WrongInput(String errorMessage){
        super(errorMessage);
    }

    public WrongInput(){
        super();
    }
}
