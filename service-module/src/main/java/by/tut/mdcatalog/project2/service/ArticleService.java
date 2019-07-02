package by.tut.mdcatalog.project2.service;

import by.tut.mdcatalog.project2.service.model.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void create(ArticleDTO articleDTO);

    void update(ArticleDTO articleDTO);

    void delete(Long id);

    List<ArticleDTO> getArticles();

    ArticleDTO getById(Long id);


}
