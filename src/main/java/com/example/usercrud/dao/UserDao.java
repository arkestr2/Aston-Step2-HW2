package com.example.usercrud.dao;

import com.example.usercrud.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class UserDao implements Dao<User> {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                log.error("Error while saving user", e);
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(User.class, id));
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            SelectionQuery<User> query = session.createSelectionQuery(
                    "FROM User", User.class
            );

            return query.getResultList();
        }
    }

    @Override
    public User update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                User updatedUser = session.merge(user);
                transaction.commit();
                return updatedUser;
            } catch (Exception e) {
                log.error("Error while updating user", e);
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.remove(user);
                transaction.commit();
            } catch (Exception e) {
                log.error("Error while deleting user", e);
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                throw e;
            }
        }
    }
}
