package tart.domain.file;

import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

public class TestUserService extends UserService{
    
    public TestUserService(UserRepository userRepository) {
        super(userRepository);
    }
    
}
