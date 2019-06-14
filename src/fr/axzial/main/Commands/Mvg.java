package fr.axzial.main.Commands;

import fr.axzial.main.MultiverseCoreGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Mvg implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender Sender, Command command, String string, String[] args) {

        MultiverseCoreGui main = MultiverseCoreGui.getPlugin(MultiverseCoreGui.class); // GET MAIN CLASS

        Player player = (Player)Sender;

        if (player instanceof Player){

            if (player.hasPermission("mvg.panel")){

                Inventory panel = Bukkit.createInventory(null, 54, "§8MVG");

                //////////////////////////////// PLAYER HEAD
                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
                SkullMeta headmeta = (SkullMeta) head.getItemMeta();
                headmeta.setOwner(player.getName());
                headmeta.setDisplayName("§b" + player.getName());
                head.setItemMeta(headmeta);
                ItemStack headclone = head.clone();
                panel.setItem(0, headclone); // ADD ITEM

                //////////////////////////////// SPACER GLASS
                ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE,1 ,(short)15);
                ItemMeta glassmeta = glass.getItemMeta();
                glassmeta.setDisplayName(" ");
                glass.setItemMeta(glassmeta);
                ItemStack glassclone = glass.clone();
                for (int x = 9; x > 18; x++){
                    panel.setItem(x, glassclone); // ADD ITEM
                }

                //////////////////////////////// AUTO CATS
                int inv = 18;
                ItemStack cat = new ItemStack(Material.BOOK);
                ItemMeta catmeta = cat.getItemMeta();
                ArrayList<String> catlore = new ArrayList<>();
                for (String s : main.sortedcats.keySet()){
                    int maps = main.sortedcats.get(s);
                    catmeta.setDisplayName(ChatColor.AQUA + s);
                    catlore.add("§eMaps: " + maps);
                    catmeta.setLore(catlore);
                    cat.setItemMeta(catmeta);
                    ItemStack catclone = cat.clone();
                    panel.setItem(inv, catclone);
                    inv++;
                }

                player.openInventory(panel);
                return true;
            }


            //////////////////////////////////////////////// NO PERMS
            else {

                player.sendMessage("§cVous n'avez pas les permission nécéssaires pour effectuer cette commande (mvg.panel)");

            }
        }
        return false;
    }
}
