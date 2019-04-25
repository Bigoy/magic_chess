package com.tssss.bysj.debug;

import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.other.Constant;

import java.util.ArrayList;
import java.util.List;

public class DebugRoles {

    public static List<Role> getDebugRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(
                "",
                "DEBUG",
                Constant.ROLE_SEX_MAN,
                "",
                "NO_LEVEL"
        ));
        return roleList;
    }
}
