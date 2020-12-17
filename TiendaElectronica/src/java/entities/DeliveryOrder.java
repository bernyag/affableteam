/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jaimelimonsamperio
 */
@Entity
@Table(name = "DELIVERY_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeliveryOrder.findAll", query = "SELECT d FROM DeliveryOrder d")
    , @NamedQuery(name = "DeliveryOrder.findByDeliverorderid", query = "SELECT d FROM DeliveryOrder d WHERE d.deliverorderid = :deliverorderid")
    , @NamedQuery(name = "DeliveryOrder.findByDeliverydays", query = "SELECT d FROM DeliveryOrder d WHERE d.deliverydays = :deliverydays")})
public class DeliveryOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DELIVERORDERID")
    private Integer deliverorderid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "DELIVERYDAYS")
    private int deliverydays;
    
    @JoinColumn(name = "IDDELIVERY", referencedColumnName = "IDDELIVERY")
    @ManyToOne(optional = false)
    private DeliveryCompany iddelivery;
    
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private OrderBook orderid;

    public DeliveryOrder() {
    }

    public DeliveryOrder(Integer deliverorderid) {
        this.deliverorderid = deliverorderid;
    }

    public DeliveryOrder(Integer deliverorderid, int deliverydays) {
        this.deliverorderid = deliverorderid;
        this.deliverydays = deliverydays;
    }

    public Integer getDeliverorderid() {
        return deliverorderid;
    }

    public void setDeliverorderid(Integer deliverorderid) {
        this.deliverorderid = deliverorderid;
    }

    public int getDeliverydays() {
        return deliverydays;
    }

    public void setDeliverydays(int deliverydays) {
        this.deliverydays = deliverydays;
    }

    public DeliveryCompany getIddelivery() {
        return iddelivery;
    }

    public void setIddelivery(DeliveryCompany iddelivery) {
        this.iddelivery = iddelivery;
    }

    public OrderBook getOrderid() {
        return orderid;
    }

    public void setOrderid(OrderBook orderid) {
        this.orderid = orderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliverorderid != null ? deliverorderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryOrder)) {
            return false;
        }
        DeliveryOrder other = (DeliveryOrder) object;
        if ((this.deliverorderid == null && other.deliverorderid != null) || (this.deliverorderid != null && !this.deliverorderid.equals(other.deliverorderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DeliveryOrder[ deliverorderid=" + deliverorderid + " ]";
    }
    
}
