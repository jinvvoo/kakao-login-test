//package com.alal.backend.controller.auth;
//
//
//import com.alal.backend.lib.JsonUtils;
//import com.alal.backend.payload.request.auth.ChangePasswordRequest;
//import com.alal.backend.payload.request.auth.SignInRequest;
//import com.alal.backend.payload.request.auth.SignUpRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.json.simple.JSONObject;
//import org.junit.Assert;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
////@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles(profiles = "local")
//public class AuthControllerTest {
//
//    private static final Logger log = LoggerFactory.getLogger(AuthControllerTest.class);
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void init() throws Exception {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                                    .addFilter(new CharacterEncodingFilter("UTF-8", true))
//                                    .build();
//
//        signup("string@aa.bb");
//    }
//
//    @AfterEach
//    public void cleanup() throws Exception {
//        remove("string@aa.bb");
//    }
//
//    private void signup(String email) throws Exception{
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail(email);
//        signUpRequest.setPassword("string");
//        signUpRequest.setName("string");
//
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/signup")
//                    .content(
//                        JsonUtils.asJsonToString(signUpRequest)
//                    )
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().is2xxSuccessful())
//                .andDo(print());
//
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//        Assert.assertEquals(jsonObject.get("check"), true);
//
//    }
//
//    private JSONObject signin(String email) throws Exception{
//
//        SignInRequest signInRequest = new SignInRequest();
//        signInRequest.setEmail(email);
//        signInRequest.setPassword("string");
//
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/signin")
//                    .content(
//                        JsonUtils.asJsonToString(signInRequest)
//                    )
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        return jsonObject;
//    }
//
//    private void remove(String email) throws Exception{
//
//        JSONObject token = signin(email);
//        String accessToken = (String) token.get("accessToken");
//
//        this.mockMvc.perform(
//            delete("/auth/")
//            .header("Authorization", String.format("Bearer %s", accessToken))
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON)
//        )
//        .andExpect(status().isOk())
//        .andDo(print());
//    }
//
//    @Test
//    void testModify() throws Exception {
//        //give
//        String oldPassword = "string";
//        String newPassword = "string";
//
//        JSONObject token = signin("string@aa.bb");
//
//        String accessToken = (String) token.get("accessToken");
//
//        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
//        changePasswordRequest.setOldPassword(oldPassword);
//        changePasswordRequest.setNewPassword(newPassword);
//        changePasswordRequest.setReNewPassword(newPassword);
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/refresh")
//                    .content(
//                        JsonUtils.asJsonToString(changePasswordRequest)
//                    )
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//         //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//    }
//
//    @Test
//    void testRefresh() throws Exception {
//        //give
//        JSONObject token = signin("string@aa.bb");
//
//        String accessToken = (String) token.get("accessToken");
//        String refreshToken = (String) token.get("refreshToken");
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/refresh")
//                    .content(
//                        JsonUtils.asJsonToString(refreshToken)
//                    )
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//    }
//
//
//    @Test
//    void testDelete() throws Exception {
//        //give
//        String email = "deleteString@aa.bb";
//        signup(email);
//
//        JSONObject token = signin(email);
//
//        String accessToken = (String) token.get("accessToken");
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    delete("/auth/")
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//        Assert.assertEquals(jsonObject.get("check"), true);
//    }
//
//    @Test
//    void testSignin() throws Exception {
//        //give
//        SignInRequest signInRequest = new SignInRequest();
//        signInRequest.setEmail("string@aa.bb");
//        signInRequest.setPassword("string");
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/signin")
//                    .content(
//                        JsonUtils.asJsonToString(signInRequest)
//                    )
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//    }
//
//    @Test
//    void testSignout() throws Exception {
//        //give
//        JSONObject token = signin("string@aa.bb");
//
//        String accessToken = (String) token.get("accessToken");
//        String refreshToken = (String) token.get("refreshToken");
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/signout")
//                    .content(
//                        JsonUtils.asJsonToString(refreshToken)
//                    )
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//        Assert.assertEquals(jsonObject.get("check"), true);
//
//    }
//
//    @Test
//    void testSignup() throws Exception {
//
//        //give
//        String email = "createString@aa.bb";
//
//        SignUpRequest signUpRequest = new SignUpRequest();
//        signUpRequest.setEmail(email);
//        signUpRequest.setPassword("string");
//        signUpRequest.setName("string");
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    post("/auth/signup")
//                    .content(
//                        JsonUtils.asJsonToString(signUpRequest)
//                    )
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().is2xxSuccessful())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//        Assert.assertEquals(jsonObject.get("check"), true);
//
//        //sample delete
//        remove(email);
//    }
//
//    @Test
//    void testWhoAmI() throws Exception {
//        //give
//        JSONObject token = signin("string@aa.bb");
//        String accessToken = token.get("accessToken").toString();
//
//        //when
//        ResultActions actions = this.mockMvc.perform(
//                    get("/auth/")
//                    .header("Authorization", String.format("Bearer %s", accessToken))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        //then
//        JSONObject jsonObject = JsonUtils.asStringToJson(actions.andReturn().getResponse().getContentAsString());
//        log.info("jsonObject={}",jsonObject);
//
//        Assert.assertEquals(jsonObject.get("check"), true);
//
//    }
//}
