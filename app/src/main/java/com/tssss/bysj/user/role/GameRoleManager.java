/*
package com.tssss.bysj.user.role;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

*/
/*
  Manage the roles that will be used on the current client, usually only two roles
  GameRole self and GameRole army

  @author Tssss
 * @date updated January 7, 2019
 *//*

public class GameRoleManager {
    private static GameRoleManager gameRoleManager;

    public static String SELF = "self";
    public static String RIVAL = "rival";

    private Map<String, GameRole> roles;

    private GameRoleManager() {
        roles = new HashMap<>();
    }

    public static GameRoleManager getGameRoleManager() {
        if (gameRoleManager == null) {
            gameRoleManager = new GameRoleManager();
        }
        return gameRoleManager;
    }

    */
/*
  Add a gameRole

  @param key      identity of the gameRole
 * @param gameRole instance of GameRole
 *//*

    public void addRole(String key, GameRole gameRole) {
        if (getRole(key) == null) {
            roles.put(key, gameRole);
            Log.wtf(getClass().getSimpleName(), JSON.toJSONString(gameRole));
        }
    }

    */
/*
  Remove a role but not self

  @param key identity of the player
 *//*

    public void removeRole(String key) {
        roles.remove(key);

        if (roles.get(key) == null)
            Log.wtf(getClass().getSimpleName(), key + " removed");
    }

    */
/*
  Get a role object according to key (GameRole.SELF and GameRole.RIVAL)

  @param key key
 * @return role object
 *//*

    public GameRole getRole(String key) {
        return roles.get(key);
    }
}
*/
