//package payMyServer.QuickTip.Services;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import payMyServer.QuickTip.Entity.User;
//import payMyServer.QuickTip.Respository.UserRepository;
//
//import java.util.logging.Logger;
//
//@Slf4j
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.debug("Loading user: {}", username);
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRoles())
//                .build();
//    }
//}