package com.alex.scancode.models.settings;

public class Server {
    private static int isServerConfigured;
    private static String config_ip;
    private static String config_port;
    private static String config_address;

    public Server() {
    }

    public static int getIsServerConfigured() {
        return isServerConfigured;
    }

    public static void setIsServerConfigured(int isServerConfigured) {
        Server.isServerConfigured = isServerConfigured;
    }

    public static String getConfig_ip() {
        return config_ip;
    }

    public static void setConfig_ip(String config_ip) {
        Server.config_ip = config_ip;
    }

    public static String getConfig_port() {
        return config_port;
    }

    public static void setConfig_port(String config_port) {
        Server.config_port = config_port;
    }

    public static String getConfig_address() {
        return config_address;
    }

    public static void setConfig_address(String config_address) {
        Server.config_address = config_address;
    }
}
