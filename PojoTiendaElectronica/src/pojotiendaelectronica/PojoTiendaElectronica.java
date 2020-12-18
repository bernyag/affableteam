/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsalmacen.Books;
import wsclient.Client;
import wscobro.Exception_Exception;
import wsdeliverycompany.DeliveryCompany;
import wsdeliveryorder.DeliveryOrder;

/**
 *
 * @author bernardoaltamirano
 */
public class PojoTiendaElectronica {

    /**
     * @param args the command line arguments
     * @throws wscobro.Exception_Exception
     */
    public static void main(String[] args) throws Exception_Exception {
        
        String host = (args.length < 1) ? null : args[0];
        long lngQuienSoy;
        long lngCuantosMilisFaltan;
        long sumDeltaT, sumDeltaT2, dtMin = 0,dtMax = 0;
        int n= 10;
        int ISBN = 1;
        int UNITS = 1;
        int COMPANYID = 1;
        int CLIENTID = 1;
        int orderId = 1;
        
        if(args.length >0){
            for(int x=0; x<args.length;x++)
                System.out.println("args["+x+"]" +args[x]);
        }
        else{
            System.out.println("Sin argumentos");
        }
        
        try {
            
            Registry registry = LocateRegistry.getRegistry(host);
            IServDisparo servDisparo = (IServDisparo) registry.lookup("ServidorDeDisparo");
            lngQuienSoy = servDisparo.quienSoy();
            lngCuantosMilisFaltan = servDisparo.deltaTEnMilis();
            System.out.println("Cliente " + lngQuienSoy + " faltan " + lngCuantosMilisFaltan  + " milisegundos");
            sumDeltaT  = 0;
            sumDeltaT2 = 0;
            Thread.currentThread().sleep(lngCuantosMilisFaltan);
 
            //Crear ordenes 
            for(int i= 0; i < n; i++){
                
                ISBN = (int) (Math.random()*4) + 1 ; // Generar un numero random para el ISBN DEL 1 AL 5 
                UNITS = (int) (Math.random()*4) + 1 ;
                CLIENTID = 1;
              
                try{
                    orderId = startPayment(CLIENTID, ISBN, UNITS);
                    System.out.println("Orden aceptada:" +orderId);
                }
                catch(Exception e){
                    System.out.println("Upsss el pedido no se pudo pedir, intentare con otro cliente ");
                    CLIENTID = CLIENTID +1 ;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        
    }

    private static Books findByIsbn(int isbn) {
        wsalmacen.WSAlmacenService service = new wsalmacen.WSAlmacenService();
        wsalmacen.WSAlmacen port = service.getWSAlmacenPort();
        return port.findByIsbn(isbn);
    }

    private static boolean startOrder(int isbn, int units) throws wsalmacen.Exception_Exception {
        wsalmacen.WSAlmacenService service = new wsalmacen.WSAlmacenService();
        wsalmacen.WSAlmacen port = service.getWSAlmacenPort();
        return port.startOrder(isbn, units);
    }
    
    // WS Cobro
    private static int startPayment(int idClt, int isbn, int units) throws Exception_Exception {
        wscobro.WSCobro_Service service = new wscobro.WSCobro_Service();
        wscobro.WSCobro port = service.getWSCobroPort();
        return port.startPayment(idClt, isbn, units);
    }

    // WS Client

    private static Client findClientById(int idClt) {
        wsclient.WsClient_Service service = new wsclient.WsClient_Service();
        wsclient.WsClient port = service.getWsClientPort();
        return port.findClientById(idClt);
    }

    private static long createDeliveryCompany(wsdeliverycompany.DeliveryCompany entity) {
        wsdeliverycompany.WsDeliveryCompany_Service service = new wsdeliverycompany.WsDeliveryCompany_Service();
        wsdeliverycompany.WsDeliveryCompany port = service.getWsDeliveryCompanyPort();
        return port.create(entity);
    }

    private static int createDeliverOrder(int idEmpresa, int idPedido) {
        wsdeliveryorder.WsDeliveryOrder_Service service = new wsdeliveryorder.WsDeliveryOrder_Service();
        wsdeliveryorder.WsDeliveryOrder port = service.getWsDeliveryOrderPort();
        return port.deliverOrder(idEmpresa, idPedido);
    }

    private static int createClient(wsclient.Client entity) {
        wsclient.WsClient_Service service = new wsclient.WsClient_Service();
        wsclient.WsClient port = service.getWsClientPort();
        return port.create(entity);
    }

    private static String ventaLibrosWSDLOperation(int isbn, int idCliente, int unidades, int idComp) {
        TiendaCA.VentaLibrosWSDLService service = new TiendaCA.VentaLibrosWSDLService();
        TiendaCA.VentaLibrosWSDLPortType port = service.getVentaLibrosWSDLPort();
        return port.ventaLibrosWSDLOperation(isbn, idCliente, unidades, idComp);
    } 
    
    
    
}