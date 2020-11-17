package ru.trofimov;

import ru.trofimov.Bot.Bot;
import ru.trofimov.model.*;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;

public class Main {
    private static void run()
    {
        final JFrame window = new JFrame("Caption");

        ImageIcon img = new ImageIcon("java.png");
        window.setIconImage(img.getImage());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setColumns(14);


        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton exitButton = new JButton("Exit");

        final JLabel label = new JLabel("Bot is not running         ");

//        WaterControl waterControl = new WaterControl("COM3");
//        final Thread thread = new Thread(waterControl);

        startButton.addActionListener(e -> {
//            thread.start();
            if (Bot.startBot()) {
                label.setText("Bot is working               ");
            } else {
                label.setText("An error has occurred");
            }
        });
        startButton.setFocusable(true);

        exitButton.addActionListener(e -> {
            window.setVisible(false);
            System.exit(0);
        });



        panel.add(startButton);
        panel.add(label);
        panel.add(exitButton);


        window.getContentPane().add(panel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        run();

//        RecipeService service = new RecipeServiceImp();
//        List<Recipe> list = service.findAll();
//        for (Recipe x : list)
//            System.out.println(x.getRecipeName());

//        Recipe recipe = service.findById(50);
//        recipe.initializationOfDependentClasses();
//        System.out.println(recipe.getRecipeName());
//        System.out.println(recipe.getIngredients()[1].getIngredientName());
//        System.out.println(recipe.getSteps()[0].getDescription());
//        System.out.println(PreparationOfTheRecipe.getRecipe(recipe, 2));


//        Date date = new Date();
////        int myDate = (date.getYear() - 100) * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
//        int myTime = date.getHours() * 100 + date.getMinutes();
//        System.out.println("myTime = " + myTime);
//
//        WaterService service = new WaterServiceImp();
//        Water water = new Water(myTime);
//
//        WaterReading hot = new WaterReading(true, true, water);
//        hot.setH0(0);
//        hot.setH1(1);
//        water.addReading(hot);
//
//        WaterReading hot1 = new WaterReading(true, false, water);
//        hot1.setH0(10);
//        hot1.setH1(11);
//        water.addReading(hot1);
//
//        WaterReading cold = new WaterReading(false, true, water);
//        cold.setH0(2);
//        cold.setH1(3);
//        water.addReading(cold);
//
//        WaterReading cold1 = new WaterReading(false, false, water);
//        cold1.setH0(12);
//        cold1.setH1(13);
//        water.addReading(cold1);
//
//        service.save(water);
////        userService.saveUser(user);
//
//        HibernateSessionFactoryUtil.close();

    }
}
