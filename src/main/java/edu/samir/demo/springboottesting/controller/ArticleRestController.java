package edu.samir.demo.springboottesting.controller;

import edu.samir.demo.springboottesting.repository.entity.ArticleEntity;
import edu.samir.demo.springboottesting.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/article")
@RequiredArgsConstructor
public class ArticleRestController {

    private final ArticleService articleService;

    @GetMapping(
            path = "get/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticleEntity getArticleById(@PathVariable Long articleId) {
        return this.articleService.selectArticleById(articleId);
    }

    @GetMapping(
            path = "get/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ArticleEntity> getArticles() {
        return this.articleService.selectAllArticles();
    }

    @PostMapping(
            path = "add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleEntity> addArticle(@RequestBody ArticleEntity newArticle) {
        return new ResponseEntity<>(this.articleService.insertArticle(newArticle), HttpStatus.CREATED);
    }

    @PutMapping(
            path = "update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleEntity> updateArticle(@RequestBody ArticleEntity updated) {
        return new ResponseEntity<>(this.articleService.updateArticle(updated), HttpStatus.ACCEPTED);
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("delete/{articleId}")
    public void removeArticleById(@PathVariable Long articleId) {
        this.articleService.deleteArticleById(articleId);
    }
}
