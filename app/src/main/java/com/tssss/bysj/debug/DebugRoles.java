package com.tssss.bysj.debug;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.other.Constant;

import java.util.ArrayList;
import java.util.List;

public class DebugRoles {

    public static List<GameRole> getDebugRoles() {
        List<GameRole> gameRoleList = new ArrayList<>();
        gameRoleList.add(new GameRole(
                "",
                "DEBUG",
                Constant.ROLE_SEX_MAN,
                "",
                "NO_LEVEL"
        ));
        return gameRoleList;
    }
}
