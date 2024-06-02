package kz.bitlab.taskmanagement.mapper;

import kz.bitlab.taskmanagement.dto.RegisterDTO;
import kz.bitlab.taskmanagement.dto.UserDTO;
import kz.bitlab.taskmanagement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterDTO registerDTO);

    UserDTO toDTO(User user);
}
