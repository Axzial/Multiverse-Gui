package fr.axzial.main.Commands;

import fr.axzial.main.MultiverseCoreGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Mvg implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender Sender, Command command, String string, String[] args) {
        MultiverseCoreGui main = MultiverseCoreGui.getPlugin(MultiverseCoreGui.class);
        Player player = (Player)Sender;
        if (player instanceof Player){
            if (player.hasPermission("mvg.panel")){
                if (args.length == 0){ //////////////////////////////////////////// PANEL
                    Inventory panel = Bukkit.createInventory(null, 54, "§8MVG");

                    //////////////SPACER
                    ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE,1 ,(short)15);
                    ItemMeta glassmeta = glass.getItemMeta();
                    glassmeta.setDisplayName(" ");
                    glass.setItemMeta(glassmeta);
                    panel.setItem(9, glass);
                    panel.setItem(10, glass);
                    panel.setItem(11, glass);
                    panel.setItem(12, glass);
                    panel.setItem(13, glass);
                    panel.setItem(14, glass);
                    panel.setItem(15, glass);
                    panel.setItem(16, glass);
                    panel.setItem(17, glass);

                    int inv = 18;
                    for (String s : main.getConfig().getConfigurationSection("cats").getKeys(false)){
                        String name = String.valueOf(main.getConfig().getConfigurationSection("cats." + s).getString("name"));
                        String id = String.valueOf(main.getConfig().getConfigurationSection("cats." + s).getString("itemid"));
                        Boolean imp = main.getConfig().getConfigurationSection("cats." + s).getBoolean("important");
                        ItemStack item = new ItemStack(Material.getMaterial(id));
                        ItemMeta itemmeta = item.getItemMeta();
                        itemmeta.setDisplayName(ChatColor.AQUA + name);
                        ArrayList<String> itemlore = new ArrayList<>();
                        int mapcount = 0;
                        for (String str : main.loaded){
                            if (str.contains(name.toLowerCase())){
                                mapcount++;
                            }
                        }
                        for (String str : main.unloaded){
                            if (str.contains(name.toLowerCase())){
                                mapcount++;
                            }
                        }
                        itemlore.add("§eMaps: " + mapcount);
                        if (imp == true){
                            itemmeta.addEnchant(Enchantment.LURE, 1, true);
                        }
                        itemmeta.setLore(itemlore);
                        itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        item.setItemMeta(itemmeta);
                        panel.setItem(inv, item);
                        inv++;
                        inv++;
                    }
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
                    SkullMeta headmeta = (SkullMeta) head.getItemMeta();
                    headmeta.setOwner(player.getName());
                    headmeta.setDisplayName("§b" + player.getName());
                    head.setItemMeta(headmeta);
                    panel.setItem(0, head);

                    player.openInventory(panel);
                    return true;
                }
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("cat")){
                        if (!main.categories.isEmpty()){
                            player.sendMessage("§7MVG §8>> §fListe des catégories");
                            for (String s : main.categories){
                                player.sendMessage("§7- §f" + s);
                            }
                            player.sendMessage("---------------------------------");
                        }
                        else {
                            player.sendMessage("§7MVG §8>> §fLa liste des catégories est vide.");
                        }
                    }
                    return true;
                }
            }
            else {
                player.sendMessage("§cVous n'avez pas les permission nécéssaires pour effectuer cette commande (mvg.panel)");
            }

        }


        return false;
    }
}
