package edu.samir.demo.springboottesting.service;

import edu.samir.demo.springboottesting.repository.entity.ArticleEntity;
import edu.samir.demo.springboottesting.repository.persistance.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleEntity insertArticle(ArticleEntity article) {
        return this.articleRepository.save(article);
    }

    public ArticleEntity selectArticleById(Long articleId) {
        return this.articleRepository.findById(articleId).orElseThrow();
    }

    public List<ArticleEntity> selectAllArticles() {
        return this.articleRepository.findAll();
    }

    public ArticleEntity updateArticle(ArticleEntity updated) {
        return this.articleRepository.save(updated);
    }

    public void deleteArticleById(Long articleId) {
        this.articleRepository.deleteById(articleId);
    }
}
