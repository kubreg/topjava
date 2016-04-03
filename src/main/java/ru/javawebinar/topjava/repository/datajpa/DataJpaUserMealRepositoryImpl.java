package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{
    private static final Sort SORT_DATETIME = new Sort(Sort.Direction.DESC, "dateTime");

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProxyUserMealRepository proxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        userMeal.setUser(em.getReference(User.class, userId));
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.findOne(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(SORT_DATETIME, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findBetween(SORT_DATETIME, startDate, endDate, userId);
    }
}
