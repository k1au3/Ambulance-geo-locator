package com.dmrot.dominiccue.amtechtimesapp.model;

/**
 * Created by dominiccue on 10/7/2017.
 */

public class ServerRequest {
    private String operation;
    private User user;

    public ServerRequest(String operation, User user) {
        this.operation = operation;
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
