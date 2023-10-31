package com.project.olms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.olms.dao.BookDAO;
import com.project.olms.dao.BorrowDAO;
import com.project.olms.dao.UserDAO;
import com.project.olms.pojo.Book;
import com.project.olms.pojo.Borrow;
import com.project.olms.pojo.Return;
import com.project.olms.pojo.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReaderRoleController {
	
	@Autowired 
	private HttpSession httpSession;
	
	@GetMapping("/reader-home")
	public String showLibrary() {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				return "reader-home";
			}
			else
				return "login-need";
		}
		catch(Exception e) {
			return "reader-home-error";
		}
	}
	
	//Display page to issue book
	@GetMapping("/request-book")
	public String showRequestBookForm(Model model, BookDAO bookDAO, UserDAO userDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				List<Book> allBooks = bookDAO.getAllBooksAvailable();
				List<User> users = userDAO.getAllUsers();
				model.addAttribute("borrow", new Borrow());
				model.addAttribute("allBooks", allBooks);
				model.addAttribute("user", user);
				return "request-book";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "request-book-error";
		}
		}
	
	@PostMapping("/request-book")
	public String issueBook(@ModelAttribute("borrow") Borrow borrow, BorrowDAO borrowDAO, BookDAO bookDAO) {
	
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				
				borrowDAO.requestBook(borrow);
				return "requested-successfully";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "request-book-error";
		}
	}
	
	
	@GetMapping("/view-issued-books")
	public String showBorrowedBooksByUser(Model model, BorrowDAO borrowDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				List<Borrow> issuedBooksByUser = borrowDAO.getBorrowedBooksByUser(user);
				model.addAttribute("issuedBooksByUser", issuedBooksByUser);
				return "view-issued-books-user";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "view-issued-books-error";
		}
	}
	
	
	@GetMapping("/return-book")
	public String showReturnBookForm(Model model, BorrowDAO borrowDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				List<Borrow> borrowedBooksByUser = borrowDAO.getBorrowedBooksByUser(user);
			
				model.addAttribute("return", new Return());
				model.addAttribute("borrowedBooksByUser", borrowedBooksByUser);
			
				return "return-book";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "return-book-error";
		}
		}
	
	@PostMapping("/return-book")
	public String returnBook(@ModelAttribute("return") Return returnObj, BorrowDAO borrowDAO, BookDAO bookDAO) {
	
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
                borrowDAO.returnBook(returnObj.getBorrow().getBorrowId(), returnObj.getReturnedDate());
        
				return "returned-successfully";
			}
			else 
				return "login-need";
		}
		catch(Exception ex) {
			return "return-book-error";
		}
	}
	
	@GetMapping("/search-reader")
	public String showSearchReaderForm(Model model) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				model.addAttribute("books", new Book());
				return "search-books-reader";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "search-reader-error";
		}
	}
	
	@PostMapping("/search-reader")
	public String SearchReaderBook(@ModelAttribute("book") Book book, BookDAO bookDAO, Model model) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Reader")) {
				List<Book> books = bookDAO.searchBooks(book.getAuthor(), book.getTitle(), book.getIsbn());
				model.addAttribute("books", books);
				return "search-books-results-reader";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "search-reader-error";
		}
	}

}
