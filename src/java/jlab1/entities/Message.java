/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlab1.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daumantas
 */
@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByName", query = "SELECT m FROM Message m WHERE m.name = :name"),
    @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message"),
    @NamedQuery(name = "Message.findByTime", query = "SELECT m FROM Message m WHERE m.time = :time")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
        @TableGenerator(name = "MSGPkGen",
                        table = "SEQUENCE",
                        schema = "APP",
                        pkColumnName = "SEQ_NAME",
                        pkColumnValue = "MESSAGE",
                        valueColumnName = "SEQ_COUNT",
                        initialValue = 0,
                        allocationSize = 1
                        )
    @GeneratedValue(generator = "MSGPkGen", strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 120)
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "PAPILDOMAS")
    private String papildomas;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    public void setPapildomas(String papildomas) {
        this.papildomas = papildomas;
    }

    public String getPapildomas() {
        return papildomas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jlab1.entities.Message[ id=" + id + " ]";
    }
    
}
