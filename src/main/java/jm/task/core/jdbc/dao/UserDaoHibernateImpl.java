package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;


public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession();

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        String sql = "CREATE TABLE IF NOT EXISTS Users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            tx = session.beginTransaction();
            MutationQuery mutationQuery = session.createNativeQuery(sql, User.class);
            mutationQuery.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            tx = session.beginTransaction();
            MutationQuery mutationQuery = session.createNativeQuery(sql, User.class);
            mutationQuery.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        String hql = "Delete From User";
        try {
            tx = session.beginTransaction();
            MutationQuery mutationQuery = session.createMutationQuery(hql);
            mutationQuery.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
