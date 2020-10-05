package ru.trofimov.model;

import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.entity.Recipe;
import ru.trofimov.parameters.DBParameters;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
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

    public static void saveWater(int hotWater, int coldWater, int date, int hour) {

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO watermeterreadings (date, hot1, hot2, hot3, hot4, hot5, hot6, hot7, hot8, hot9, hot10, hot11, hot12, hot13, hot14, hot15, hot16, hot17, hot18, hot19, hot20, hot21, hot22, hot23, hot0, cold1, cold2, cold3, cold4, cold5, cold6, cold7, cold8, cold9, cold10, cold11, cold12, cold13, cold14, cold15, cold16, cold17, cold18, cold19, cold20, cold21, cold22, cold23, cold0) VALUES(");
        builder.append(date).append(", ");
        for (int i = 0; i < 24; i++){
            if (i == hour - 1)
                builder.append(hotWater).append(",");
            else builder.append("0,");
        }
        for (int i = 0; i < 24; i++){
            if (i == hour - 1)
                builder.append(coldWater).append(",");
            else builder.append("0,");
        }
        builder.replace(builder.length() - 1, builder.length(), "");
        builder.append(");");

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.execute(builder.toString());
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
    }

    public static void updateWater(int myDate, int hotWater, int coldWater, int hour) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            System.out.println(myDate);
            statement.execute("UPDATE watermeterreadings SET hot" + hour +" = " + hotWater + ", cold" + hour +" = " + coldWater + " WHERE date =" + myDate + ";");
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!!!");
        }
    }

    public static int lastDateWater() {
        int lastDate = 0;
        int lastId = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, date FROM watermeterreadings;");
            while (resultSet.next()) {
                lastDate = resultSet.getInt("date");
                lastId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return lastDate;
    }

    public static List<WaterPerDay> findAllWater() {
        List<WaterPerDay> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM watermeterreadings;");
            while (resultSet.next()) {
                List<Integer> hotWater = new ArrayList<>(24);
                List<Integer> coldWater = new ArrayList<>(24);
                for (int i = 0; i < 24; i ++){
                    hotWater.add(resultSet.getInt("hot" + i));
                    coldWater.add(resultSet.getInt("cold" + i));
                }
                WaterPerDay water = new WaterPerDay(resultSet.getInt("date"), hotWater, coldWater);
                list.add(water);
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }
        return list;
    }

    public static WaterPerDay getWaterByDate(int date) {
        WaterPerDay water = new WaterPerDay(0, new ArrayList<>(), new ArrayList<>());
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM watermeterreadings WHERE date = " + date + ";");
            while (resultSet.next()) {
                List<Integer> hotWater = new ArrayList<>(24);
                List<Integer> coldWater = new ArrayList<>(24);
                for (int i = 0; i < 24; i ++){
                    hotWater.add(resultSet.getInt("hot" + i));
                    coldWater.add(resultSet.getInt("cold" + i));
                }
                water = new WaterPerDay(resultSet.getInt("date"), hotWater, coldWater);
            }
            return water;
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }

        return water;
    }

    public static List<Integer> getWaterByDatePreviousAndNext(int date) {
        List<Integer> list = new ArrayList<>();
        int[] dates = new int[2];
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from watermeterreadings\n" +
                    "where (\n" +
                    "    date = IFNULL((select min(date) from watermeterreadings where date > " + date + "),0)\n" +
                    "    or  date = IFNULL((select max(date) from watermeterreadings where date < " + date + "),0)\n" +
                    "       )");
            while (resultSet.next()) {
                list.add(resultSet.getInt("date"));
                if (resultSet.getInt("date") < date)
                    dates[0] = resultSet.getInt("date");
                if (resultSet.getInt("date") > date)
                    dates[1] = resultSet.getInt("date");
            }
        } catch (SQLException e) {
            System.out.println("Неудалось загрузить класс драйвера!");
        }

        for (int x : dates)
            System.out.println("x = " + x);

        return list;
    }



}


