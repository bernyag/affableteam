/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jaimelimonsamperio
 */
@Entity
@Table(name = "ORDER_BOOK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderBook.findAll", query = "SELECT o FROM OrderBook o")
    , @NamedQuery(name = "OrderBook.findByOrderid", query = "SELECT o FROM OrderBook o WHERE o.orderid = :orderid")
    , @NamedQuery(name = "OrderBook.findByUnitsordered", query = "SELECT o FROM OrderBook o WHERE o.unitsordered = :unitsordered")
    , @NamedQuery(name = "OrderBook.findByFinalcost", query = "SELECT o FROM OrderBook o WHERE o.finalcost = :finalcost")})
public class OrderBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Integer orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNITSORDERED")
    private int unitsordered;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "FINALCOST")
    private BigDecimal finalcost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private List<DeliveryOrder> deliveryOrderList;
    @JoinColumn(name = "ISBN", referencedColumnName = "ISBN")
    @ManyToOne(optional = false)
    private Books isbn;
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID")
    @ManyToOne(optional = false)
    private Client clientid;

    public OrderBook() {
    }

    public OrderBook(Integer orderid) {
        this.orderid = orderid;
    }

    public OrderBook(Integer orderid, int unitsordered, BigDecimal finalcost) {
        this.orderid = orderid;
        this.unitsordered = unitsordered;
        this.finalcost = finalcost;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public int getUnitsordered() {
        return unitsordered;
    }

    public void setUnitsordered(int unitsordered) {
        this.unitsordered = unitsordered;
    }

    public BigDecimal getFinalcost() {
        return finalcost;
    }

    public void setFinalcost(BigDecimal finalcost) {
        this.finalcost = finalcost;
    }

    @XmlTransient
    public List<DeliveryOrder> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    public void setDeliveryOrderList(List<DeliveryOrder> deliveryOrderList) {
        this.deliveryOrderList = deliveryOrderList;
    }

    public Books getIsbn() {
        return isbn;
    }

    public void setIsbn(Books isbn) {
        this.isbn = isbn;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderBook)) {
            return false;
        }
        OrderBook other = (OrderBook) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OrderBook[ orderid=" + orderid + " ]";
    }
    
}
