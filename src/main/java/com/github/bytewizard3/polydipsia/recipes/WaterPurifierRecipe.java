package com.github.bytewizard3.polydipsia.recipes;

import com.github.bytewizard3.polydipsia.PolydipsiaMod;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WaterPurifierRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient recipeItems;

    public WaterPurifierRecipe(ResourceLocation id, ItemStack output, Ingredient recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return recipeItems.test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(recipeItems);
        return ingredients;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<WaterPurifierRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "water_purifying";
    }

    public static class Serializer implements RecipeSerializer<WaterPurifierRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(PolydipsiaMod.MODID, "water_purifying");

        @Override
        public WaterPurifierRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));

            return new WaterPurifierRecipe(pRecipeId, output, ingredient);
        }

        @Override
        public @Nullable WaterPurifierRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();

            return new WaterPurifierRecipe(pRecipeId, output, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, WaterPurifierRecipe pRecipe) {
            pRecipe.getIngredients().get(0).toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
