package com.project.olms.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "returns")
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int returnId;
    
    @OneToOne
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;
    
    @Column(name = "returned_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnedDate;

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public Date getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}
    
    
}