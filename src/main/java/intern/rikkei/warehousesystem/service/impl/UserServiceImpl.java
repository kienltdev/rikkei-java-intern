package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.enums.Role;
import intern.rikkei.warehousesystem.entity.User;
import intern.rikkei.warehousesystem.mapper.UserMapper;
import intern.rikkei.warehousesystem.repository.UserRepository;
import intern.rikkei.warehousesystem.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.dto.response.UserResponse;
import intern.rikkei.warehousesystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.username())){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userRepository.existsByEmail(registerRequest.email())){
            throw new IllegalArgumentException("Email already exists");
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
    public UserResponse updateProfile(String userName, UpdateProfileRequest updateProfileRequest) {

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

        String newEmail = updateProfileRequest.email();

        if(!user.getEmail().equalsIgnoreCase(newEmail)){
            if(userRepository.existsByEmail(updateProfileRequest.email())){
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(updateProfileRequest.email());
        }


        user.setFullName(updateProfileRequest.fullName());

        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public UserResponse getCurrentUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

        return userMapper.toUserResponse(user);
    }

}
