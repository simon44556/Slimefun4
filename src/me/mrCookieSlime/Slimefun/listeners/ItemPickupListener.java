package me.mrCookieSlime.Slimefun.listeners;

import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.SlimefunStartup;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Listens to the ItemPickup events to prevent it if the item has the "no_pickup" metadata or is an ALTAR_PROBE.
 *
 * @since 4.1.11
 */
public class ItemPickupListener implements Listener {

    public ItemPickupListener(SlimefunStartup plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (e.getItem().hasMetadata("no_pickup")) e.setCancelled(true);
        else if (!e.getItem().hasMetadata("no_pickup") && e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName() && e.getItem().getItemStack().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', "&5&dALTAR &3Probe - &e"))) {
            e.setCancelled(true);
            e.getItem().remove();
        }
    }

    @EventHandler
    public void onMinecartPickup(InventoryPickupItemEvent e) {
        if (e.getItem().hasMetadata("no_pickup")) e.setCancelled(true);
        else if (!e.getItem().hasMetadata("no_pickup") && e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName() && e.getItem().getItemStack().getItemMeta().getDisplayName().startsWith(ChatColor.translateAlternateColorCodes('&', "&5&dALTAR &3Probe - &e"))) {
            e.setCancelled(true);
            e.getItem().remove();
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onItemPickup(PlayerPickupItemEvent e)
    {
        Player player11 = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();
        if ((item.getType() == Material.SKULL_ITEM) && (item.getDurability() == 3) && (item.hasItemMeta()) && (((SkullMeta)item.getItemMeta()).hasOwner()) && (((SkullMeta)item.getItemMeta()).getOwner().equalsIgnoreCase("cscorelib"))) {
            if ((!item.getItemMeta().hasDisplayName()) || ((!item.getItemMeta().getDisplayName().contains("Backpack")) && (!item.getItemMeta().getDisplayName().contains("Cooler")) && (!item.getItemMeta().getDisplayName().contains("Soul Jar"))))
            {
                String texture = CustomSkull.getTexture(item);
                if ((SlimefunItem.getByItem(item) == null) && (SlimefunItem.map_texture.containsKey(texture))) {
                    if (item.getAmount() != 1)
                    {
                        SlimefunItem sf = SlimefunItem.getByID((String)SlimefunItem.map_texture.get(texture));
                        if (sf.getID().contains("HOTBAR_PET")) {
                            return;
                        }
                        ItemStack sfItem = sf.getItem().clone();
                        sfItem.setAmount(item.getAmount());
                        e.getItem().getItemStack().setItemMeta(sfItem.getItemMeta());
                        System.out.println("Slimefun Fix > " + sfItem.getAmount() + "x " + sf.getID() + " drop fixed for player " + player11.getName());
                    }
                    else
                    {
                        SlimefunItem sf = SlimefunItem.getByID((String)SlimefunItem.map_texture.get(texture));
                        if (sf.getID().contains("HOTBAR_PET")) {
                            return;
                        }
                        ItemStack sfItem = sf.getItem().clone();
                        e.getItem().getItemStack().setItemMeta(sfItem.getItemMeta());
                        System.out.println("Slimefun Fix > " + sf.getID() + " drop fixed for player " + player11.getName());
                    }
                }
            }
        }
    }
}
