package com.springbootpracticeboard.repository;

import com.springbootpracticeboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
