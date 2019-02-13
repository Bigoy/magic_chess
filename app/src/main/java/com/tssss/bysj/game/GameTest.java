package com.tssss.bysj.game;

import com.tssss.bysj.user.role.GameRole;

public class GameTest {
    private GameRole self;
    private GameRole rival;

    public GameTest() {
        self = new GameRole();
        rival = new GameRole();

        initRoles();
    }

    private void initRoles() {
        self.setRoleName("陶帅");
        self.setRoleLevel(GameRole.ROLE_LEVEL_SENIOR);
        self.setRoleSex(GameRole.ROLE_SEX_BOY);
        self.setRoleExperience(0);
        self.setRoleState(GameRole.ROLE_STATE_ONLINE);

        rival.setRoleName("陶很帅");
        rival.setRoleLevel(GameRole.ROLE_LEVEL_JUNIOR);
        rival.setRoleSex(GameRole.ROLE_SEX_BOY);
        rival.setRoleExperience(50);
        rival.setRoleState(GameRole.ROLE_STATE_ONLINE);
    }

    public GameRole getSelf() {
        return this.self;
    }

    public GameRole getRival() {
        return this.rival;
    }
}
