package com.spring.jwt.service.impl;

import com.spring.jwt.dto.UserDTO;
import com.spring.jwt.dto.UserUpdateRequest;
import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.exception.EmailNotVerifiedException;
import com.spring.jwt.exception.UserNotFoundExceptions;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.UserService;
import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.EmailService;
import com.spring.jwt.utils.EmailVerificationService.EmailVerification;
import com.spring.jwt.utils.EmailVerificationService.EmailVerificationRepo;
import com.spring.jwt.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailVerificationRepo emailVerificationRepo;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Override
    public BaseResponseDTO registerAccount(UserDTO userDTO) {
        BaseResponseDTO response = new BaseResponseDTO();

        validateAccount(userDTO);

        User user = insertUser(userDTO);

        try {
            userRepository.save(user);
            response.setCode(String.valueOf(HttpStatus.OK.value()));
            response.setMessage("Account Created Successfully !!");
        } catch (Exception e) {
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service unavailable");
        }
        return response;
    }

    private User insertUser(UserDTO userDTO) {
        Optional<EmailVerification> emailVerificationOpt = emailVerificationRepo.findByEmail(userDTO.getEmail());

        if (emailVerificationOpt.isEmpty() ||
                EmailVerification.STATUS_NOT_VERIFIED.equals(emailVerificationOpt.get().getStatus())) {
            throw new EmailNotVerifiedException("Email not verified");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setAddress(userDTO.getAddress());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(userDTO.getRole()));
        user.setRoles(roles);
        return user;
    }

    private void validateAccount(UserDTO userDTO) {
        if (ObjectUtils.isEmpty(userDTO)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Data must not empty");
        }

        User user = userRepository.findByEmail(userDTO.getEmail());
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Email is already registered !!");
        }

        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        if (!roles.contains(userDTO.getRole())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }
        Optional<User> mobileNumber= userRepository.findByMobileNumber(userDTO.getMobileNumber());
        if(!ObjectUtils.isEmpty(mobileNumber)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Mobile Number is already registered !!");
        }
    }

        public ResponseDto forgotPass(String email, String resetPasswordLink, String domain) {
            User user = userRepository.findByEmail(email);
            if (user == null) throw new UserNotFoundExceptions("User not found");

            emailService.sendResetPasswordEmail(email, resetPasswordLink, domain);

            return new ResponseDto(HttpStatus.OK.toString(), "Email sent");
        }

    public void updateResetPassword(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UserNotFoundExceptions("User not found");

        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);
    }

    public ResponseDto updatePassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user == null || user.getResetPasswordTokenExpiry() == null || LocalDateTime.now().isAfter(user.getResetPasswordTokenExpiry())) {
            throw new UserNotFoundExceptions("Invalid or expired token");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);

        return new ResponseDto(HttpStatus.OK.toString(), "Password reset successful");
    }


    public boolean validateResetToken(String token) {
        User user = userRepository.findByResetPasswordToken(token);
        if (user == null) return false;

        return user.getResetPasswordTokenExpiry() != null && LocalDateTime.now().isBefore(user.getResetPasswordTokenExpiry());
    }


    public boolean isSameAsOldPassword(String token, String newPassword) {
            User user = userRepository.findByResetPasswordToken(token);
            if (user == null) throw new UserNotFoundExceptions("Invalid or expired token");

            return passwordEncoder.matches(newPassword, user.getPassword());
        }


    @Override
    public Page<UserDTO> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAllWithRoles(pageable);

        if (userPage.isEmpty()) throw new UserNotFoundExceptions("User not found", HttpStatus.NOT_FOUND);

        return userPage.map(UserDTO::new);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new UserNotFoundExceptions("User not found"));
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExceptions("User not found"));

        if (updateRequest.getFirstName() != null) user.setFirstName(updateRequest.getFirstName());
        if (updateRequest.getLastName() != null) user.setLastName(updateRequest.getLastName());
        if (updateRequest.getEmail() != null) user.setEmail(updateRequest.getEmail());
        if (updateRequest.getAddress() != null) user.setAddress(updateRequest.getAddress());
        if (updateRequest.getMobileNumber() != null) user.setMobileNumber(updateRequest.getMobileNumber());

        userRepository.save(user);

        return new UserDTO(user);
    }

}
