package com.company.converter;

import com.company.dto.UserDTO;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class UserDTOConverter implements Converter<String, UserDTO> {

    private final UserService userService;

    public UserDTOConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO convert(String userName) {
        return userService.findById(userName);
    }
}
