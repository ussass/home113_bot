package ru.trofimov.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.trofimov.model.Water;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class WaterDaoImp implements WaterDao {
    @Override
    public Water findById(int id) {
        Water water;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            water = session.get(Water.class, id);
        }
        return water;
    }

    @Override
    public void save(Water water) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(water);
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.toString());
        }
    }

    @Override
    public void merge(Water water) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.merge(water);
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e.toString());
        }
    }

    @Override
    public void delete(Water water) {

    }

    @Override
    public List<Water> findAll() {
        return (List<Water>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Water").list();
    }

    @Override
    public Water getWaterByDate(int date) {
        return (Water) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("FROM Water WHERE water_date = " + date).uniqueResult();
    }

//    public List<Water> getWaterByDatePreviousAndNext(int date) {
////        String sql = "FROM Water\n" +
////                "WHERE (\n" +
////                "    water_date = coalesce((select min(water_date) from Water where water_date > " + date + "),0)\n" +
////                "    or  water_date = coalesce((select max(water_date) from Water where water_date < " + date + "),0)\n" +
////                "       )";
//
////        String sql = "FROM Water WHERE (water_date = coalesce((select min(water_date) FROM Water WHERE water_date > 201115),0)  or  water_date = coalesce((select max(water_date) FROM Water WHERE water_date < 201115),0))";
//        String sql = "from Water where water_date = (select MIN(water_date) from Water where water_date > 201116)";
//
//        return (List<Water>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(sql).list();
//    }

}
