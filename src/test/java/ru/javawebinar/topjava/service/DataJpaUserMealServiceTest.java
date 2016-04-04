package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by kubreg on 04.04.2016.
 */
@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserMealServiceTest extends UserMealServiceTest {
    @Test
    public void getWithUser() {
        UserMeal um = service.getWithUser(MEAL1_ID, USER_ID);
        UserTestData.MATCHER.assertEquals(um.getUser(), USER);
    }
}
