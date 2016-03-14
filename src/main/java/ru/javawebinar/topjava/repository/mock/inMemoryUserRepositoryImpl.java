package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by kubreg on 13.03.2016.
 */
public class inMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(MockUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        LOG.info("save " + user);

        if (user.isNew()) user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);

        repository.remove(id);
        return true;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);

        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        return repository.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(v -> email.equals(v.getEmail()))
                .findFirst()
                .get();
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");

        return repository.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().getName().compareTo(e2.getValue().getName()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
