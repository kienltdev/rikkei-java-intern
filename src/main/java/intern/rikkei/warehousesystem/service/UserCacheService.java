package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.entity.User;
import intern.rikkei.warehousesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Cacheable("users")
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.user.notFound",
                            new Object[]{username},
                            LocaleContextHolder.getLocale());
                    return new UsernameNotFoundException(message);
                });
    }
}