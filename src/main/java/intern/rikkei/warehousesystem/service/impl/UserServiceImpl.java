package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.constant.ErrorCodes;
import intern.rikkei.warehousesystem.enums.Role;
import intern.rikkei.warehousesystem.entity.User;
import intern.rikkei.warehousesystem.exception.DuplicateResourceException;
import intern.rikkei.warehousesystem.exception.ResourceNotFoundException;
import intern.rikkei.warehousesystem.mapper.UserMapper;
import intern.rikkei.warehousesystem.repository.UserRepository;
import intern.rikkei.warehousesystem.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.dto.response.UserResponse;
import intern.rikkei.warehousesystem.service.UserCacheService;
import intern.rikkei.warehousesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final UserCacheService userCacheService;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        Locale locale = LocaleContextHolder.getLocale();
        if(userRepository.existsByUsername(registerRequest.username())){
            String message = messageSource.getMessage("error.username.exists",
                    new Object[]{registerRequest.username()}, locale);
            throw new DuplicateResourceException(ErrorCodes.USERNAME_ALREADY_EXISTS, message);
        }

        if(userRepository.existsByEmail(registerRequest.email())){
            String message = messageSource.getMessage("error.email.exists",
                    new Object[]{registerRequest.email()}, locale);
            throw new DuplicateResourceException(ErrorCodes.EMAIL_ALREADY_EXISTS, message);
        }

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setFullName(registerRequest.fullName());
        user.setEmail(registerRequest.email());
        user.setRole(Role.STAFF);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "users", key = "#username")
    public UserResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest) {
        userCacheService.evictUserFromCache(username);

        Locale locale = LocaleContextHolder.getLocale();
        String userNotFoundMessage = messageSource.getMessage("error.user.notFound", new Object[]{username}, locale);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->  new ResourceNotFoundException(ErrorCodes.USER_NOT_FOUND, userNotFoundMessage));

        String newEmail = updateProfileRequest.email();

        if(StringUtils.hasText(newEmail) && !user.getEmail().equalsIgnoreCase(newEmail)){
            if(userRepository.existsByEmail(updateProfileRequest.email())){
                String emailExistsMessage = messageSource.getMessage("error.email.exists", new Object[]{newEmail}, locale);
                throw new DuplicateResourceException(ErrorCodes.EMAIL_ALREADY_EXISTS, emailExistsMessage);
            }
            user.setEmail(updateProfileRequest.email());
        }


        String newFullName = updateProfileRequest.fullName();
        if (StringUtils.hasText(newFullName)) {
            user.setFullName(newFullName);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public UserResponse getCurrentUser(String userName) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.user.notFound", new Object[]{userName}, locale);
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.USER_NOT_FOUND, message));

        return userMapper.toUserResponse(user);
    }

}
