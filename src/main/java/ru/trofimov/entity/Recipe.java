package ru.trofimov.entity;


//import ru.trofimov.model.Crutch;
//import ru.trofimov.model.DirtyJob;

import ru.trofimov.model.Ingredient;
import ru.trofimov.model.Step;

public class Recipe {

    private int id;
    private String recipeName;
    private int category;
    private int portion;
    private int time;
    private String[] namesMainImage;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(String recipeName, int category, int portion, int hour, int min, String[] NamesMainImage, Ingredient[] ingredients, Step[] steps) {
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = hour * 60 + min;
        this.namesMainImage = NamesMainImage;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(int id, String recipeName, int category, int portion, int time, String namesMainImage, String ingredients, String steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.category = category;
        this.portion = portion;
        this.time = time;
        this.namesMainImage = namesMainImage.split("&\\*&");
        Ingredient[] ing = new Ingredient[ingredients.split("&\\*&").length];
        for (int i = 0; i < ing.length; i++) {
            String x = ingredients.split("&\\*&")[i];
            if (!x.equals("")) ing[i] = new Ingredient(x);
        }
        this.ingredients = ing;
        Step[] st = new Step[steps.split("&\\*&").length];
        for (int i = 0; i < st.length; i++) {
            String x = steps.split("&\\*&")[i];
            if (!x.equals("")) st[i] = new Step(x);
        }
        this.steps = st;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getTime() {
        int hour, min;
        min = time % 60;
        hour = (time - min) / 60;
        if (hour == 0 && min == 0) return "---";
        else if (hour > 0 && min == 0) return hour + correctHour(hour);
        else if (hour == 0 && min > 0) return min + " минут";
        else return hour + correctHour(hour) + min + " минут";

    }

    public int getHour() {
        return time / 60;
    }

    public int getMinute() {
        return time % 60;
    }

//    public String getLinkWithId() {
//        return Crutch.toTranscript(recipeName) + "-" + id;
//    }

    public int getId() {
        return id;
    }

    public int getCategoryInt() {
        return category;
    }

//    public String getCategory() {
//        return DirtyJob.intCategoryToString(category);
//    }

    public int getPortion() {
        return portion;
    }

    public String[] getNamesMainImage() {
        return namesMainImage;
    }

    public String getNamesMainImageOneLine() {
        StringBuilder sb = new StringBuilder();
        for (String x : namesMainImage)
            sb.append(x).append("!%!");
        return sb.toString();
    }

    public Ingredient[] getIngredients() {
        try {
            ingredients[0].toString();
            return ingredients;
        } catch (Exception e) {
            System.out.println("Отработал getIngredients.Exception");
            return new Ingredient[]{new Ingredient("", 0, 0)};
        }
    }

    public Step[] getSteps() {
        try {
            steps[0].toString();
            return steps;
        } catch (Exception e) {
            System.out.println("Отработал getSteps.Exception");
            return new Step[]{new Step("", "")};
        }
    }

    public void showFields() {
        System.out.println("id: " + id);
        System.out.println("recipeName: " + recipeName);
        System.out.println("category: " + category);
        System.out.println("portion: " + portion);
        System.out.println("time: " + time);
        System.out.println("namesMainImage:");
        for (String x : namesMainImage) {
            System.out.print("   ");
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println("ingredients: " + ingredients.length);
//        for (Ingredient x : ingredients) {
//            System.out.print("   ");
//            try {
//                System.out.print(x.show() + " ");
//            } catch (Exception e) {
//                System.out.println("Exception: " + e.toString());
//            }
//        }
        System.out.println();
        System.out.println("steps:");
//        for (Step x : steps) {
//            System.out.print("   ");
//            try {
//                System.out.print(x.show() + " ");
//            } catch (Exception e) {
//                System.out.println("Exception: " + e.toString());
//            }
//        }
        System.out.println();
        System.out.println("-------------------");
    }

    public String insertIntoDb(boolean add) {
        StringBuilder ingredient = new StringBuilder();
        for (Ingredient value : ingredients) {
            ingredient.append(value.toString()).append("&*&");
        }

        StringBuilder step = new StringBuilder();
        for (Step value : steps) {
            step.append(value.toString()).append("&*&");
        }

        StringBuilder nameMainImage = new StringBuilder();
        for (String value : namesMainImage) {
            nameMainImage.append(value).append("&*&");
        }
        if (add)
            return "VALUES ('" + recipeName + "', " + category + ", " + portion + ", " + time + ", '" + ingredient.toString() + "', '" + step.toString() + "', '" + nameMainImage.toString() + "');";
        else
            return "recipeName = '" + recipeName + "', category = " + category + ", portion = " + portion + " , cookingTime = " + time + " , " +
                    "ingredients = '" + ingredient.toString() + "', steps = '" + step.toString() + "', photos = '" + nameMainImage.toString() + "'";
    }

    private String correctHour(int hour) {
        int lastN = hour % 10;
        int penultN = (hour % 100 - lastN) / 10;
        if (lastN == 1 && penultN != 1) return " час ";
        if ((lastN == 2 || lastN == 3 || lastN == 4) && penultN != 1) return " часа ";
        else return " часов ";
    }
}
