package com.kpi.events.utils;

import com.kpi.events.model.User;

class Client {
    public static void main(String[] args) {
        User user = new User();
        user.setId(60);
        user.setFirstName(" ");
//        user.setLogin(" ");
        if (!ValidationManager.isValidate(user)) {
            System.out.println(ValidationManager.getFirsErrorMessage(user));
        } else System.out.println("okay");
    }
}
