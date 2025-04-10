package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.ReplaceUserDto;
import com.example.BaseServerToDo.dto.GetUserDto;
import com.example.BaseServerToDo.dto.UserDtoConvertor;
import com.example.BaseServerToDo.entity.UserEntity;
import com.example.BaseServerToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConvertor userDtoConvector;

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

    public Page<GetUserDto> getPaginatedUsers(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<UserEntity> userPage = userRepository.findAll(pageable);
        List<GetUserDto> getUserDtoList = userPage.getContent()
                .stream()
                .map(userDtoConvector::convertUserToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(getUserDtoList, pageable, userPage.getTotalElements());
    }

    public GetUserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userDtoConvector.convertUserToDTO(userEntity.get());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
