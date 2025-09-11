package tart.domain.user;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository ur) {
        userRepository = ur;
    }

    public String create(NewUser user) {
        return userRepository.create(user);
    }

}
