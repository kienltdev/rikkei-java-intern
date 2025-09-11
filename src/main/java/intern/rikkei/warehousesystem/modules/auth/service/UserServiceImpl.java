package intern.rikkei.warehousesystem.modules.auth.service;

import intern.rikkei.warehousesystem.common.enums.Role;
import intern.rikkei.warehousesystem.modules.auth.entity.User;
import intern.rikkei.warehousesystem.modules.auth.mapper.UserMapper;
import intern.rikkei.warehousesystem.modules.auth.repository.UserRepository;
import intern.rikkei.warehousesystem.modules.auth.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.response.UserResponse;
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
        if(userRepository.existsByUserName(registerRequest.userName())){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userRepository.existsByEmail(registerRequest.email())){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUserName(registerRequest.userName());
        user.setEmail(registerRequest.email());
        user.setPassWord(passwordEncoder.encode(registerRequest.passWord()));
        user.setFullName(registerRequest.fullName());
        user.setEmail(registerRequest.email());
        user.setRole(Role.STAFF);

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(String userName, UpdateProfileRequest updateProfileRequest) {

        User user = userRepository.findByUserName(userName)
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
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

        return userMapper.toUserResponse(user);
    }

}
