package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int ID_1 = START_SEQ + 2;
    public static final int ID_2 = START_SEQ + 3;
    public static final int ID_3 = START_SEQ + 4;

    public static final int A_ID_1 = START_SEQ + 5;
    public static final int A_ID_2 = START_SEQ + 6;
    public static final int A_ID_3 = START_SEQ + 7;


    public static final UserMeal U_MEAL_1 = new UserMeal(ID_1, LocalDateTime.of(2016,3,23,2,7,31), "Burger", 500);
    public static final UserMeal U_MEAL_2 = new UserMeal(ID_2, LocalDateTime.of(2016,3,24,2,7,31), "Pizza", 1500);
    public static final UserMeal U_MEAL_3 = new UserMeal(ID_3, LocalDateTime.of(2016,3,24,2,3,31), "Pie", 1500);

    public static final UserMeal A_MEAL_1 = new UserMeal(A_ID_1, LocalDateTime.of(2016,3,23,2,7,31), "Pie", 1300);
    public static final UserMeal A_MEAL_2 = new UserMeal(A_ID_2, LocalDateTime.of(2016,3,23,2,7,31), "Pizza", 1500);
    public static final UserMeal A_MEAL_3 = new UserMeal(A_ID_3, LocalDateTime.of(2016,3,24,2,7,31), "Cola", 1300);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

}
