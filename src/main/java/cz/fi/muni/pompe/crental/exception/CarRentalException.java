package cz.fi.muni.pompe.crental.exception;

/**
 *
 * @author Yayo
 */
public class CarRentalException extends Exception{
   
    public CarRentalException() {
    }


    public CarRentalException(String msg) {
        super(msg);
    }

    public CarRentalException(String msg, Throwable cause) {
        super(msg,cause);

    }

     public CarRentalException(Throwable cause) {
        super(cause);
     }
}
