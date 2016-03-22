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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
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
        UserMeal userMeal = service.get(USER_MEAL_ID, USER_ID);
        MealTestData.MATCHER.assertEquals(USER_MEAL, userMeal);
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
        service.delete(USER_MEAL_ID, USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(new ArrayList<>(), service.getAll(USER_ID));
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

    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> list = service.getAll(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL), list);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(USER_MEAL);
        updated.setDescription("BurgerUpd");
        service.update(updated, USER_ID);
        MealTestData.MATCHER.assertEquals(updated, service.get(USER_MEAL_ID, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testForeignUpdate() throws Exception {
        service.update(USER_MEAL, ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        UserMeal meal = new UserMeal(LocalDateTime.now(), "Test", 1000);
        UserMeal created = service.save(meal, USER_ID);
        meal.setId(created.getId());
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL, meal), service.getAll(USER_ID));
    }
}