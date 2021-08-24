package edu.samir.demo.springboottesting.repository.persistance;

import edu.samir.demo.springboottesting.repository.entity.ArticleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Container
    public static PostgreSQLContainer psqlContainer = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password");

    // Dynamically populate environment properties
    @DynamicPropertySource
    static void updateProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", psqlContainer::getUsername);
        registry.add("spring.datasource.password", psqlContainer::getPassword);
    }

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        assertNotNull(articleRepository);
    }

    @Test
    public void save_whenNewArticle_thenArticleNameMatchHuawei() throws Exception {
        this.articleRepository.save(new ArticleEntity(1L, "Huawei", "Description", 1000));
        assertEquals(1, articleRepository.findAll().size());
    }

    @Test
    public void findById_whenId1_thenReturnArticle() throws Exception {
        this.articleRepository.save(new ArticleEntity(1L, "Huawei", "Description", 1000));
        assertEquals(1, articleRepository.findById(1L).get().getId());
        assertEquals("Huawei", articleRepository.findById(1L).get().getName());
    }

}