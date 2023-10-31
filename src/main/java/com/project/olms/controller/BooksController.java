package com.project.olms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.olms.dao.BookDAO;
import com.project.olms.pojo.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class BooksController {
 
	@Autowired 
	private HttpSession httpSession;
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				model.addAttribute("book", new Book());
				return "add-books";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "add-error";
		}
	}
	
	@PostMapping("/add")
	public String addBook(@ModelAttribute("book") Book book, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				bookDAO.saveOrUpdateBook(book);
				return "added-successfully";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "add-error";
		}
	}
	
	@GetMapping("/view")
	public String showAllBooks(Model model, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				List<Book> books = bookDAO.getAllBooks();
				model.addAttribute("books", books);
				return "list-books";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "view-error";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id")int id, Model model, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				Book book = bookDAO.getBookById(id);
				model.addAttribute("book", book);
				return "edit-books";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "edit-error";
		}
	}
	
	@PostMapping("/edit/{id}")
	public String updateBook(@PathVariable("id")int id, @ModelAttribute("book") Book book, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				book.setId(id);
				bookDAO.saveOrUpdateBook(book);
				return "edited-successfully";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "edit-error";
		}
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id")int id, Model model, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				Book book = bookDAO.getBookById(id);
				model.addAttribute("book", book);
				return "delete-books";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "delete-error";
		}
	}
	
	@PostMapping("/delete/{id}")
	public String deleteSuccess(@PathVariable("id")int id, @ModelAttribute("book") Book book, BookDAO bookDAO) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				book.setId(id);
				bookDAO.deleteBook(book);
				return "deleted-successfully";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "delete-error";
		}
	}
	
	
	@GetMapping("/search")
	public String showSearchForm(Model model) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				model.addAttribute("books", new Book());
				return "search-books";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "search-error";
		}
	}
	
	@PostMapping("/search")
	public String SearchBook(@ModelAttribute("book") Book book, BookDAO bookDAO, Model model) {
		try {
			User user = (User)httpSession.getAttribute("session");
			if(user!=null && user.getRole().equals("Librarian")) {
				List<Book> books = bookDAO.searchBooks(book.getAuthor(), book.getTitle(), book.getIsbn());
				model.addAttribute("books", books);
				return "search-books-results";
			}
			else
				return "login-need";
		}
		catch(Exception ex) {
			return "search-error";
		}
	}
}
