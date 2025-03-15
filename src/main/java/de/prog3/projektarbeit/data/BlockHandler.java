package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;
import de.prog3.projektarbeit.eventHandling.events.data.player.BlockPlayerEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.BlockPlayerListener;
import de.prog3.projektarbeit.eventHandling.listeners.ui.OpenPageEventListener;
import de.prog3.projektarbeit.ui.pages.PageType;

import java.util.ArrayList;

public class BlockHandler {
    private static BlockHandler instance;
    private final ArrayList<Player> blockedPlayers;

    private BlockHandler() {
        this.blockedPlayers = new ArrayList<>();
        registerListeners();
    }

    public static BlockHandler getInstance() {
        if (instance == null) {
            instance = new BlockHandler();
        }
        return instance;
    }


    private void registerListeners() {
        new OpenPageEventListener(Priority.HIGHEST) {
            @Override
            public void onEvent(OpenPageEvent event) {
                PageType type = event.getPageType();
                switch (type) {
                    case EDIT_PLAYER:
                        Player player = (Player) event.getArgs()[0];
                        if(blockedPlayers.contains(player)) {
                            event.setCancelled(true);
                        }
                        break;
                    case TRANSFER_PLAYER:
                        // System.out.println(event.getArgs().length);
                        break;
                    default:
                        break;
                }
            }
        };

        new BlockPlayerListener(){
            @Override
            public void onEvent(BlockPlayerEvent event) {
                if(event.isBlocked()){
                    blockedPlayers.add(event.getPlayer());
                } else {
                    blockedPlayers.remove(event.getPlayer());
                }
            }
        };

    }

}