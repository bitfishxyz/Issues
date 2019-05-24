package com.github.fish56.issues.controller;

import com.github.fish56.issues.IssuesApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

public class ReposControllerTest extends IssuesApplicationTests {

    @Test
    public void getIssues() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        String url = "/repos/vuejs/vue/issues";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }
}