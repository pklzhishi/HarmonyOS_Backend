package org.example.HarmonyOS_backend.tool;

public class UserHolder {
    private static final ThreadLocal<Integer> userId = new ThreadLocal<>();

    public static void setUserId(int id) { userId.set(id); }
    public static Integer getUserId() { return userId.get(); }
    public static void remove() { userId.remove(); }
}