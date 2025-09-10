package intern.rikkei.warehousesystem.security;

import intern.rikkei.warehousesystem.modules.auth.entity.User;
import intern.rikkei.warehousesystem.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +  user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassWord(),
                Collections.singletonList(authority)
        );
    }


}
