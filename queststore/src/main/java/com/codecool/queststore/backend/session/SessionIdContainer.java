package com.codecool.queststore.backend.session;

import java.util.HashMap;
import java.util.Map;

public class SessionIdContainer {

    private static SessionIdContainer instance;
    private Map<String, String> container;

    private SessionIdContainer() {
        container = new HashMap<>();
    }

    public static SessionIdContainer getSessionIdContainer() {
        if (instance == null) {
            instance = new SessionIdContainer();
        }
        return instance;
    }

    public Map<String, String> getContainer() {
        return container;
    }

    public String getUserId(String sessionId) {
        return container.get(sessionId);
    }

    public void add(String sessionId, String userLogin) {
        this.container.put(sessionId, userLogin);
    }

    public void remove(String sessionId) {
        this.container.remove(sessionId);
    }

    public boolean contains(String sessionId) {
        return container.containsKey(sessionId);
    }
}
