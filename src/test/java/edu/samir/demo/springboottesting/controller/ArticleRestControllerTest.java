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

import static org.mockito.Mockito.when;
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
    }

    @Test
    public void getArticleById_whenId1_thenReturnFirstArticle() throws Exception {
        ArticleEntity article1 = new ArticleEntity(1L, "Apple MacBookAir M1", "Laptop i7 8Go 512SSD 13\"", 1399);
        when(articleService.selectArticleById(1L)).thenReturn(article1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/article/get/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1399));

    }

    @Test
    //@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1, delimiter = ':')
    public void getArticles_ReturnAllArticles() throws Exception {
        when(this.articleService.selectAllArticles()).thenReturn(
                List.of(
                        new ArticleEntity(1L, "Apple MacBookAir M1", "Laptop i7 8Go 512SSD 13\"", 1399),
                        new ArticleEntity(2L, "Huawei MateBook D14", "Laptop i7 16Go 512SSD 14\"", 1249),
                        new ArticleEntity(3L, "Lenovo ThinkBook 15 G2", "Laptop Ryzen5 8Go 512SSD 15.6\"", 899)
                )
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/article/get/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Lenovo ThinkBook 15 G2"));

    }

    @Test
    public void addArticle_whenNewArticle_thenArticleNameMatchLaptop() throws Exception {
        ArticleEntity laptop = new ArticleEntity(1L, "Laptop", "Description", 1000);
        when(this.articleService.insertArticle(laptop)).thenReturn(laptop);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/article/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(laptop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void updateArticle_whenUpdatePrice_thenReturn1500() throws Exception {
        ArticleEntity updatedArticle = new ArticleEntity(1L, "Laptop", "Description", 1500);
        when(this.articleService.updateArticle(updatedArticle)).thenReturn(updatedArticle);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/article/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedArticle)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1500));
    }

    @Test
    public void deleteArticleById_whenId1_thenStatusAccepted() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/article/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

}