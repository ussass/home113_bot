package ru.trofimov;

import com.google.inject.internal.cglib.transform.$ClassTransformer;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterControl;
import ru.trofimov.dao.WaterDaoImp;
import ru.trofimov.model.*;
import ru.trofimov.service.RecipeService;
import ru.trofimov.service.RecipeServiceImp;
import ru.trofimov.service.WaterService;
import ru.trofimov.service.WaterServiceImp;
import ru.trofimov.utils.HibernateSessionFactoryUtil;

import javax.persistence.criteria.CriteriaQuery;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main {
    private static void run() {
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

        WaterControl waterControl = new WaterControl("COM3");
        final Thread thread = new Thread(waterControl);

        startButton.addActionListener(e -> {
            thread.start();
//            if (Bot.startBot()) {
//                label.setText("Bot is working               ");
//            } else {
//                label.setText("An error has occurred");
//            }
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


//        List<WaterReading> list = new ArrayList<>();
//
//        Random random = new Random(1);
//
//        for (int i = 0; i < 4; i++) {
//            List<Integer> integerList = new ArrayList<>();
//            for (int j = 0; j < 12; j++) {
//                integerList.add(random.nextInt(10));
//            }
//            WaterReading waterReading = new WaterReading();
//            waterReading.setValues(integerList);
//            switch (i){
//                case 0:
//                    waterReading.setHot(true);
//                    waterReading.setMorning(true);
//                    break;
//                case 1:
//                    waterReading.setHot(true);
//                    waterReading.setMorning(false);
//                    break;
//                case 2:
//                    waterReading.setHot(false);
//                    waterReading.setMorning(true);
//                    break;
//                case 3:
//                    waterReading.setHot(false);
//                    waterReading.setMorning(false);
//                    break;
//            }
//            list.add(waterReading);
//        }
//
//
//
//        for (int x : list.get(0).getWaterList())
//            System.out.print(x + " ");
//
//        System.out.println();
//        System.out.println("list.get(0).getH1() = " + list.get(0).getH1());
//        System.out.println("list.get(0).getH2() = " + list.get(0).getH2());
//
//        String graph = DirtyJob.ListGraph(list, true, true);
//        System.out.println(graph);



//        WaterService service = new WaterServiceImp();
//        int[] nums = service.getWaterByDatePreviousAndNext(43);
//        System.out.println("-------------------");
//        for (int x : nums)
//            System.out.println(x);


//        CriteriaQuery<Water> criteria = builder.createQuery( Person.class );
//        Root<Person> personRoot = criteria.from( Person.class );
//        criteria.select( personRoot );
//        criteria.where( builder.equal( personRoot.get( Person_.eyeColor ), "brown" ) );
//        List<Person> people = em.createQuery( criteria ).getResultList();
//        for ( Person person : people ) { ... }


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


//        Random random = new Random();
//        System.out.println(random.nextInt(10));

//        Date date = new Date();
//        int myDate = (date.getYear() - 100) * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
//        int myTime = date.getHours() * 100 + date.getMinutes();
//        System.out.println("myTime = " + myTime);
//
//        WaterService service = new WaterServiceImp();
//        Water water = new Water(191018);
//
//        WaterReading hot = new WaterReading(true, true, water);
//        hot.setH0(random.nextInt(20));
//        hot.setH1(random.nextInt(20));
//        water.addReading(hot);
//
//        WaterReading hot1 = new WaterReading(true, false, water);
//        hot1.setH0(random.nextInt(20));
//        hot1.setH1(random.nextInt(20));
//        water.addReading(hot1);
//
//        WaterReading cold = new WaterReading(false, true, water);
//        cold.setH0(random.nextInt(20));
//        cold.setH1(random.nextInt(20));
//        water.addReading(cold);
//
//        WaterReading cold1 = new WaterReading(false, false, water);
//        cold1.setH0(random.nextInt(20));
//        cold1.setH1(random.nextInt(20));
//        water.addReading(cold1);
//
//        service.save(water);
//        userService.saveUser(user);
//

//        List<Water> waters = service.findAll();
//        System.out.println("waters.size() = " + waters.size());
//
//        int hot = 0;
//        int cold = 0;
//
//        for (Water x : waters) {
//            for (WaterReading reading : x.getWaterReadings()) {
//                if (reading.isHot()) hot += reading.sumAll();
//                else cold += reading.sumAll();
//            }
//            System.out.println("hot = " + hot);
//            System.out.println("cold = " + cold);
//        }
//
//
//        HibernateSessionFactoryUtil.close();

    }
}
