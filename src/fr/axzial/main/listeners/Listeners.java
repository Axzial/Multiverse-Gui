package fr.axzial.main.listeners;

import fr.axzial.main.MultiverseCoreGui;
import fr.axzial.main.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Pattern;

public class Listeners implements Listener {

    @EventHandler
    public void onClickItem(InventoryClickEvent event) throws InterruptedException {

        MultiverseCoreGui main = MultiverseCoreGui.getInstance();

        Player player = (Player) event.getWhoClicked(); // GET PLAYER

        /*                                         Listen for MVG inventory                                           */
        if (player.getOpenInventory().getTitle().equalsIgnoreCase(Utils.colorize("&8MVG"))){
            /////////////////////////////////////////////// CANCEL OTHER EVENTS
            event.setCancelled(true);
            if (event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE || event.getCurrentItem().getType() == Material.AIR){
                event.setCancelled(true);
                return;
            }

            //////////////////////////////////////////////////////// OPEN CAT
            if (event.getCurrentItem().getItemMeta().getDisplayName().contains("§b")){

                String catname = event.getCurrentItem().getItemMeta().getDisplayName().replace("§b", ""); // REPLACE COLOR TO GET DEFAULT NAME


                Inventory worlds = Bukkit.createInventory(null, 54, Utils.colorize("§8MVG §f-> §b" + catname)); // NEW INV

                /////// ITEMS
                ItemStack green = new ItemStack(Material.WOOL, 1, (byte)5);
                ItemMeta greenmeta = green.getItemMeta();
                ItemStack red = new ItemStack(Material.WOOL, 1, (byte)14);
                ItemMeta redmeta = red.getItemMeta();

                int inv = 0;

                //////////////////////// WORLDS
                if (event.getCurrentItem().getType() != Material.SKULL_ITEM){
                    for (String f : main.loaded){ // GET LOADED WORLDS
                        String[] Array = f.split(Pattern.quote("."));
                        if (Array[0].equalsIgnoreCase(catname)){
                            ItemStack loaded = green.clone();
                            ItemMeta loadedmeta = loaded.getItemMeta();
                            loadedmeta.setDisplayName(f + "§a(Loaded)");
                            loaded.setItemMeta(loadedmeta);
                            worlds.setItem(inv, loaded); // SET ITEM
                            inv++;
                        }
                    }
                    for (String f : main.unloaded){ // GET UNLOADED
                        String[] Array = f.split(Pattern.quote("."));
                        if (Array[0].equalsIgnoreCase(catname)){
                            ItemStack unloaded = red.clone();
                            ItemMeta loadedmeta = unloaded.getItemMeta();
                            loadedmeta.setDisplayName(f + "§c(Unloaded)");
                            unloaded.setItemMeta(loadedmeta);
                            worlds.setItem(inv, unloaded); // SET ITEM
                            inv++;
                        }
                    }
                }
                else {
                    for (String f : main.loaded){ // GET LOADED WORLDS
                        if (f.contains(catname)){
                            ItemStack loaded = green.clone();
                            ItemMeta loadedmeta = loaded.getItemMeta();
                            loadedmeta.setDisplayName(f + "§a(Loaded)");
                            loaded.setItemMeta(loadedmeta);
                            worlds.setItem(inv, loaded); // SET ITEM
                            inv++;
                        }
                    }
                    for (String f : main.unloaded){ // GET UNLOADED
                        if (f.contains(catname.toLowerCase())){
                            ItemStack unloaded = red.clone();
                            ItemMeta loadedmeta = unloaded.getItemMeta();
                            loadedmeta.setDisplayName(f + "§c(Unloaded)");
                            unloaded.setItemMeta(loadedmeta);
                            worlds.setItem(inv, unloaded); // SET ITEM
                            inv++;
                        }
                    }
                }

                /////////////////////////////////// BACK ITEM
                ItemStack back = new ItemStack(Material.BARRIER);
                ItemMeta backmeta = back.getItemMeta();
                backmeta.setDisplayName("§cRetour");
                back.setItemMeta(backmeta);
                worlds.setItem(53, back);
                player.openInventory(worlds);
            }
        }

        ///////////////////////////////////////////// LISTEN FOR MVG WORLDS INV
        if (player.getOpenInventory().getTitle().contains(Utils.colorize("§8MVG §f->"))){
            ItemStack clicked = event.getCurrentItem();
            /////////////////////////////////////////////// CANCEL OTHER EVENTS
            event.setCancelled(true);
            if (event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE || event.getCurrentItem().getType() == Material.AIR){
                event.setCancelled(true);
                return;
            }

            ////////////////////////////////////////////// OPEN MAIN CATS
            if (clicked.getType() == Material.BARRIER){
                player.performCommand("mvg");
            }

            ////////////////////////////////////////////// UNLOADED WORLD
            if (clicked.getItemMeta().getDisplayName().contains("Unloaded")){
                String nameserv = event.getCurrentItem().getItemMeta().getDisplayName().replace("§c(Unloaded)", "").toLowerCase();
                player.closeInventory();
                player.performCommand("mv load " + nameserv);
                Thread.sleep(1000);
                player.performCommand("mv tp " + nameserv);
            }

            ///////////////////////////////////////////// LOADED WORLDS
            if (clicked.getItemMeta().getDisplayName().contains("Loaded")){
                String nameserv = event.getCurrentItem().getItemMeta().getDisplayName().replace("§a(Loaded)", "").toLowerCase();
                player.closeInventory();
                player.performCommand("mv tp " + nameserv);
            }


        }
    }
}