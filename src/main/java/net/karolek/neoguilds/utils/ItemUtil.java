package net.karolek.neoguilds.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static ItemStack itemstackFromString(String string) {
        int amount;
        Material material;
        short data = -1;
        String[] splits = string.split(" ");
        if (splits.length != 2) return null;
        amount = Integer.parseInt(splits[0]);
        String[] splits2 = splits[1].split("@");
        material = Material.matchMaterial(splits2[0]);
        if (splits2.length > 1)
            data = Short.parseShort(splits2[1]);

        if (data == -1) {
            return new ItemStack(material, amount);
        } else {
            return new ItemStack(material, amount, data);
        }
    }

    public static List<ItemStack> getItems(List<String> items, double modifier) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for (String s : items) {

            ItemStack i = itemstackFromString(s);

            int amount = i.getAmount();
            if (modifier != 1D)
                amount = (int) Math.round((double) amount * modifier);
            i.setAmount(amount);

            itemStacks.add(i);
        }

        return itemStacks;
    }

    public static boolean checkItems(List<ItemStack> items, Player p) {
        for (ItemStack item : items)
            if (!p.getInventory().containsAtLeast(item, item.getAmount()))
                return false;
        return true;
    }

    public static void removeItems(List<ItemStack> items, Player player) {
        player.getInventory().removeItem(items.toArray(new ItemStack[items.size()]));
        // for(ItemStack item : items) {
        //    player.getInventory().remove(item);
        // }

    }

    public static boolean checkAndRemove(List<ItemStack> items, Player player) {
        boolean has = checkItems(items, player);
        if (has)
            removeItems(items, player);
        return has;
    }

}
