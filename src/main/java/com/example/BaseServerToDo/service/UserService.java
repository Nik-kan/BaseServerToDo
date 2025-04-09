package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.ReplaceUserDto;
import com.example.BaseServerToDo.dto.GetUserDto;
import com.example.BaseServerToDo.dto.UserDtoConvector;
import com.example.BaseServerToDo.entity.UserEntity;
import com.example.BaseServerToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConvector userDtoConvector;

// это мне для наглядности
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public UserEntity replaceUser(ReplaceUserDto replaceUserDto) {
        UserEntity userEntity = UserEntity.builder()
                .name(replaceUserDto.getName())
                .email(replaceUserDto.getEmail())
                .password(replaceUserDto.getPassword())
                .build();
        return userRepository.save(userEntity);
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDtoConvector::convertUserToDTO)
                .collect(Collectors.toList());
    }

    public GetUserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userDtoConvector.convertUserToDTO(userEntity.get());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
