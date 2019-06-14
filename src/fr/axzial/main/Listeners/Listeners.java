package fr.axzial.main.Listeners;

import fr.axzial.main.MultiverseCoreGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Listeners implements Listener {

    @EventHandler
    public void onClickItem(InventoryClickEvent event) throws InterruptedException {
        MultiverseCoreGui main = MultiverseCoreGui.getPlugin(MultiverseCoreGui.class);
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTitle().equalsIgnoreCase("§8MVG")){
            event.setCancelled(true);
            if (event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE || event.getCurrentItem().getType() == Material.AIR){
                event.setCancelled(true);
                return;
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§b")){
                String catname = event.getCurrentItem().getItemMeta().getDisplayName().replace("§b", "");

                Inventory servs = Bukkit.createInventory(null, 54, "§8MVG §f-> §b" + catname);
                int inv = 0;
                for (String s : main.loaded){
                    if (s.contains(catname.toLowerCase())){
                        ItemStack item = new ItemStack(Material.WOOL,1 , (byte)5);
                        ItemMeta itemmeta = item.getItemMeta();
                        itemmeta.setDisplayName(s + "§a(Loaded)");
                        item.setItemMeta(itemmeta);
                        servs.setItem(inv, item);
                        inv++;
                    }
                }
                for (String s : main.unloaded){
                    if (s.contains(catname.toLowerCase())){
                        ItemStack item = new ItemStack(Material.WOOL,1 , (byte)14);
                        ItemMeta itemmeta = item.getItemMeta();
                        itemmeta.setDisplayName(s + "§c(Unloaded)");
                        item.setItemMeta(itemmeta);
                        servs.setItem(inv, item);
                        inv++;
                    }

                }
                ItemStack back = new ItemStack(Material.BARRIER);
                ItemMeta backmeta = back.getItemMeta();
                backmeta.setDisplayName("§cRetour");
                back.setItemMeta(backmeta);
                servs.setItem(45, back);
                player.openInventory(servs);
            }
        }
        if (player.getOpenInventory().getTitle().contains("§8MVG §f->")){
            event.setCancelled(true);
            if (event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType() == Material.BARRIER){
                player.performCommand("mvg");
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Unloaded")){
                String nameserv = event.getCurrentItem().getItemMeta().getDisplayName().replace("§c(Unloaded)", "").toLowerCase();
                player.closeInventory();
                player.performCommand("mv load " + nameserv);
                Thread.sleep(1000);
                player.performCommand("mv tp " + nameserv);
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Loaded")){
                String nameserv = event.getCurrentItem().getItemMeta().getDisplayName().replace("§a(Loaded)", "").toLowerCase();
                player.closeInventory();
                player.performCommand("mv tp " + nameserv);
            }


        }
    }
}