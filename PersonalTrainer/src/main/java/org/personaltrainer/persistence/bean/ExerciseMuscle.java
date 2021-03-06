package org.personaltrainer.persistence.bean;
// Generated 08-sep-2013 9:45:08 by Hibernate Tools 3.1.0.beta4

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * ExerciseMuscle generated by hbm2java
 */
@Entity
@Table(name="exercise_muscle"
    ,catalog="personalTrainer"
, uniqueConstraints = {  }
)

public class ExerciseMuscle  implements java.io.Serializable {


    // Fields    

     private int idExerciseMuscle;
     private Muscle muscle;
     private Exercise exercise;
     private Date createDate;
     private Date updateDate;
     private String updateUser;
     private String updateProgram;
     private int optimistLock;
     private boolean indPrincipal;


    // Constructors

    /** default constructor */
    public ExerciseMuscle() {
    }

    
    /** full constructor */
    public ExerciseMuscle(int idExerciseMuscle, Muscle muscle, Exercise exercise, Date createDate, Date updateDate, String updateUser, String updateProgram, int optimistLock, boolean indPrincipal) {
        this.idExerciseMuscle = idExerciseMuscle;
        this.muscle = muscle;
        this.exercise = exercise;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateUser = updateUser;
        this.updateProgram = updateProgram;
        this.optimistLock = optimistLock;
        this.indPrincipal = indPrincipal;
    }
    

   
    // Property accessors
    @Id
    @Column(name="ID_EXERCISE_MUSCLE", unique=true, nullable=false, insertable=true, updatable=true)

    public int getIdExerciseMuscle() {
        return this.idExerciseMuscle;
    }
    
    public void setIdExerciseMuscle(int idExerciseMuscle) {
        this.idExerciseMuscle = idExerciseMuscle;
    }
    @ManyToOne(cascade={},
        fetch=FetchType.LAZY)
    
        @JoinColumn(name="ID_MUSCLE", unique=false, nullable=false, insertable=true, updatable=true)

    public Muscle getMuscle() {
        return this.muscle;
    }
    
    public void setMuscle(Muscle muscle) {
        this.muscle = muscle;
    }
    @ManyToOne(cascade={},
        fetch=FetchType.LAZY)
    
        @JoinColumn(name="ID_EXERCISE", unique=false, nullable=false, insertable=true, updatable=true)

    public Exercise getExercise() {
        return this.exercise;
    }
    
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    @Column(name="CREATE_DATE", unique=false, nullable=false, insertable=true, updatable=true, length=19)

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name="UPDATE_DATE", unique=false, nullable=false, insertable=true, updatable=true, length=19)

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
    @Column(name="UPDATE_PROGRAM", unique=false, nullable=false, insertable=true, updatable=true, length=45)

    public String getUpdateProgram() {
        return this.updateProgram;
    }
    
    public void setUpdateProgram(String updateProgram) {
        this.updateProgram = updateProgram;
    }
    @Column(name="OPTIMIST_LOCK", unique=false, nullable=false, insertable=true, updatable=true)

    public int getOptimistLock() {
        return this.optimistLock;
    }
    
    public void setOptimistLock(int optimistLock) {
        this.optimistLock = optimistLock;
    }
    @Column(name="IND_PRINCIPAL", unique=false, nullable=false, insertable=true, updatable=true)

    public boolean isIndPrincipal() {
        return this.indPrincipal;
    }
    
    public void setIndPrincipal(boolean indPrincipal) {
        this.indPrincipal = indPrincipal;
    }
   








}
