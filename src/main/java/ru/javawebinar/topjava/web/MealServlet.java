package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private UserMealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        AutowireCapableBeanFactory beanFactory = appCtx.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        controller.save(userMeal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");

        if (userId != null && !userId.isEmpty()) {
            LoggedUser.setId(Integer.parseInt(userId));
            controller.setUserId(LoggedUser.id());
        }

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", getList(request));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private Collection<UserMealWithExceed> getList(HttpServletRequest request) {
        String temp = request.getParameter("fromDate");
        LocalDate fromDate = temp != null ? (temp.isEmpty() ? LocalDate.MIN : LocalDate.parse(temp)) : null;
        temp = request.getParameter("toDate");
        LocalDate toDate = temp != null ? (temp.isEmpty() ? LocalDate.MAX : LocalDate.parse(temp)) : null;
        temp = request.getParameter("fromTime");
        LocalTime fromTime = temp != null ? (temp.isEmpty() ? LocalTime.MIN : LocalTime.parse(temp)) : null;
        temp = request.getParameter("toTime");
        LocalTime toTime = temp != null ? (temp.isEmpty() ? LocalTime.MAX : LocalTime.parse(temp)) : null;

        if (fromDate == null && toDate == null && fromTime == null && toTime == null) {
            return controller.getAll();
        } else if (fromTime == null && toTime == null) {
            return controller.getFiltered(fromDate, toDate);
        } else if (fromDate == null && toDate == null) {
            return controller.getFiltered(fromTime, toTime);
        } else {
            return controller.getFiltered(fromDate, toDate, fromTime, toTime);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
