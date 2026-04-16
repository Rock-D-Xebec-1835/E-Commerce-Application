package com.cartapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cartapplication.dto.UserRequestDTO;
import com.cartapplication.dto.UserResponseDTO;
import com.cartapplication.entity.User;
import com.cartapplication.repository.UserRepository;
import com.cartapplication.exception.ResourceNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
    	User user=new User();
    	user.setFirstName(dto.getFirstName());
    	user.setLastName(dto.getLastName());
    	user.setEmail(dto.getEmail());
    	user.setPhone(dto.getPhone());
    	user.setRole(dto.getRole());
        User saved = userRepository.save(user);
        return toResponseDTO(saved);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList() );
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        User updated = userRepository.save(user);
        return toResponseDTO(updated);
    }
    
    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
}


