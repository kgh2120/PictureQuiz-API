package com.kk.picturequizapi.domain.users.service;

import com.kk.picturequizapi.domain.users.dto.MyInfoResponseDto;
import com.kk.picturequizapi.domain.users.dto.SignUpResponseDto;
import com.kk.picturequizapi.domain.users.dto.TokenResponseDto;
import com.kk.picturequizapi.domain.users.dto.UserAccessRequestDto;
import com.kk.picturequizapi.domain.users.entity.Users;
import com.kk.picturequizapi.domain.users.exception.LoginDataNotFoundException;
import com.kk.picturequizapi.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service @Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public SignUpResponseDto signUp(UserAccessRequestDto dto) {

        String encodedPassword = encoder.encode(dto.getPassword());
        Users users = Users.createUserEntity(dto.getLoginId(), encodedPassword);

        Users save = userRepository.save(users);
        return new SignUpResponseDto(save.getId());
    }

    @Override
    public TokenResponseDto login(UserAccessRequestDto dto) {
        Users users = userRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(LoginDataNotFoundException::new);

        if(!encoder.matches(dto.getPassword(),users.getPassword()))
            throw new LoginDataNotFoundException();

        return new TokenResponseDto();
    }

    @Override
    public MyInfoResponseDto readMyInfo() {
        return MyInfoResponseDto.createDto(getUser());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .orElseThrow();
    }

    private Users getUser() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                ;
    }
}
