package ru.trofimov.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.trofimov.model.Recipe;
import ru.trofimov.model.Water;
import ru.trofimov.model.WaterReading;

import javax.persistence.criteria.CriteriaQuery;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Recipe.class);
                configuration.addAnnotatedClass(Water.class);
                configuration.addAnnotatedClass(WaterReading.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());


            } catch (Exception e) {
                System.out.println("Исключение: " + e);
            }
        }

        return sessionFactory;
    }


    public static void close() {
        sessionFactory.close();
    }

}
