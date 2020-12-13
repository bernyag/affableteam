package wsAlmacen;

import entities.Books;
import facade.BooksFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService()
public class WSAlmacen {

    @EJB
    private BooksFacade ejbRef;
    
    
    @WebMethod(operationName = "findByIsbn")
    public Books findByIsbn(@WebParam(name = "isbn") long isbn) {
        return ejbRef.findByIsbn(isbn);
    }

    @WebMethod(operationName = "checkStock")
    public boolean checkStock(@WebParam(name = "isbn") long isbn, @WebParam(name = "units") int units) {

        String strUnitsAvailable;
        int intUnitsAvailable = 0;
        boolean res = false;

        strUnitsAvailable = ejbRef.unitsAvailable(isbn);
        intUnitsAvailable = Integer.parseInt(strUnitsAvailable);
        if (intUnitsAvailable >= units) {
            res = true;
        }
        return res;
    }

    @WebMethod(operationName = "holdStock")
    public void holdStock(@WebParam(name = "isbn") long isbn, @WebParam(name = "units") int units) {
        ejbRef.holdStock(isbn, units);
    }

    /**
     * Web service that validates if there is stock for a certain book and puts it on hold for funds validation
     * 
     * @param ISBN International Serial Book Number - book identifier
     * @param units Units of books being requested
     * @return a String with the message corresponding to any specific error (invalid ISBN format, ISBN not in database, invalid units value, insufficient stock or successful order.
     */
    @WebMethod(operationName = "startOrder")
    public String startOrder(@WebParam(name = "isbn") long isbn, @WebParam(name = "units") int units) {
        String res = "";
        if (Long.toString(isbn).length() == 13) { // TODO: Check that the ISBN used is in the 13 digit ISBN format 
            if (units >= 1) {
                Books b = ejbRef.findByIsbn(isbn);
                if (b.getIsbn() != -1) {    //Check if the ISBN is in the database
                    if (checkStock(isbn, units)) {
                        holdStock(isbn, units);
                        res = "An order has been created for the book with ISBN = " + isbn + "."
                                + "\n\t" + units + " unit(s) have been placed on hold while validating the account's funds.";
                    } else {
                        res = "There is unsufficient stock for the book with ISBN = " + isbn + ". The stock available is of " + b.getUnitsavailable() + " unit(s) and you tried " + units +", please try an amount less than or equal to the one available.";
                    }
                } else {
                    res = "The books with ISBN  = " + isbn + " is not registered in the database. Please try with another ISBN.";
                }
            } else {
                res = "Please enter an amount of units greater than 0.";
            }

        } else {
            res = "Please enter an ISBN with length of 13 digits.";
        }
        return res;
    }
}
