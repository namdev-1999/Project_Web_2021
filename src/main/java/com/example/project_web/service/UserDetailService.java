package com.example.project_web.service;

import com.example.project_web.entity.User;
import com.example.project_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserPrincipal(user);
    }

    // Tìm user theo email
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    // Tìm user theo id
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    // Lấy danh sách toàn bộ user
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Xóa user theo id
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    // Chỉnh sửa thông tin user
    public void update(User user) {
        User u = userRepository.findUserById(user.getId());
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setDob(user.getDob());
        u.setPhone(user.getPhone());
        u.setRole(user.getRole());
        u.setStatus(user.getStatus());
        userRepository.save(u);
    }

    //Thêm user mới
    public User addUser(User user) {
        User u = new User();
        u.setAvatar(user.getAvatar());
        u.setEmail(user.getEmail());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setName(user.getName());
        u.setDob(user.getDob());
        u.setPhone(user.getPhone());
        u.setRole(user.getRole());
        u.setStatus(user.getStatus());
        return userRepository.save(u);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    // Lấy token to rết password
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    // Update password
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    // Đăng ký
    public boolean register(User user) {
        if (checkUserExist(user.getEmail())) return false;
        User u = new User();
        u.setAvatar(user.getAvatar());
        u.setEmail(user.getEmail());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setName(user.getName());
        u.setDob(user.getDob());
        u.setPhone(user.getPhone());
        u.setRole("USER");
        u.setStatus(user.getStatus());
        userRepository.save(u);
        return true;
    }

    // Kiểm tra email đã tồn tại hay chưa
    public boolean checkUserExist(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }
}
