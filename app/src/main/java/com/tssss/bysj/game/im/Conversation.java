package com.tssss.bysj.game.im;

import com.tssss.bysj.game.core.Role;

public class Conversation {
    private Role role;

    private String lastChatHistory;

    public Conversation() {

    }



    public Conversation(Role role, String lastChatHistory) {
        this.role = role;
        this.lastChatHistory = lastChatHistory;

    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLastChatHistory() {
        return lastChatHistory;
    }

    public void setLastChatHistory(String lastChatHistory) {
        this.lastChatHistory = lastChatHistory;
    }
}
