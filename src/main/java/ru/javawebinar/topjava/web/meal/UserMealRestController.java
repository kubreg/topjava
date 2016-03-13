package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private int userId = LoggedUser.id();

    @Autowired
    private UserMealService service;

    public UserMeal save(UserMeal userMeal) {
        return service.save(userMeal, userId);
    }

    public void delete(int id) {
        service.delete(id, userId);
    }

    public UserMeal get(int id) {
        return service.get(id, userId);
    }

    public Collection<UserMealWithExceed> getAll() {
        return UserMealsUtil.getWithExceeded(service.getAll(userId), UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
