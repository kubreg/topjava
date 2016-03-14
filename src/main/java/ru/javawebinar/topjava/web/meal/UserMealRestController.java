package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private int userId;

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

    public Collection<UserMealWithExceed> getFiltered(LocalDate from, LocalDate to) {
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(userId), from, to, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void setUserId(int id) {
        userId = id;
    }
}
