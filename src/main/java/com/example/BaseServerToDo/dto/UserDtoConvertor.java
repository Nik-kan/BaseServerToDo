package com.example.BaseServerToDo.dto;

import com.example.BaseServerToDo.entity.UserEntity;
import lombok.NonNull;

public class UserDtoConvertor {

    public GetUserDto convertUserToDTO(@NonNull UserEntity userEntity) {
        GetUserDto getUserDto = new GetUserDto();
        getUserDto.setId(userEntity.getId());
        getUserDto.setName(userEntity.getName());
        getUserDto.setEmail(userEntity.getEmail());
        getUserDto.setPassword(userEntity.getPassword());
        return getUserDto;
    }
}
