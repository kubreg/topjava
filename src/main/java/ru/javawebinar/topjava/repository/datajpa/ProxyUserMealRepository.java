package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kubreg on 03.04.2016.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId")
    List<UserMeal> findAll(Sort sort, @Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    UserMeal findOne(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m "+
            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate")
    List<UserMeal>  findBetween(Sort sort, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
    UserMeal getWithUser(@Param("id") int id, @Param("userId") int userId);

    UserMeal findByIdAndUser(int id, User user);

    List<UserMeal> findByUserOrderByDateTimeDesc(User user);

    List<UserMeal> findByUserAndDateTimeBetweenOrderByDateTimeDesc(User user, LocalDateTime startDate, LocalDateTime endDate);
}
