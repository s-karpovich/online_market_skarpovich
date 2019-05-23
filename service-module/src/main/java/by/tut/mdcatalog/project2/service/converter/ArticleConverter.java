package by.tut.mdcatalog.project2.service.converter;

import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.service.model.ArticleDTO;

public interface ArticleConverter {

    ArticleDTO toDTO(Article article);

    Article fromDTO(ArticleDTO articleDTO);
}
