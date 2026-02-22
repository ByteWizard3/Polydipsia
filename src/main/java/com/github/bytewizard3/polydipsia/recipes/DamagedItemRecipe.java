package com.github.bytewizard3.polydipsia.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class DamagedItemRecipe extends ShapedRecipe {

    public DamagedItemRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult, boolean pShowNotification) {
        super(pId, pGroup, pCategory, pWidth, pHeight, pRecipeItems, pResult, pShowNotification);
    }

    public DamagedItemRecipe(ResourceLocation pId, String pGroup, CraftingBookCategory pCategory, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
        super(pId, pGroup, pCategory, pWidth, pHeight, pRecipeItems, pResult);
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        ItemStack result = super.getResultItem(access).copy();
        result.setDamageValue(result.getMaxDamage()); // Fully damaged
        return result;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        return super.matches(inv, level);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        return getResultItem(access); // Craft the damaged item
    }
}
