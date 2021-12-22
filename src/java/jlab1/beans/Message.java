/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlab1.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daumantas
 */
@XmlRootElement
public class Message {
    
    public void Message(){}
    
    private String name;
    private String msg;
    private Date time;
    private String papildomas;

    public void setPapildomas(String papildomas) {
        this.papildomas = papildomas;
    }

    public String getPapildomas() {
        return papildomas;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public Date getTime() {
        return time;
    }
}
