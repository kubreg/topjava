package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    private static int id;

    public static int id() {
        return id;
    }

    public int getId() {
        return id;
    }

    public static int getCaloriesPerDay() {
        return UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static void setId(int b) {
        id = b;
    }
}
