package ru.trofimov.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.trofimov.model.Water;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class WaterDaoImp implements WaterDao{
    @Override
    public Water findById(int id) {
        Water water;
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory("water").openSession()){
            water = session.get(Water.class, id);
        }
        return water;
    }

    @Override
    public void save(Water water) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory("water").openSession()){
            Transaction tx1 = session.beginTransaction();
            session.save(water);
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Water water) {

    }

    @Override
    public void delete(Water water) {

    }

    @Override
    public List<Water> findAll(int category) {
        String sql = category == 0? "FROM Water" : "FROM Water where category = " + category;
        return (List<Water>) HibernateSessionFactoryUtil.getSessionFactory("recipe").openSession().createQuery(sql).list();
    }
}
