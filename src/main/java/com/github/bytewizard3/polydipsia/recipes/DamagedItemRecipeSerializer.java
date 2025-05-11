package com.github.bytewizard3.polydipsia.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;

public class DamagedItemRecipeSerializer implements RecipeSerializer<DamagedItemRecipe> {

    @Override
    public DamagedItemRecipe fromJson(ResourceLocation id, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");

        // Use CraftingBookCategory.valueOf to get category from the string
        CraftingBookCategory category = CraftingBookCategory.valueOf(GsonHelper.getAsString(json, "category", "MISC").toUpperCase());

        // Parse the pattern
        JsonArray patternArray = GsonHelper.getAsJsonArray(json, "pattern");
        int width = patternArray.size() > 0 ? patternArray.get(0).getAsString().length() : 0;
        int height = patternArray.size();

        // Parse ingredients
        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int i = 0; i < height; i++) {
            String row = patternArray.get(i).getAsString();
            for (int j = 0; j < width; j++) {
                char c = row.charAt(j);
                // Assign specific ingredient based on the character
                if (c == 'X') {
                    ingredients.set(i * width + j, Ingredient.of(Items.LEATHER)); // Example: 'X' maps to leather
                } else if (c == ' ') {
                    ingredients.set(i * width + j, Ingredient.EMPTY);
                }
            }
        }

        // Parse the result item
        JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = ShapedRecipe.itemStackFromJson(resultJson);

        boolean showNotification = GsonHelper.getAsBoolean(json, "show_notification", true);

        return new DamagedItemRecipe(id, group, category, width, height, ingredients, result, showNotification);
    }

    @Override
    public @Nullable DamagedItemRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        boolean showNotification = buffer.readBoolean();

        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int i = 0; i < ingredients.size(); ++i) {
            ingredients.set(i, Ingredient.fromNetwork(buffer));
        }

        ItemStack result = buffer.readItem();

        return new DamagedItemRecipe(id, group, category, width, height, ingredients, result, showNotification);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, DamagedItemRecipe recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.category());
        buffer.writeVarInt(recipe.getWidth());
        buffer.writeVarInt(recipe.getHeight());
        buffer.writeBoolean(recipe.showNotification());

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buffer);
        }

        buffer.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}
