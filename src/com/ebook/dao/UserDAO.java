package com.ebook.dao;

import com.ebook.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserDAO {

    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public void saveUser(User user) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public User getUser(String username, String password) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                "FROM User WHERE username = :username AND password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        }
    }
}
