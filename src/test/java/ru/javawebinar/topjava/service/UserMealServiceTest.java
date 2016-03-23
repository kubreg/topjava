package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

/**
 * Created by kubreg on 23.03.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(ID_1, USER_ID);
        MealTestData.MATCHER.assertEquals(U_MEAL_1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testForeignGet() throws Exception {
        service.get(1, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ID_1, USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(U_MEAL_2, U_MEAL_3), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testForeignDelete() throws Exception {
        service.delete(1, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MealTestData.MATCHER.assertCollectionEquals(Collections.singletonList(U_MEAL_1),
                service.getBetweenDates(LocalDate.of(2016,3,23), LocalDate.of(2016,3,23), USER_ID));
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MealTestData.MATCHER.assertCollectionEquals(Collections.singletonList(U_MEAL_2),
                service.getBetweenDateTimes(LocalDateTime.of(2016,3,24,2,5,30), LocalDateTime.of(2016,3,24,2,10,30), USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> list = service.getAll(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(U_MEAL_2, U_MEAL_3, U_MEAL_1), list);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(U_MEAL_1);
        updated.setDescription("BurgerUpd");
        service.update(updated, USER_ID);
        MealTestData.MATCHER.assertEquals(updated, service.get(ID_1, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testForeignUpdate() throws Exception {
        service.update(U_MEAL_1, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal meal = new UserMeal(LocalDateTime.of(2016,3,23,3,17,5), "Test", 1000);
        UserMeal created = service.save(meal, USER_ID);
        meal.setId(created.getId());
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(U_MEAL_2, U_MEAL_3, meal, U_MEAL_1), service.getAll(USER_ID));
    }
}