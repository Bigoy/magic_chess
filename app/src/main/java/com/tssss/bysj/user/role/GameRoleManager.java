package com.tssss.bysj.game.role;

import android.util.Log;

import com.tssss.bysj.user.role.GameRole;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage the players that will be used on the current client, usually only two players
 * GameRole self and GameRole army
 *
 * @author Tssss
 * @date updated January 7, 2019
 */
public class GameRoleManager {
    private static GameRoleManager gameRoleManager;

    public static String SELF = "self";
    public static String ARMY = "army";

    private Map<String, GameRole> players;

    private GameRoleManager() {
        players = new HashMap<>();
    }

    public static GameRoleManager getGameRoleManager() {
        if (gameRoleManager == null) {
            gameRoleManager = new GameRoleManager();
        }
        return gameRoleManager;
    }

    /**
     * Add a gameRole
     *
     * @param key    identity of the gameRole
     * @param gameRole instance of GameRole
     */
    public void addPlayer(String key, GameRole gameRole) {
        if (getPlayer(key) == null) {
            players.put(key, gameRole);
        }

        Log.wtf(getClass().getSimpleName(), "gameRole existed");
    }

    /**
     * Remove a player but not self
     *
     * @param key identity of the player
     */
    public void removePlayer(String key) {
        players.remove(key);
    }

    /**
     * Get a player object according to key (GameRole.SELF and GameRole.ARMY)
     *
     * @param key key
     * @return player object
     */
    public GameRole getPlayer(String key) {
        return players.get(key);
    }
}
