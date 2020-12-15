/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bernardoaltamirano
 */
@Entity
@Table(name = "BOOKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b")
    , @NamedQuery(name = "Books.findByIsbn", query = "SELECT b FROM Books b WHERE b.isbn = :isbn")
    , @NamedQuery(name = "Books.findUnitsByIsbn", query = "SELECT b.unitsavailable FROM Books b WHERE b.isbn = :isbn")
    , @NamedQuery(name = "Books.findByUnitsavailable", query = "SELECT b FROM Books b WHERE b.unitsavailable = :unitsavailable")
    , @NamedQuery(name = "Books.findByUnitsonhold", query = "SELECT b FROM Books b WHERE b.unitsonhold = :unitsonhold")
    , @NamedQuery(name = "Books.findByPrice", query = "SELECT b FROM Books b WHERE b.price = :price")
    , @NamedQuery(name = "Books.holdStock" , query = "UPDATE Books SET unitsonhold = 999 WHERE isbn = 1")
    //, @NamedQuery(name = "Books.holdStock" , query = "UPDATE Books b SET b.unitsonhold = b.unitsonhold + :units, b.unitsavailable = b.unitsavailable - :units WHERE b.isbn = :isbn")
})
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ISBN")
    private Integer isbn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNITSAVAILABLE")
    private int unitsavailable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNITSONHOLD")
    private int unitsonhold;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    public Books() {
    }

    public Books(Integer isbn) {
        this.isbn = isbn;
    }

    public Books(Integer isbn, int unitsavailable, int unitsonhold, BigDecimal price) {
        this.isbn = isbn;
        this.unitsavailable = unitsavailable;
        this.unitsonhold = unitsonhold;
        this.price = price;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public int getUnitsavailable() {
        return unitsavailable;
    }

    public void setUnitsavailable(int unitsavailable) {
        this.unitsavailable = unitsavailable;
    }

    public int getUnitsonhold() {
        return unitsonhold;
    }

    public void setUnitsonhold(int unitsonhold) {
        this.unitsonhold = unitsonhold;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Books[ isbn=" + isbn + " ]";
    }
    
}
