package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;
import de.prog3.projektarbeit.eventHandling.events.data.player.BlockPlayerEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.BlockPlayerListener;
import de.prog3.projektarbeit.eventHandling.listeners.ui.OpenPageEventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BlockHandler {
    private static final Logger logger = LoggerFactory.getLogger(BlockHandler.class);
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
        logger.info("Registriere OpenPageEventListener");
        new OpenPageEventListener(Priority.HIGHEST) {
            @Override
            public void onEvent(OpenPageEvent event) {
                PageType type = event.getPageType();
                switch (type) {
                    case EDIT_PLAYER:
                        Player player = (Player) event.getArgs()[0];
                        if (blockedPlayers.contains(player)) {
                            logger.info("Spieler {} ist für weitere Bearbeitungen blockiert", player.getFullName());
                            event.setCancelled(true);
                        }
                        break;
                    case TRANSFER_PLAYER:
                        Player playerToTransfer = (Player) event.getArgs()[0];
                        if (blockedPlayers.contains(playerToTransfer)) {
                            logger.info("Spieler {} ist für Transfers blockiert", playerToTransfer.getFullName());
                            event.setCancelled(true);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        logger.info("Registriere BlockPlayerListener");
        new BlockPlayerListener() {
            @Override
            public void onEvent(BlockPlayerEvent event) {
                Player player = event.getPlayer();
                if (event.isBlocked() && blockedPlayers.contains(player)) {
                    logger.info("Spieler {} ist bereits blockiert", player.getFullName());
                }
                else if (event.isBlocked()) {
                    blockedPlayers.add(player);
                    logger.info("Spieler {} wurde blockiert", player.getFirstName());
                } else {
                    blockedPlayers.remove(player);
                    logger.info("Spieler {} wurde entsperrt", player.getFullName());
                }
            }
        };
    }
}