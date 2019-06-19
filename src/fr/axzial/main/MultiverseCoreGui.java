package fr.axzial.main;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import fr.axzial.main.commands.Mvg;
import fr.axzial.main.listeners.Listeners;
import fr.axzial.main.utils.Utils;

import java.util.*;

public class MultiverseCoreGui extends JavaPlugin {

    /*                                                  Worlds List                                                   */
    public ArrayList<String> unloaded = new ArrayList<>();
    public ArrayList<String> loaded = new ArrayList<>();
    public ArrayList<String> allworlds = new ArrayList<>();
    public HashMap<String, Integer> catnames = new HashMap<>();
    public HashMap<String, Integer> sortedcats = new HashMap<>();


    private static MultiverseCoreGui instance;
    private static Utils utils;
    private MultiverseCore core;

    /*
                                                     Enable Method
                                                                                                                      */
    @Override
    public void onEnable(){

        instance = this;

        /*                                          Commands & Listeners                                              */
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        this.getCommand("mvg").setExecutor(new Mvg());


        /*                                             Check for MvCore                                               */
        this.core = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (this.core == null){
            getLogger().info("Multiverse-Core not found");
        }
        else {
            getLogger().info("Founded Multiverse-Core");
        }


        /*                                           Tidy World Call                                                  */
        utils.tidyworlds();

    }


    /*
                                                     Disable Method
                                                                                                                      */

    @Override
    public void onDisable(){

    }



    /*                                               getInstance Method                                               */
    public static MultiverseCoreGui getInstance(){
        return instance;
    }

    /*                                                 getCore Method                                                 */
    public MultiverseCore getCore() {
        return this.core;
    }
}
