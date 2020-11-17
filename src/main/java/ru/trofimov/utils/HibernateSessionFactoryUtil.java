package ru.trofimov.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.trofimov.model.Recipe;
import ru.trofimov.model.Water;
import ru.trofimov.model.WaterReading;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory(String className){
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration().configure();
//                switch (className){
//                    case "recipe" :
                        configuration.addAnnotatedClass(Recipe.class);
//                        break;
//                    case "water" :
                        configuration.addAnnotatedClass(Water.class);
                        configuration.addAnnotatedClass(WaterReading.class);
//                        break;
//                }
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e){
                System.out.println("Исключение: " + e);
            }
        }
        return sessionFactory;
    }



    public static void close(){
        sessionFactory.close();
    }

}
