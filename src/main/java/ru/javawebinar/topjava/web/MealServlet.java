package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.InMemoryUserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private static InMemoryUserMeal repository = new InMemoryUserMeal();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealList", UserMealsUtil.getFilteredMealsWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));

            repository.delete(id);
            response.sendRedirect("meals");
        } else {
            int id = Integer.valueOf(request.getParameter("id"));

            request.setAttribute("meal", repository.get(id));
            request.getRequestDispatcher("editMeal.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        UserMeal userMeal = new UserMeal(Integer.valueOf(request.getParameter("id")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        repository.save(userMeal);
        response.sendRedirect("meals");
    }
}
