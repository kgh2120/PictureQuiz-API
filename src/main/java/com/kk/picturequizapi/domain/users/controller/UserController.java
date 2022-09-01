package com.kk.picturequizapi.domain.users.controller;

import com.kk.picturequizapi.domain.users.dto.MyInfoResponseDto;
import com.kk.picturequizapi.domain.users.dto.SignUpResponseDto;
import com.kk.picturequizapi.domain.users.dto.UserAccessRequestDto;
import com.kk.picturequizapi.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UserAccessRequestDto dto) {
        SignUpResponseDto responseDto = userService.signUp(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<MyInfoResponseDto> readMyInfo() {
        return ResponseEntity.ok(userService.readMyInfo());
    }
}
