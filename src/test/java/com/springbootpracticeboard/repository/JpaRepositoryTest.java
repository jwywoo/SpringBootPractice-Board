package com.springbootpracticeboard.repository;

import com.springbootpracticeboard.config.JpaConfig;
import com.springbootpracticeboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA CONNECTION TEST")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("TEST READ")
    @Test
    void givenTestdata_whenSelecting_thenWorksFine() {
        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).isNotNull().hasSize(0);
    }

    @DisplayName("TEST CREATE")
    @Test
    void givenTestdata_whenInserting_thenWorksFine() {
        // given
        long previousCount = articleRepository.count();
        // when
        articleRepository.save(
                Article.of("new title", "new content", "#Spring")
        );
        articleRepository.flush();
        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("TEST UPDATE")
    @Test
    void givenTestdata_whenUpdate_thenWorksFine() {
        // given
        articleRepository.save(Article.of("new title", "new content", "#Spring"));
        Article selectedArticle = articleRepository.findById(1L).orElseThrow();
        selectedArticle.setHashtag("#SpringBoot");
        // when
        articleRepository.save(selectedArticle);
        articleRepository.flush();

        // then
        assertThat(selectedArticle).hasFieldOrPropertyWithValue("hashtag", "#SpringBoot");
    }

    @DisplayName("TEST DELETE")
    @Test
    void givenTestdata_whenDelete_thenWorksFine() {
        // given
        articleRepository.save(Article.of("new title", "new content", "#Spring"));
        Article selectedArticle = articleRepository.findById(1L).orElseThrow();

        // when
        articleRepository.delete(selectedArticle);
//        articleRepository.flush();

        // then
        assertThat(articleRepository.count()).isEqualTo(0L);
    }
}