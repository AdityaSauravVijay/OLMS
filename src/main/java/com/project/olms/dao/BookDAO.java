
  package com.project.olms.dao;
  
  import java.util.Date; 
  import java.util.List;
  
  import org.hibernate.Criteria; 
  import org.hibernate.criterion.Restrictions;
  import org.hibernate.query.Query; 
  import org.springframework.stereotype.Component;
  
  import com.project.olms.pojo.Book; 
  import com.project.olms.pojo.User;
  
  @Component 
  public class BookDAO extends DAO{
  
  public Book getBookById(int id) 
  { 
	  begin(); Book book = getSession().get(Book.class, id); 
	  commit(); 
	  return book; 
  }
  
  public List<Book> getAllBooks() 
  {   
	  begin(); 
	  Query query = getSession().createQuery("FROM Book"); 
	  List<Book> books = query.list();
	  commit(); 
	  return books; 
  }
  
  public List<Book> getAllBooksAvailable() 
  { 
	  begin(); 
	  Query query = getSession().createQuery("FROM Book where available is true"); 
	  List<Book> books = query.list(); commit(); 
	  return books; 
  }
  
  public List<Book> searchBooks(String author, String title, String isbn) 
  {
	  begin(); 
	  Criteria criteria = getSession().createCriteria(Book.class); 
	  if(author != null && !author.isEmpty()) 
	  {
	  criteria.add(Restrictions.eq("author", author)); 
	  } 
	  if (title != null && !title.isEmpty()) 
	  { 
	  criteria.add(Restrictions.eq("title", title)); 
	  } 
	  if (isbn!= null && !isbn.isEmpty()) 
	  { 
	  criteria.add(Restrictions.eq("isbn", isbn)); 
	  }
	  List<Book> books = criteria.list(); 
	  commit(); 
	  return books; 
  }
  
  public void saveOrUpdateBook(Book book) 
  { 
	  begin();
	  getSession().saveOrUpdate(book); 
	  commit(); 
  }
  
  public void deleteBook(Book book) 
  { 
	  begin(); 
	  getSession().delete(book);
	  commit(); 
  }
  
  
  
  
  
  
  }
 