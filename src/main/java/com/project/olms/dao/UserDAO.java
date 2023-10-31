package com.project.olms.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.project.olms.pojo.User;

@Component
public class UserDAO extends DAO {

    public User getUserById(long id) {
        begin();
        User user = getSession().get(User.class, id);
        commit();
        return user;
    }

    public User getUserByEmail(String email) {
        begin();
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        User user = (User) criteria.uniqueResult();
        commit();
        return user;
    }

    public List<User> getAllUsers() {
        begin();
        Criteria criteria = getSession().createCriteria(User.class);
        List<User> users = criteria.list();
        commit();
        return users;
    }

    public void saveOrUpdateUser(User user) {
        begin();
        getSession().save(user);
        commit();
    }

    public void deleteUser(User user) {
        begin();
        getSession().delete(user);
        commit();
    }
    
}
