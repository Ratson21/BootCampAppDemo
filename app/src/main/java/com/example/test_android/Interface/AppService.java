package com.example.test_android.Interface;


import com.example.test_android.model.User;

public class AppService {
    private static String token;
    private static User user;

    public static String getToken(){return token;}
    public static void setToken(String token) { AppService.token = token;}
    public static User getUser(){return user;}
    public static void setUser(User user) { AppService.user = user;}
}
