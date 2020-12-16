package webservices;

import entities.Books;
import facades.BooksFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Codigo perteneciente a: Tutorial de BPEL con OpenESB
 *
 * @author www.adictosaltrabajo.com - Ivan Garcia Puebla
 * @version 1.0
 */
@WebService()
public class WSAlmacen {

    @EJB
    private BooksFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Operacion de un servicio web implementado con JAX-WS que comprueba el
     * stock de un libro.
     *
     * @param ISBN Identificador de un libro
     * @param unidades Unidades del producto solicitadas
     * @return True si hay unidades disponibles.
     */
    @WebMethod(operationName = "findByIsbn")
    public Books findByIsbn(@WebParam(name = "isbn") int isbn) {
        return ejbRef.findByIsbn(isbn);
    }

    @WebMethod(operationName = "checkStock")
    public boolean checkStock(@WebParam(name = "isbn") int isbn, @WebParam(name = "unidades") int unidades) {

        String strUnitsAvailable;
        int intUnitsAvailable = 0;
        boolean res = false;

        strUnitsAvailable = ejbRef.unitsAvailable(isbn);
        intUnitsAvailable = Integer.parseInt(strUnitsAvailable);
        if (intUnitsAvailable >= unidades) {
            res = true;
        }
        return res;
    }

    @WebMethod(operationName = "holdStock")
    public void holdStock(@WebParam(name = "isbn") int isbn, @WebParam(name = "units") int units) {
        ejbRef.holdStock(isbn, units);
    }

    @WebMethod(operationName = "startOrder")
    public String startOrder(@WebParam(name = "isbn") int isbn, @WebParam(name = "units") int units) {
        String res = "";
        if (Integer.toString(isbn).length() == 1) { // TODO: Check that the ISBN used is in the 13 digit ISBN format 

            Books b = ejbRef.findByIsbn(isbn);
            if (b.getIsbn() != -1) {    //Check if the ISBN is in the database
                if (units >= 1) {
                    if (checkStock(isbn, units)) {
                        holdStock(isbn, units);
                        res = "An order has been created for the book with ISBN = " + isbn + "."
                                + "\n\t" + units + " unit(s) have been placed on hold while validating the account's funds.";
                    } else {
                        res = "There is unsufficient stock for the book with ISBN = " + isbn + ". The stock available is of " + b.getUnitsavailable() + " unit(s) and you tried " + units + ", please try an amount less than or equal to the one available.";
                    }
                } else {
                    res = "Please enter an amount of units greater than 0.";
                }
            } else {
                res = "The books with ISBN  = " + isbn + " is not registered in the database. Please try with another ISBN.";
            }

        } else {
            res = "Please enter an ISBN with length of 13 digits.";
        }
        return res;
    }
}
