package tart.data.user;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import tart.domain.user.NewUser;
import tart.domain.user.User;
import tart.domain.user.UserRepository;

public class InMemoryUserRepository implements UserRepository {

    private static final Map USERS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewUser newUser) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, newUser.getLogin(), newUser.getPassword());
        USERS_STORE.put(newUser.getLogin(), user);

        return id;
    }
}
