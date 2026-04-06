package com.jobintech.jobboardapi.services;

import com.jobintech.jobboardapi.dto.UserMapper;
import com.jobintech.jobboardapi.dto.UserRequestDTO;
import com.jobintech.jobboardapi.dto.UserResponseDTO;
import com.jobintech.jobboardapi.entities.User;
import com.jobintech.jobboardapi.enums.AccountType;
import com.jobintech.jobboardapi.exception.ConflictException;
import com.jobintech.jobboardapi.exception.NotFoundException;
import com.jobintech.jobboardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        return userMapper.toResponseDTO(findUserOrThrow(id));
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already in use: " + dto.getEmail());
        }
        User user = userMapper.toEntity(dto);
        return userMapper.toResponseDTO(userRepository.save(user));
    }
    
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User existing = findUserOrThrow(id);

        if (!existing.getEmail().equals(dto.getEmail())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already in use: " + dto.getEmail());
        }

        // MapStruct met à jour l'entity existant directement — pas de new User()
        userMapper.updateEntityFromDTO(dto, existing);

        return userMapper.toResponseDTO(userRepository.save(existing));
    }

    public void deleteUser(Long id) {
        userRepository.delete(findUserOrThrow(id));
    }

    public List<UserResponseDTO> getUsersByAccountType(AccountType accountType) {
        return userRepository.findByAccountType(accountType)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // méthode interne réutilisable
    public User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

}
