package com.rftdevgroup.transporthub.service.impl;

import com.rftdevgroup.transporthub.data.dto.UserCredentialDTO;
import com.rftdevgroup.transporthub.data.dto.UserDTO;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.data.repository.UserRepository;
import com.rftdevgroup.transporthub.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An implementation of the {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public <T> Optional<T> findAndMapUser(String username, Class<T> mapTo) {
        Optional<User> user = userRepository.findUserByUserName(username);
        return user.isPresent() ? Optional.of(modelMapper.map(user.get(), mapTo)) : Optional.empty();
    }

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
