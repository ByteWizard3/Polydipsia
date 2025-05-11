package com.github.bytewizard3.polydipsia.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DamagedItemRecipeSerializer implements RecipeSerializer<DamagedItemRecipe> {

    @Override
    public DamagedItemRecipe fromJson(ResourceLocation id, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        CraftingBookCategory category = CraftingBookCategory.MISC;
        if (json.has("category")) {
            category = CraftingBookCategory.valueOf(GsonHelper.getAsString(json, "category").toUpperCase());
        }

        JsonArray pattern = GsonHelper.getAsJsonArray(json, "pattern");
        JsonObject key = GsonHelper.getAsJsonObject(json, "key");

        // Parse key
        Map<String, Ingredient> keyMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : key.entrySet()) {
            if (entry.getKey().length() != 1 || entry.getKey().equals(" ")) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "'");
            }
            keyMap.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }
        keyMap.put(" ", Ingredient.EMPTY); // space means empty slot

        // Convert pattern to ingredient list
        String[] patternLines = new String[pattern.size()];
        for (int i = 0; i < pattern.size(); i++) {
            patternLines[i] = pattern.get(i).getAsString();
        }

        int width = patternLines[0].length();
        int height = patternLines.length;

        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = patternLines[y];
            for (int x = 0; x < width; x++) {
                String keyChar = String.valueOf(line.charAt(x));
                Ingredient ingredient = keyMap.getOrDefault(keyChar, Ingredient.EMPTY);
                ingredients.set(x + y * width, ingredient);
            }
        }

        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
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
