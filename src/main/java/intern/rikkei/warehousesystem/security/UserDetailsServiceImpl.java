package intern.rikkei.warehousesystem.security;

import intern.rikkei.warehousesystem.entity.User;
import intern.rikkei.warehousesystem.repository.UserRepository;
import intern.rikkei.warehousesystem.service.UserCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserCacheService userCacheService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userCacheService.findUserByUsername(username);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +  user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }


}
