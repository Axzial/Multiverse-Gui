package fr.axzial.main.utils;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import fr.axzial.main.MultiverseCoreGui;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {

    private static MultiverseCoreGui main;

    public Utils(){
        throw new RuntimeException("Cannot create instance of Utils.class");
    }

    /*
                                                     Colorize
                                                                                                                      */
    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /*
                                                     Sort HashMap
                                                                                                                      */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> catnames)
    {
        /*                                     New List of Elements in HashMap                                        */
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(catnames.entrySet());

        /*                                               Sort List                                                    */
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> t1,
                               Map.Entry<String, Integer> t2)
            {
                return (t2.getValue()).compareTo(t1.getValue());
            }
        });

        /*                                             Return HashMap                                                 */
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /*
                                                   Tidy Worlds Scheduler
                                                                                                                      */
    public void tidyworlds(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
            public void run(){
                /*                                         Clear Old List                                             */
                main.unloaded.clear();
                main.loaded.clear();
                main.allworlds.clear();
                main.catnames.clear();

                main.getLogger().info("Tidy");

                /*                                      Tidy Unloaded in List                                         */
                for (String unlworlds : main.getCore().getMVWorldManager().getUnloadedWorlds()){
                    main.unloaded.add(unlworlds);
                    main.allworlds.add(unlworlds);
                    String[] array = unlworlds.split(Pattern.quote("."));
                    if (main.catnames.containsKey(array[0])){
                        int i = main.catnames.get(array[0]) + 1;
                        main.catnames.remove(array[0]);
                        main.catnames.put(array[0], i);
                    }
                    else {
                        main.catnames.put(array[0], 1);
                    }
                }

                /*                                       Tidy Loaded in List                                          */
                for (MultiverseWorld worlds : main.getCore().getMVWorldManager().getMVWorlds()){
                    main.loaded.add(worlds.getName());
                    main.allworlds.add(worlds.getName());
                    String[] array = worlds.getName().split(Pattern.quote("."));
                    if (main.catnames.containsKey(array[0])){
                        int i = main.catnames.get(array[0]) + 1;
                        main.catnames.remove(array[0]);
                        main.catnames.put(array[0], i);
                    }
                    else {
                        main.catnames.put(array[0], 1);
                    }
                }

                /*                                          Sort HashMap                                              */
                main.sortedcats = sortByValue(main.catnames);
            }
        }, 0, 100);
    }

}
