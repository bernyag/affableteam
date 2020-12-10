package wsAlmacen;

import entities.Books;
import facade.BooksFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Codigo perteneciente a: Tutorial de BPEL con OpenESB
 * @author www.adictosaltrabajo.com - Ivan Garcia Puebla
 * @version 1.0
 */
@WebService()
public class WSAlmacen {
    @EJB
    private BooksFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Operacion de un servicio web implementado con JAX-WS
     * que comprueba el stock de un libro.
     *
     * @param ISBN Identificador de un libro
     * @param unidades Unidades del producto solicitadas
     * @return True si hay unidades disponibles.
     */
    
    @WebMethod(operationName = "findByIsbn")
    public List<Books> findByIsbn(@WebParam(name = "isbn")
    int isbn) {
        return ejbRef.findByIsbn(isbn);
    }
    
//    @WebMethod(operationName = "findByIsbn")
//    public String findUnitsByIsbn(@WebParam(name = "isbn")
//    int isbn) {
//        return ejbRef.findUnitsByIsbn(isbn);
//    }
    
    @WebMethod(operationName = "comprobarStock")
    public boolean comprobarStock(@WebParam(name = "isbn")
    int isbn, @WebParam(name = "unidades")
    int unidades) {

       
        String strUnitsAvailable;
        int intUnitsAvailable = 0;
        boolean res = false;
        
        strUnitsAvailable = ejbRef.unitsAvailable(isbn);
        intUnitsAvailable = Integer.parseInt(strUnitsAvailable);
        System.out.println(strUnitsAvailable + " unidades disponibles del ISBN " + isbn);
        if ( intUnitsAvailable >= unidades ){
            res = true;            
        }
        return res;
    }
    
    @WebMethod(operationName = "holdStock")
    public void holdStock(@WebParam(name = "isbn")
    int isbn, @WebParam(name = "units")
    int units) {
       ejbRef.holdStock(isbn, units);
    }
}
