package ru.trofimov;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.entity.Ingredient;
import ru.trofimov.entity.Step;
import ru.trofimov.model.DirtyJob;
import ru.trofimov.model.Recipe;
import ru.trofimov.model.WorkWithDB;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static void main(String[] args)
    {
//        run();
        RecipeService service = new RecipeServiceImp();
//        List<Recipe> list = service.findAll();
//        for (Recipe x : list){
//            System.out.println(x.getRecipeName());
//        }
        Recipe recipe = service.findById(51);
        recipe.initializationOfDependentClasses();
//        System.out.println(recipe.getIngredientsClass()[0].toString());
        for (Ingredient x : recipe.getIngredients())
            System.out.println(x.getIngredientName() + ": " + x.getQuantity() + " " + x.getMeasure());
        System.out.println("------------------");
        for (Step x : recipe.getSteps())
            System.out.println(x.getPathToImage() + ": " + x.getDescription());
        System.out.println("------------------");
        System.out.println(recipe.getRecipeName());
        System.out.println(recipe.getIngredientsString());
    }
}
