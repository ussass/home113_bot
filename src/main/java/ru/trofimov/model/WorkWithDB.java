package ru.trofimov.model;

import ru.trofimov.entity.Recipe;
import ru.trofimov.parameters.DBParameters;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkWithDB {

    private static final String URL = DBParameters.getUrl();
    private static final String USERNAME = DBParameters.getUserName();
    private static final String PASSWORD = DBParameters.getPassword();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public static List<Recipe> findAll(int category) {
        String sql;
        if (category == 0) sql = "SELECT * FROM recipes;";
        else sql = "SELECT * FROM recipes WHERE category = " + category + " ORDER BY id DESC;";
        List<Recipe> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                Recipe recipe = new Recipe(resultSet.getInt("id"), resultSet.getString("recipeName"), resultSet.getInt("category"),
                        resultSet.getInt("portion"), resultSet.getInt("cookingTime"), resultSet.getString("photos"),
                        resultSet.getString("ingredients"), resultSet.getString("steps"));
                list.add(recipe);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return list;
    }

    public static int save(Recipe recipe) {
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO recipes (recipeName, category, portion, cookingTime, ingredients, steps, photos) " + recipe.insertIntoDb(true));
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return lastId;
    }

    public static Recipe read(int id) {

        Recipe recipe;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM recipes WHERE id = " + id + ";");

            if (resultSet.next()) {
                recipe = new Recipe(resultSet.getInt("id"), resultSet.getString("recipeName"), resultSet.getInt("category"),
                        resultSet.getInt("portion"), resultSet.getInt("cookingTime"), resultSet.getString("photos"),
                        resultSet.getString("ingredients"), resultSet.getString("steps"));
            } else
                recipe = new Recipe(id, "No", 0, 0, 0, "No-Image-Found.png", "No&%&1&%&0", "111111111111111111&%&St&No-Image-Found.png");
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
            recipe = new Recipe(id, "No", 0, 0, 0, "No-Image-Found.png", "No&%&1&%&0", "111111111111111111&%&St&No-Image-Found.png");
        }

        return recipe;
    }

    public static void delete(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM recipes WHERE id=" + id + ";");
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
    }

    public static void update(int id, Recipe recipe) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE recipes SET " + recipe.insertIntoDb(false) + " WHERE id = " + id + ";");
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
    }

    public static int saveWater(int hotWater, int coldWater, Date date) {
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO watermeterreadings (date, hot1, hot2, hot3, hot4, hot5, hot6, hot7, hot8, hot9, hot10, hot11, hot12, hot13, hot14, hot15, hot16, hot17, hot18, hot19, hot20, hot21, hot22, hot23, hot24, cold1, cold2, cold3, cold4, cold5, cold6, cold7, cold8, cold9, cold10, cold11, cold12, cold13, cold14, cold15, cold16, cold17, cold18, cold19, cold20, cold21, cold22, cold23, cold24) " +
                    "VALUES(" + date.getTime() + ", " + hotWater + ",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0," + coldWater + ",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);");
            ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return lastId;
    }

    public static void updateWater(int id, int hotWater, int coldWater, int hour) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE watermeterreadings SET hot" + hour +" = " + hotWater + ", cold" + hour +" = " + coldWater + " WHERE id = " + id + ";");
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
    }

}


