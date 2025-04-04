package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.ReplaceUserDto;
import com.example.BaseServerToDo.dto.GetUserDto;
import com.example.BaseServerToDo.entity.UserEntity;
import com.example.BaseServerToDo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity replaceUser(ReplaceUserDto replaceUserDto){
        UserEntity userEntity = UserEntity.builder()
                .name(replaceUserDto.getName())
                .email(replaceUserDto.getEmail())
                .password(replaceUserDto.getPassword())
                .build();
        return userRepository.save(userEntity);
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public GetUserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        var getUserDto = convertToDTO(userEntity.get());
        return getUserDto;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public GetUserDto convertToDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userEntity.getId());
        getUserDto.setName(userEntity.getName());
        getUserDto.setEmail(userEntity.getEmail());
        getUserDto.setPassword(userEntity.getPassword());
        return getUserDto;
    }

    //    public UserEntity replaceUser(ReplaceUserDto replaceUserDto){
//    public void replaceUser(ReplaceUserDto replaceUserDto) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName(replaceUserDto.getName());
//        userEntity.setEmail(replaceUserDto.getEmail());
//        userEntity.setPassword(replaceUserDto.getPassword());
//        userRepository.save(userEntity);
//    }
}
