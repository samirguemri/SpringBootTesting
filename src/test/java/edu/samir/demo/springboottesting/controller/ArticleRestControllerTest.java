package edu.samir.demo.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.samir.demo.springboottesting.repository.entity.ArticleEntity;
import edu.samir.demo.springboottesting.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleRestController.class)
class ArticleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    public void getArticleById_whenId1_thenReturnFirstArticle() throws Exception {
        ArticleEntity article1 = new ArticleEntity(1L, "Apple", "Description", 1000);
        when(articleService.selectArticleById(1L)).thenReturn(article1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/article/get/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1000));

    }

    @Test
    //@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1, delimiter = ':')
    public void getArticles_ReturnAllArticles() throws Exception {
        when(this.articleService.selectAllArticles()).thenReturn(
                List.of(
                        new ArticleEntity(1L, "Apple", "Apple Description", 1399),
                        new ArticleEntity(2L, "Huawei", "Huawei Description", 1249),
                        new ArticleEntity(3L, "Lenovo", "Lenovo Description", 899)
                )
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/article/get/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Lenovo"));

    }

    @Test
    public void addArticle_whenNewArticle_thenArticleNameMatchLaptop() throws Exception {
        ArticleEntity laptop = new ArticleEntity(1L, "Apple", "Description", 1000);
        when(this.articleService.insertArticle(laptop)).thenReturn(laptop);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/article/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(laptop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Apple"));
    }

    @Test
    public void updateArticle_whenUpdatePrice_thenReturn1500() throws Exception {
        ArticleEntity updatedArticle = new ArticleEntity(1L, "Apple", "Description", 1500);
        when(this.articleService.updateArticle(updatedArticle)).thenReturn(updatedArticle);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/article/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedArticle)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1500));
    }

    @Test
    public void deleteArticleById_whenUserIsAuthenticatedAndId1_thenStatusAccepted() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/article/delete/1")
                        .with(user("user").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteArticleById_whenUserIsAnonymous_thenRejectDeleting() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/article/delete/1").with(csrf()))
                .andExpect(status().isUnauthorized());
    }

}