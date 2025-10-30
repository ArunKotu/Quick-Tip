package payMyServer.QuickTip.Services;
import org.springframework.stereotype.Service;
import payMyServer.QuickTip.Entity.User;
import payMyServer.QuickTip.Respository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register user
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
