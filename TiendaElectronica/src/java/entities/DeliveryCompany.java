/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jaimelimonsamperio
 */
@Entity
@Table(name = "DELIVERY_COMPANY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeliveryCompany.findAll", query = "SELECT d FROM DeliveryCompany d")
    , @NamedQuery(name = "DeliveryCompany.findByIddelivery", query = "SELECT d FROM DeliveryCompany d WHERE d.iddelivery = :iddelivery")
    , @NamedQuery(name = "DeliveryCompany.findByName", query = "SELECT d FROM DeliveryCompany d WHERE d.name = :name")})
public class DeliveryCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDDELIVERY")
    private Integer iddelivery;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddelivery")
    private List<DeliveryOrder> deliveryOrderList;

    public DeliveryCompany() {
    }

    public DeliveryCompany(Integer iddelivery) {
        this.iddelivery = iddelivery;
    }

    public DeliveryCompany(Integer iddelivery, String name) {
        this.iddelivery = iddelivery;
        this.name = name;
    }

    public Integer getIddelivery() {
        return iddelivery;
    }

    public void setIddelivery(Integer iddelivery) {
        this.iddelivery = iddelivery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<DeliveryOrder> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    public void setDeliveryOrderList(List<DeliveryOrder> deliveryOrderList) {
        this.deliveryOrderList = deliveryOrderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddelivery != null ? iddelivery.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeliveryCompany)) {
            return false;
        }
        DeliveryCompany other = (DeliveryCompany) object;
        if ((this.iddelivery == null && other.iddelivery != null) || (this.iddelivery != null && !this.iddelivery.equals(other.iddelivery))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DeliveryCompany[ iddelivery=" + iddelivery + " ]";
    }
    
}
