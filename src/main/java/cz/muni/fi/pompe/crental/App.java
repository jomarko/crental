package cz.muni.fi.pompe.crental;

import cz.muni.fi.pompe.crental.testsamples.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        DAOEmployeeSample esample = new DAOEmployeeSample();
        esample.run();
    }
}
