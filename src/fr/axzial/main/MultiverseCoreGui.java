package fr.axzial.main;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fr.axzial.main.Commands.Mvg;
import fr.axzial.main.Listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class MultiverseCoreGui extends JavaPlugin {

    //////////////////////////////////////////// WORLDS LIST
    public ArrayList<String> unloaded = new ArrayList<>();
    public ArrayList<String> loaded = new ArrayList<>();
    public ArrayList<String> categories = new ArrayList<>();

    //////////////////////////////////////////// MV CORE
    private MultiverseCore core;

    //////////////////////////////////////////// ENABLE
    @Override
    public void onEnable(){

        //////////////////////////////////////////// COMMANDS & LISTENERS
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        this.getCommand("mvg").setExecutor(new Mvg());

        //////////////////////////////////////////// CHECK IF MVCORE EXIST
        this.core = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (this.core == null){
            getLogger().info("Multiverse-Core not found");
        }
        else {
            getLogger().info("Founded Multiverse-Core");
        }

        //////////////////////////////////////////// TIDY WORLDS
        tidyworlds();

    }


    //////////////////////////////////////////// DISABLE
    @Override
    public void onDisable(){

    }



    //////////////////////////////////////////// GET MVCORE
    public MultiverseCore getCore() { return this.core; }


    //////////////////////////////////////////// TIDY WORLDS IN LIST
    public void tidyworlds(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run(){
                unloaded.clear();
                loaded.clear();
                for (String unlworlds : getCore().getMVWorldManager().getUnloadedWorlds()){
                    unloaded.add(unlworlds);
                }
                for (MultiverseWorld worlds : getCore().getMVWorldManager().getMVWorlds()){
                    loaded.add(worlds.getName());
                }
            }
        }, 0, 100);
    }
}
