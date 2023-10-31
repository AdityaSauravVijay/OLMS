package com.project.olms.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.project.olms.pojo.Book;
import com.project.olms.pojo.Borrow;
import com.project.olms.pojo.Return;
import com.project.olms.pojo.User;

@Component
public class BorrowDAO extends DAO {

	public void issueBook(Borrow borrow) {
	    begin();
	    getSession().save(borrow);
	    commit();
	}
	
    public void requestBook(Borrow borrow) {
        begin();
        getSession().save(borrow);
        commit();
    }

    public List<Borrow> getIssuedBooks() {
        begin();
        Query query = getSession().createQuery("FROM Borrow WHERE returnDate IS NULL");
        List<Borrow> issuedBooks = query.list();
        commit();
        return issuedBooks;
    }

    public void returnBook(int borrowId, Date returnedDate) {
        begin();
        Borrow borrow = getSession().get(Borrow.class, borrowId);
        borrow.setReturnDate(returnedDate);
        getSession().update(borrow);
        commit();
    }
    
    public void completeRecieveRequest(int borrowId, Date dueDate) {
        begin();
        Borrow borrow = getSession().get(Borrow.class, borrowId);
        borrow.setDueDate(dueDate);
        getSession().update(borrow);
        commit();
    }
    
    public List<Borrow> getAllBorrowedBooks() {
        begin();
        Query query = getSession().createQuery("FROM Borrow");
        List<Borrow> borrowedBooks = query.list();
        commit();
        return borrowedBooks;
    }
    
    public List<Borrow> getAllBorrowedReturnedBooks() {
        begin();
        Query query = getSession().createQuery("FROM Borrow WHERE returnDate IS NOT NULL ");
        List<Borrow> borrowedBooks = query.list();
        commit();
        return borrowedBooks;
    }

    public List<Borrow> getBorrowedBooksByUser(User user) {
        begin();
        Query query = getSession().createQuery("FROM Borrow WHERE user=:user AND returnDate IS NULL");
        query.setParameter("user", user);
        List<Borrow> borrowedBooks = query.list();
        commit();
        return borrowedBooks;
    }
    

    public Borrow getBorrowById(int borrowId) {
        begin();
        Borrow borrow = getSession().get(Borrow.class, borrowId);
        commit();
        return borrow;
    }
    
    public void renewBook(int borrowId, Date newDueDate) {
        begin();
        Borrow borrow = getSession().get(Borrow.class, borrowId);
        borrow.setDueDate(newDueDate);
        getSession().update(borrow);
        commit();
    }
    
    public List<Borrow> getReturnedBooks() {
        begin();
        Query query = getSession().createQuery("FROM Borrow WHERE returnDate IS NOT NULL");
        List<Borrow> returnedBooks = query.list();
        commit();
        return returnedBooks;
    }
    
    public List<Borrow> getBorrowHistoryByUser(User user) {
        begin();
        Query query = getSession().createQuery("FROM Borrow WHERE user=:user");
        query.setParameter("user", user);
        List<Borrow> borrowHistory = query.list();
        commit();
        return borrowHistory;
    }
    
	public void deleteBorrow(Borrow borrow) {
	    begin();
	    getSession().delete(borrow);
	    commit();
	}
}