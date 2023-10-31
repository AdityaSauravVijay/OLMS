package com.project.olms.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "borrows")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowId;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "borrowed_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date borrowedDate;
    
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    
    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;
    
    @OneToOne(mappedBy = "borrow")
    private Return returnObj;

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowedDate) {
		this.borrowedDate = borrowedDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Return getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Return returnObj) {
		this.returnObj = returnObj;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
    
   
}