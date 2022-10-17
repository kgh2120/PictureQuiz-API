package com.kk.picturequizapi.domain.quiz.query.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.picturequizapi.domain.quiz.query.dao.QuizSearchDao;
import com.kk.picturequizapi.domain.quiz.query.dto.QuizSearchCondition;
import com.kk.picturequizapi.domain.quiz.query.dto.QuizSearchResponse;
import com.kk.picturequizapi.global.config.SecurityConfig;
import com.kk.picturequizapi.global.security.JwtAuthenticationFilter;
import com.kk.picturequizapi.global.security.JwtAuthorizationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = {QuizSearchController.class, },
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}

)
class QuizSearchControllerTest {

    @MockBean
    QuizSearchDao dao;

    @Autowired
    MockMvc mockMvc;
    
    @Test @WithMockUser(username = "test")
    void search_anything () throws Exception{
        //given
        given(dao.searchQuizByCondition(any(QuizSearchCondition.class),anyInt()))
                .willReturn(new QuizSearchResponse(null,1,true));

        QuizSearchCondition cond = new QuizSearchCondition();
        cond.setPageNum(0);
        ObjectMapper mapper = new ObjectMapper();

        //when  //then
        mockMvc.perform(post("/quiz")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjY2MDAzMzU0LCJleHAiOjE2NjYwODk3NTR9.isUcKk0LPBUnQ2UbUYenf5gnkvz3v0VyLd0Kb88NcYI")
                .content(mapper.writeValueAsString(cond)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nextPageNum").value(1))
                .andExpect(jsonPath("$.hasNext").value(true))
                .andDo(print());

        
    
    }


}