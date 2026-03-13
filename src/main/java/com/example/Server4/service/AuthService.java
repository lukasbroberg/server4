package com.example.Server4.service;

import com.example.Server4.JwtService;
import com.example.Server4.dto.AuthResponse;
import com.example.Server4.dto.LoginRequest;
import com.example.Server4.dto.RegisterRequest;
import com.example.Server4.dto.UserResponse;
import com.example.Server4.entity.User;
import com.example.Server4.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username da ton tai");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email da ton tai");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return toUserResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        String usernameOrEmail = request.getUsername();

        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new BadCredentialsException("Sai tai khoan hoac mat khau"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Sai tai khoan hoac mat khau");
        }

        String token = jwtService.generateToken(user.getUsername(), "USER");

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(toUserResponse(user));

        return response;
    }

    public UserResponse getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Chua dang nhap");
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Khong tim thay user"));

        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
/*package com.example.Server4.service;

import com.example.Server4.JwtService;
import com.example.Server4.dto.AuthResponse;
import com.example.Server4.dto.LoginRequest;
import com.example.Server4.dto.RegisterRequest;
import com.example.Server4.dto.UserResponse;
import com.example.Server4.entity.User;
import com.example.Server4.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class import React, { useEffect } from "react";
        import { Text, TouchableOpacity, View } from "react-native";
        import { authService } from "../services/authService";
        import { storageService } from "../services/storageService";
        import { userService } from "../services/userService";

export default function HomeScreen() {
    useEffect(() => {
    const clearToken = async () => {
            await storageService.removeToken();
    console.log("DA XOA TOKEN CU");
    };

    clearToken();
  }, []);

  const testRegister = async () => {
            console.log("BAT DAU REGISTER");

    try {
      const result = await authService.register({
                username: "mari_new_1",
                email: "mari_new_1@gmail.com",
                password: "123456",
      });

        console.log("REGISTER OK:", result);
        alert("Register thanh cong");
    } catch (error: any) {
        console.log("REGISTER ERROR FULL:", error);
        console.log("REGISTER ERROR MESSAGE:", error?.message);
        console.log("REGISTER ERROR RESPONSE:", error?.response?.data);
        alert(
                typeof error?.response?.data === "string"
                ? error.response.data
                : JSON.stringify(
                error?.response?.data || error?.message || "Register that bai"
            )
      );
    }
  };

  const testLogin = async () => {
    try {
      const result = await authService.login({
                username: "mari_new_1",
                password: "123456",
      });

        console.log("LOGIN OK:", result);
        alert("Login thanh cong");
    } catch (error: any) {
        console.log(
                "LOGIN ERROR:",
                error?.response?.data || error?.message || error
      );
        alert("Login that bai");
    }
  };

  const testGetToken = async () => {
    try {
      const token = await storageService.getToken();
        console.log("TOKEN DA LUU:", token);
        alert(token ? "Da co token" : "Chua co token");
    } catch (error) {
        console.log("GET TOKEN ERROR:", error);
    }
  };

  const testGetMe = async () => {
    try {
      const result = await userService.getMe();
        console.log("GET ME OK:", result);
        alert(`Xin chao ${result.username}`);
    } catch (error: any) {
        console.log(
                "GET ME ERROR:",
                error?.response?.data || error?.message || error
      );
        alert("Lay /auth/me that bai");
    }
  };

  const testRawConnection = async () => {
    try {
        console.log("TEST RAW CONNECTION...");

      const response = await fetch("http://10.24.73.206:8080/auth/me");

        console.log("RAW STATUS:", response.status);

      const text = await response.text();
        console.log("RAW BODY:", text);

        alert(`Status: ${response.status}`);
    } catch (error: any) {
        console.log("RAW FETCH ERROR:", error);
        alert(`Fetch error: ${error?.message || error}`);
    }
  };

    return (
            <View
    style={{
            flex: 1,
            justifyContent: "center",
            alignItems: "center",
            padding: 20,
      }}
    >
      <Text style={{ fontSize: 22, marginBottom: 20 }}>Test authService</Text>

            <TouchableOpacity
    onPress={testRegister}
    style={{
            backgroundColor: "green",
            paddingVertical: 12,
            paddingHorizontal: 20,
            borderRadius: 8,
            marginBottom: 15,
            width: 220,
            alignItems: "center",
        }}
      >
        <Text style={{ color: "white", fontWeight: "bold" }}>
    Test Register
        </Text>
            </TouchableOpacity>

            <TouchableOpacity
    onPress={testLogin}
    style={{
            backgroundColor: "blue",
            paddingVertical: 12,
            paddingHorizontal: 20,
            borderRadius: 8,
            width: 220,
            alignItems: "center",
            marginTop: 15,
        }}
      >
        <Text style={{ color: "white", fontWeight: "bold" }}>Test Login</Text>
            </TouchableOpacity>

            <TouchableOpacity
    onPress={testGetToken}
    style={{
            backgroundColor: "orange",
            paddingVertical: 12,
            paddingHorizontal: 20,
            borderRadius: 8,
            width: 220,
            alignItems: "center",
            marginTop: 15,
        }}
      >
        <Text style={{ color: "white", fontWeight: "bold" }}>
    Test Get Token
            </Text>
            </TouchableOpacity>

            <TouchableOpacity
    onPress={testGetMe}
    style={{
            backgroundColor: "purple",
            paddingVertical: 12,
            paddingHorizontal: 20,
            borderRadius: 8,
            width: 220,
            alignItems: "center",
            marginTop: 15,
        }}
      >
        <Text style={{ color: "white", fontWeight: "bold" }}>
    Test Get Me
            </Text>
            </TouchableOpacity>

            <TouchableOpacity
    onPress={testRawConnection}
    style={{
            backgroundColor: "red",
            paddingVertical: 12,
            paddingHorizontal: 20,
            borderRadius: 8,
            width: 220,
            alignItems: "center",
            marginTop: 15,
        }}
      >
        <Text style={{ color: "white", fontWeight: "bold" }}>
    Test Raw Connection
            </Text>
            </TouchableOpacity>
            </View>
  );
}AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username da ton tai");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email da ton tai");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        return toUserResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        String usernameOrEmail = request.getUsername();

        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new BadCredentialsException("Sai tai khoan hoac mat khau"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Sai tai khoan hoac mat khau");
        }

        String token = jwtService.generateToken(user.getUsername(), "USER");

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUser(toUserResponse(user));

        return response;
    }

    public UserResponse getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Chua dang nhap");
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Khong tim thay user"));

        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}*/
