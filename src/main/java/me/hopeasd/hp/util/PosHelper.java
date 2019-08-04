package me.hopeasd.hp.util;

import org.bukkit.Location;

public class PosHelper {


    public static  double TwoDistance(final Location a, final Location b){
        return Math.sqrt((Math.pow((a.getX() - b.getX()), 2)) + (Math.pow((a.getZ() - b.getZ()), 2)));
    }

    // 5,-2   0,0
    public static String TwoPosition(final Location a, final Location b){
        int ns = a.getBlockZ() - b.getBlockZ();
        int ew = a.getBlockX() - b.getBlockX();
        StringBuilder sb = new StringBuilder();
        if (ew> 0 ){sb.append("%west"); }else if (ew< 0){ sb.append("%east");};
        if (ns> 0){sb.append("%north");}else if (ns< 0){ sb.append("%south");};

        return positionFormat(sb.toString());
    }

    private static String positionFormat(String s){
        return s.replace("%north","北").
                replace("%south","南").
                replace("%west","西").
                replace("%east","东");
    }

}
