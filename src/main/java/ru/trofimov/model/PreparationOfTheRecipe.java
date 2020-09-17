package ru.trofimov.model;

import ru.trofimov.entity.Recipe;

public class PreparationOfTheRecipe {

    public static String getRecipe(int id){
        Recipe recipe = WorkWithDB.read(id);
        return  getRecipePrivate(recipe, recipe.getPortion());
    }

    public static String getRecipe(int id, int portions){
        Recipe recipe = WorkWithDB.read(id);
        return  getRecipePrivate(recipe, portions);
    }

    private static String getRecipePrivate(Recipe recipe, int portions){
        StringBuilder builder = new StringBuilder();

        builder.append(recipe.getRecipeName()).append('\n');
        builder.append("Количество порций: ").append(portions).append('\n');
        builder.append("Время приготовления: ").append(recipe.getTime()).append("\n\n");
        builder.append("Ингредиенты:").append('\n');
        for (int i = 0; i < recipe.getIngredients().length; i ++){
            if (recipe.getIngredients()[i].getIngredientName().equals("")) continue;
            builder.append(recipe.getIngredients()[i].getIngredientName()).append(" - ");
            double quantity = (double) recipe.getIngredients()[i].getQuantity()/recipe.getPortion()*portions;
            if (quantity == (int) quantity) builder.append((int) quantity).append(" ");
            else builder.append(quantity).append(" ");
            builder.append(recipe.getIngredients()[i].getMeasure()).append('\n');
        }
        builder.append('\n');
        builder.append("Способ приготовления:").append('\n');
        for (int i = 0; i < recipe.getSteps().length; i ++){
            if (recipe.getSteps()[i].getDescription().equals("")) continue;
            builder.append(i + 1).append(". ");
            builder.append(recipe.getSteps()[i].getDescription()).append('\n');
        }
        builder.append('\n');
        builder.append("Изменить количество порций: ");
        return builder.toString();
    }


}
