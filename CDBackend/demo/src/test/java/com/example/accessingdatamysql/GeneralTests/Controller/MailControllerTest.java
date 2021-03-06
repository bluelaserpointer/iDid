package com.example.accessingdatamysql.GeneralTests.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.controller.MailController;
import com.example.accessingdatamysql.entity.AuthRequest;
import com.example.accessingdatamysql.entity.ListRequest;
import com.example.accessingdatamysql.entity.Mail;
import com.example.accessingdatamysql.entity.MailBox;
import com.example.accessingdatamysql.entity.MailDetails;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.MailBoxService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailControllerTest {

        // @Test
        // @DisplayName("File: MailController Method: contextLoads")
        // public void contextLoads() {
        // assertThat(mailController).isNotNull();
        // }
        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        private String TOKEN;

        @Autowired
        private MailBoxService mailBoxService;
        // @Autowired
        // private MailController mailController;

        @Before
        public void setUp() {
                mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @AfterEach
        void tearDown() {

        }

        @AfterAll
        static void tearDownAll() {

        }

        @Transactional
        @Rollback(value = true)
        public String getTOKEN() throws Exception {
                // System.out.println(TOKEN);
                User addedUser = new User("postTest", "postTest", "postTest", "postTest", "ROLE_ADMIN");
                String addU = JSON.toJSONString(addedUser);
                // System.out.println(addU);
                mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/user/unitTest")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(addU))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                AuthRequest user = new AuthRequest();
                user.setPassword("postTest");
                user.setUserName("postTest");

                String body = JSON.toJSONString(user);
                System.out.println(body);
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("http://localhost:8080/user/login")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                TOKEN = result.getResponse().getContentAsString();
                System.out.println(TOKEN);
                JSONObject temp = JSON.parseObject(TOKEN);
                TOKEN = temp.getString("token");
                TOKEN = "Bearer " + TOKEN;
                System.out.println(TOKEN);
                return TOKEN;
        }

        @Transactional
        @Rollback(value = true)
        public Mail addMailBeforeTest(String token) throws Exception {
                Mail Mail = new Mail("addMailBeforeTest");
                MailDetails MailDetails = new MailDetails();
                MailDetails.setMailDescription("addMailBeforeTest");
                MailDetails.setMailImg("addMailBeforeTest");
                Mail.setMailDetails(MailDetails);

                String body = JSON.toJSONString(Mail);

                mailBoxService.addNewMailBox(1);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/mail/addMail")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());

                // System.out.println(result.getResponse().getContentAsString());
                String json = result.getResponse().getContentAsString();
                Mail = JSON.parseObject(json, Mail.class);
                return Mail;
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: findMailByMailId")
        public void findMailByMailId() throws Exception {
                String token = getTOKEN();
                Mail addedMail = addMailBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/mail/getMail?mailId=" + addedMail.getMailId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: addNewMail")
        public void addNewMail() throws Exception {
                String token = getTOKEN();
                Mail Mail = new Mail("addNewMail");
                MailDetails MailDetails = new MailDetails();
                MailDetails.setMailDescription("addNewMail");
                MailDetails.setMailImg("addNewMail");
                Mail.setMailDetails(MailDetails);

                String body = JSON.toJSONString(Mail);

                // System.out.println(body);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/mail/addMail")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: updateMail")
        public void updateMail() throws Exception {
                String token = getTOKEN();
                Mail addedMail = addMailBeforeTest(token);
                Mail Mail = new Mail("updateMail");
                MailDetails MailDetails = new MailDetails(addedMail.getMailId(), "updateMail", "updateMail");
                Mail.setMailDetails(MailDetails);
                Mail.setMailId(addedMail.getMailId());

                String body = JSON.toJSONString(Mail);
                MvcResult result = mockMvc
                                .perform(get("/mail/updateMail?mailId=" + addedMail.getMailId())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(body)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: getAllMails")
        public void getAllMails() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/mail/getAllMails").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteMails")
        public void deleteMails() throws Exception {
                String token = getTOKEN();
                Mail addedMail = addMailBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/mail/deleteMails?mailIds=" + addedMail.getMailId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteAllMails")
        public void deleteAllMails() throws Exception {
                String token = getTOKEN();
                MvcResult result = mockMvc
                                .perform(get("/mail/deleteAllMails").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: deleteMail")
        public void deleteMail() throws Exception {
                String token = getTOKEN();
                Mail addedMail = addMailBeforeTest(token);
                MvcResult result = mockMvc.perform(get("/mail/deleteMail?mailId=" + addedMail.getMailId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: MailController Method: ListPage")
        public void ListPage() throws Exception {
                String token = getTOKEN();
                // Activity addedActivity = addActivityBeforeTest(token);

                ListRequest listRequest = new ListRequest();
                listRequest.setPageSize(10);
                listRequest.setPageToken(1);

                String body = JSON.toJSONString(listRequest);
                System.out.println(body);
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mail/List").content(body)
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: mailController Method: sendMail")
        public void sendMail() throws Exception {
                String token = getTOKEN();
                addMailBeforeTest(token);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mail/sendMail?mailId=1&userId=1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @Transactional
        @Rollback(value = true)
        @DisplayName("File: mailController Method: sendMailToAllUsers")
        public void sendMailToAllUsers() throws Exception {
                String token = getTOKEN();
                Mail add = addMailBeforeTest(token);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                                .post("/mail/sendMailToAllUsers?mailId=" + add.getMailId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
                                .andReturn();
                System.out.println(result.getResponse().getContentAsString());
        }

}