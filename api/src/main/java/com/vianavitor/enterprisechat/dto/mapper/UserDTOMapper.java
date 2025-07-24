package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.user.CreatorDTO;
import com.vianavitor.enterprisechat.dto.user.UserCreateDTO;
import com.vianavitor.enterprisechat.dto.user.UserRespDTO;
import com.vianavitor.enterprisechat.dto.user.UserUpdateDTO;
import com.vianavitor.enterprisechat.model.User;

public class UserDTOMapper implements CustomMapper<User> {
    private static final UserDTOMapper instance = new UserDTOMapper();

    private UserDTOMapper() {}

    public static UserDTOMapper getInstance() {
        return instance;
    }

    @Override
    public User createDTOtoEntity(Record cDto) {
        UserCreateDTO dto = (UserCreateDTO) cDto;

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setAdmin(dto.admin());

        return user;
    }

    @Override
    public User updateDTOtoEntity(Record uDto) {
        UserUpdateDTO dto = (UserUpdateDTO) uDto;

        User user = new User();
        user.setId(dto.id());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setAdmin(dto.admin());

        return user;
    }

    @Override
    public UserRespDTO entityToRespDTO(User entity) {
        return new UserRespDTO(entity.getName(), entity.getName(), entity.isAdmin());
    }

    public CreatorDTO entityToCreatorDTO(User entity) {
        return new CreatorDTO(entity.getId(), entity.getName(), entity.isAdmin());
    }

}
