package edu.samir.demo.springboottesting.repository.persistance;

import edu.samir.demo.springboottesting.repository.entity.ArticleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        assertNotNull(dataSource);
        assertNotNull(entityManager);
        assertNotNull(articleRepository);
        articleRepository.save(new ArticleEntity(1L, "Huawei", "Description", 1000));
    }

    @Test
    public void save_whenNewArticle_thenArticleNameMatchHuawei() throws Exception {
        assertEquals(1, articleRepository.findAll().size());
    }

    @Test
    public void findById_whenId1_thenReturnArticle() throws Exception {
        assertEquals(1, articleRepository.findById(1L).get().getId());
        assertEquals("Huawei", articleRepository.findById(1L).get().getName());
    }

}