package com.project.olms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.olms.pojo.*;
import com.project.olms.dao.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LibrarianRoleController {
	
	@Autowired 
	private HttpSession httpSession;
	
	@GetMapping("/librarian-home")
	public String showLibrary() {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				return "librarian-home";
			}
			else
				return "login-need";
		}
		catch(Exception e) {
			return "home-error";
		}
	}
	
	//Display page to issue book
	@GetMapping("/issue-book")
	public String showIssueBookForm(Model model, BookDAO bookDAO, UserDAO userDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				List<Book> allBooks = bookDAO.getAllBooksAvailable();
				List<User> users = userDAO.getAllUsers();
				model.addAttribute("borrow", new Borrow());
				model.addAttribute("allBooks", allBooks);
				model.addAttribute("users", users);
				return "issue-book";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "issue-book-error";
		}
		}
	
	@PostMapping("/issue-book")
	public String issueBook(@ModelAttribute("borrow") Borrow borrow, BorrowDAO borrowDAO, BookDAO bookDAO) {
	
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				borrow.getBook().setAvailable(false);
				Book book = bookDAO.getBookById(borrow.getBook().getId());
				book.setAvailable(false);
				bookDAO.saveOrUpdateBook(book);
				borrowDAO.issueBook(borrow);
				return "issued-successfully";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "issue-book-error";
		}
	}
	
	
	
	//Display page to issue book
	@GetMapping("/issue-book-by-request")
	public String showIssueBookFormByRequest(Model model, BookDAO bookDAO, UserDAO userDAO, BorrowDAO borrowDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				List<Borrow> allBooks = borrowDAO.getIssuedBooks();
				List<User> users = userDAO.getAllUsers();
				model.addAttribute("return", new Return());
				model.addAttribute("allBooks", allBooks);
				model.addAttribute("users", users);
				return "issue-book-by-request";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "issue-book-by-request-error";
		}
		}
	
	@PostMapping("/issue-book-by-request")
	public String issueBookByRequest(@ModelAttribute("return") Return returnObj, BorrowDAO borrowDAO, BookDAO bookDAO) {
	
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
                Borrow borrow = borrowDAO.getBorrowById(returnObj.getBorrow().getBorrowId());
                Book book = bookDAO.getBookById(borrow.getBook().getId());
                book.setAvailable(false);
                bookDAO.saveOrUpdateBook(book);
                borrowDAO.completeRecieveRequest(returnObj.getBorrow().getBorrowId(), returnObj.getBorrow().getDueDate());
				return "issued-book-by-request-successfully";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "issue-book-by-request-error";
		}
	}
	
	
	
	@GetMapping("/receive-book")
	public String showRecieveBookForm(Model model, BookDAO bookDAO, UserDAO userDAO, BorrowDAO borrowDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				List<Borrow> borrowedBooks = borrowDAO.getAllBorrowedReturnedBooks();
			
				model.addAttribute("return", new Return());
				model.addAttribute("borrowedBooks", borrowedBooks);
			
				return "recieve-book";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "receive-book-error";
		}
		}
	
	@PostMapping("/receive-book") // corrected spelling mistake in the URL mapping
	public String receiveBook(@ModelAttribute("return") Return returnObj, BorrowDAO borrowDAO, BookDAO bookDAO) {
	    try {
		User user = (User) httpSession.getAttribute("session");
	    if (user!=null && user.getRole().equals("Librarian")) {
	    	Borrow borrow = borrowDAO.getBorrowById(returnObj.getBorrow().getBorrowId());
	    	Book book = bookDAO.getBookById(borrow.getBook().getId());
			book.setAvailable(true);
			bookDAO.saveOrUpdateBook(book);
	        borrowDAO.deleteBorrow(borrow); // pass borrowId and returnDate to the DAO method
	        return "recieved-successfully"; // corrected spelling mistake in the view name
	    } else 
	        return "login-need";
	    }  catch(Exception ex) {
	  		  return "receive-book-error"; //handle error appropriately
	  	}
	}
	
	
	
	
	
}

