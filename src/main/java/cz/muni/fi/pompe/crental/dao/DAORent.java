package cz.muni.fi.pompe.crental.dao;

import cz.fi.muni.pompe.crental.exception.CarRentalException;
import cz.muni.fi.pompe.crental.entity.Rent;
import java.util.List;

/**
 *
 * @author Jaro
 */
public interface DAORent {
    /**
    * Method stores a rent to database.
    * @throws NullPointerException if rent is null
    * @throws IllegalArgumentException if rent has id, hasn't request, confirmedBy, confirmedAt, rentedCar set
    * @param rent Instance of Rent to be stored. Instance can't be stored before.
    */
    void createRent(Rent rent) throws CarRentalException;
   
    /**
    * Method deletes a rent from database.
    * @throws NullPointerException if idOfRentToBeDeleted is null
    * @param id Id of Rent to be deleted.
    */
    void deleteRent(Long id) throws CarRentalException;
    
   /**
     * Method update record in database, which has the same id as rent.
     * @throws NullPointerException if employee is null
     * @throws IllegalArgumentException if employee hasn't id, request, confirmedBy, confirmedAt, rentedCar set
     * @param rent Instance of Rent to be updated. Instance must be stored before method is called.
     */
    void updateRent(Rent rent) throws CarRentalException;
    
    /**
     * Method returns List of all rents in database
     * @return List of all rents in database. If no rent is stored, empty list is returned;
     */
    List<Rent> getAllRents() throws CarRentalException;
    
    /**
     * Method returns Rent with specific id in database
     * @throws NullPointerException if idOfRent is null
     * @throws NoResultException if no Rent with given id is not in database
     * @param idOfRent Id of wanted Rent
     * @return Instance of Rent with wanted id
     */
    Rent getRentById(Long idOfRent) throws CarRentalException;
}