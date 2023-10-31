package com.project.olms.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import com.project.olms.pojo.User;
import com.project.olms.pojo.Book;
import com.project.olms.pojo.Borrow;
import com.project.olms.pojo.Return;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	 public static SessionFactory getSessionFactory() {
		 if(sessionFactory == null) {
			 try {
				 Configuration configuration = new Configuration();
				 
				 Properties settings = new Properties();
				 settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				 settings.put(Environment.URL, "jdbc:mysql://localhost:3306/OLMSDb?createDatabaseIfNotExist=true");
				 settings.put(Environment.USER, "root");
				 settings.put(Environment.PASS, "cosmos2297");
				 settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				 settings.put(Environment.SHOW_SQL, "true");
				 settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				 settings.put(Environment.HBM2DDL_AUTO, "update");
				 
				 configuration.setProperties(settings);
				 configuration.addAnnotatedClass(User.class);
				 configuration.addAnnotatedClass(Book.class);
				 configuration.addAnnotatedClass(Borrow.class);
				 configuration.addAnnotatedClass(Return.class);

				 
				 ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		                    .applySettings(configuration.getProperties()).build();
				 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				 
				 
				 
				 
				 
			 }
			 
			 catch(Exception e) {
				 e.printStackTrace();
			 }
			 
		 }
		 return sessionFactory;
	 }

}
