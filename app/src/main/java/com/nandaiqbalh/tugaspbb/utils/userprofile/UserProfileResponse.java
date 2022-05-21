package com.nandaiqbalh.tugaspbb.utils.userprofile;

import com.nandaiqbalh.tugaspbb.model.User;

public class UserProfileResponse {
    private int success = 0;
    private String message;
    private User user = new User();

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
