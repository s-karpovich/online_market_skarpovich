package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ArticleDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> getArticles();

    ArticleDTO getById(Long id);

    void add(ArticleDTO articleDTO);
}
