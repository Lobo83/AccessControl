package org.accessControl.persistence.bean;
// Generated 15-ago-2013 12:06:29 by Hibernate Tools 3.1.0.beta4

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * AccessControlUser generated by hbm2java
 */
@Entity
@Table(name="access_control_user"
    ,catalog="personalTrainer"
, uniqueConstraints = {  }
)

public class AccessControlUser  implements java.io.Serializable {


    // Fields    

     private String userLogin;
     private Date createDate;
     private Date updateDate;
     private String updateUser;
     private String updateProgram;
     private short optimistLock;
     private String nombre;
     private String userPassword;
     private String codProfile;


    // Constructors

    /** default constructor */
    public AccessControlUser() {
    }

    
    /** full constructor */
    public AccessControlUser(String userLogin, Date createDate, Date updateDate, String updateUser, String updateProgram, short optimistLock, String nombre, String userPassword, String codProfile) {
        this.userLogin = userLogin;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateUser = updateUser;
        this.updateProgram = updateProgram;
        this.optimistLock = optimistLock;
        this.nombre = nombre;
        this.userPassword = userPassword;
        this.codProfile = codProfile;
    }
    

   
    // Property accessors
    @Id
    @Column(name="USER_LOGIN", unique=true, nullable=false, insertable=true, updatable=true, length=10)

    public String getUserLogin() {
        return this.userLogin;
    }
    
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    @Column(name="CREATE_DATE", unique=false, nullable=false, insertable=true, updatable=true, length=10)

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name="UPDATE_DATE", unique=false, nullable=false, insertable=true, updatable=true, length=10)

    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Column(name="UPDATE_USER", unique=false, nullable=false, insertable=true, updatable=true, length=10)

    public String getUpdateUser() {
        return this.updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    @Column(name="UPDATE_PROGRAM", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getUpdateProgram() {
        return this.updateProgram;
    }
    
    public void setUpdateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
    }
    @Column(name="OPTIMIST_LOCK", unique=false, nullable=false, insertable=true, updatable=true, precision=3, scale=0)

    public short getOptimistLock() {
        return this.optimistLock;
    }
    
    public void setOptimistLock(short optimistLock) {
        this.optimistLock = optimistLock;
    }
    @Column(name="NOMBRE", unique=false, nullable=false, insertable=true, updatable=true, length=100)

    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Column(name="USER_PASSWORD", unique=false, nullable=false, insertable=true, updatable=true, length=10)

    public String getUserPassword() {
        return this.userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    @Column(name="COD_PROFILE", unique=false, nullable=false, insertable=true, updatable=true, length=10)

    public String getCodProfile() {
        return this.codProfile;
    }
    
    public void setCodProfile(String codProfile) {
        this.codProfile = codProfile;
    }
   








}