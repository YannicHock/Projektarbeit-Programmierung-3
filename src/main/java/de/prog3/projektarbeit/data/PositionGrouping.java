package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;

public enum PositionGrouping {
    GOALKEEPER("Torwart", new ArrayList<>(){{
        add(Position.GK);
    }}),
    DEFENDER("Verteidiger", new ArrayList<>(){{
        add(Position.RB);
        add(Position.LB);
        add(Position.CB);
    }}),
    MIDFIELDER("Mittelfeldspieler", new ArrayList<>(){{
        add(Position.CDM);
        add(Position.RM);
        add(Position.LM);
        add(Position.CM);
        add(Position.CAM);
    }}),
    STRIKER("Stürmer", new ArrayList<>(){{;
        add(Position.ST);
        add(Position.RW);
        add(Position.LW);
        add(Position.RS);
        add(Position.LS);
    }});

    final String friendlyName;
    final ArrayList<Position> grouping;
    PositionGrouping(String friendlyName, ArrayList<Position> grouping){
        this.friendlyName = friendlyName;
        this.grouping = grouping;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public static PositionGrouping getGrouping(Player player){
        HashMap<PositionGrouping, Integer> positionCount = new HashMap<>();
        for(Position position : player.getPositions()){
            for(PositionGrouping grouping : PositionGrouping.values()){
                if(grouping.grouping.contains(position)){
                    positionCount.put(grouping, positionCount.getOrDefault(grouping, 0) + 1);
                }
            }
        }
        PositionGrouping mostCommonGrouping = null;
        int mostCommonGroupingCount = 0;
        for(PositionGrouping grouping : positionCount.keySet()){
            if(positionCount.get(grouping) > mostCommonGroupingCount){
                mostCommonGrouping = grouping;
                mostCommonGroupingCount = positionCount.get(grouping);
            }
        }
        return mostCommonGrouping;
    }
}
