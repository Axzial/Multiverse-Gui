package fr.axzial.main;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import fr.axzial.main.Commands.Mvg;
import fr.axzial.main.Listeners.Listeners;

import java.util.*;
import java.util.regex.Pattern;

public class MultiverseCoreGui extends JavaPlugin {

    //////////////////////////////////////////// WORLDS LIST
    public ArrayList<String> unloaded = new ArrayList<>();
    public ArrayList<String> loaded = new ArrayList<>();
    public ArrayList<String> allworlds = new ArrayList<>();
    public HashMap<String, Integer> catnames = new HashMap<>();
    public HashMap<String, Integer> sortedcats = new HashMap<>();

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


    //////////////////////////////////////////// CLASS HASHMAP
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> catnames)
    {
        ///////////////////// NEW LIST OF ELEMENTS IN HASHMAP
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(catnames.entrySet());

        ////////////////////////////// COMPARE SORT LIST
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> t1,
                               Map.Entry<String, Integer> t2)
            {
                return (t2.getValue()).compareTo(t1.getValue());
            }
        });

        /////////////////////////////// RETURN IN HASHMAP
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    //////////////////////////////////////////// TIDY WORLDS IN LIST & CLASS CATS BY NUMBER OF MAPS IN IT
    public void tidyworlds(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run(){
                ////////////////////////////////////////////// CLEAR OLD LISTS
                unloaded.clear();
                loaded.clear();
                allworlds.clear();
                catnames.clear();

                ////////////////////////////////////////////// TIDY UNLOADED WORLDS
                for (String unlworlds : getCore().getMVWorldManager().getUnloadedWorlds()){
                    unloaded.add(unlworlds);
                    allworlds.add(unlworlds);
                    String[] Array = unlworlds.split(Pattern.quote("."));
                    if (catnames.containsKey(Array[0])){
                        int i = catnames.get(Array[0]) + 1;
                        catnames.remove(Array[0]);
                        catnames.put(Array[0], i);
                    }
                    else {
                        catnames.put(Array[0], 1);
                    }
                }

                ////////////////////////////////////////////// TIDY LOADED WORLDS
                for (MultiverseWorld worlds : getCore().getMVWorldManager().getMVWorlds()){
                    loaded.add(worlds.getName());
                    allworlds.add(worlds.getName());
                    String[] Array = worlds.getName().split(Pattern.quote("."));
                    if (catnames.containsKey(Array[0])){
                        int i = catnames.get(Array[0]) + 1;
                        catnames.remove(Array[0]);
                        catnames.put(Array[0], i);
                    }
                    else {
                        catnames.put(Array[0], 1);
                    }
                }

                ////////////////////////////////////////////////SORT HASHMAP WITH NEW LIST
                sortedcats = sortByValue(catnames);
            }
        }, 0, 100);
    }
}
