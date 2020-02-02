package com.nesoy.springsecurity.account;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    MockMvc mockmvc;
    @Autowired
    private AccountService accountService;

    @Test
    public void index_anonymous() throws Exception {
        mockmvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithAnonymousUser
    public void index_anonymous2() throws Exception {
        mockmvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "nesoy", roles = "USER")
    public void index_user() throws Exception {
        mockmvc.perform(get("/").with(user("nesoy").password("123").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "nesoy", roles = "USER")
    public void index_user2() throws Exception {
        mockmvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void admin_user() throws Exception {
        mockmvc.perform(get("/admin").with(user("nesoy").password("123").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "nesoy", roles = "USER")
    public void admin_user2() throws Exception {
        mockmvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithUserTest
    public void admin_user3() throws Exception {
        mockmvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void admin_admin() throws Exception {
        mockmvc.perform(get("/admin").with(user("nesoy").password("123").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    public void login_success() throws Exception {
        Account account = new Account();
        account.setUsername("nesoy");
        account.setPassword("123");
        account.setRole("USER");
        accountService.createAccount(account);

        mockmvc.perform(formLogin().user("nesoy").password("123"))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_fail() throws Exception {
        Account account = new Account();
        account.setUsername("nesoy");
        account.setPassword("123");
        account.setRole("USER");
        accountService.createAccount(account);

        mockmvc.perform(formLogin().user("nesoy").password("1234"))
                .andExpect(unauthenticated());
    }

}

@Test
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "nesoy", roles = "USER")
@interface WithUserTest {

}