package by.tut.mdcatalog.project2.repository.impl;

import by.tut.mdcatalog.project2.repository.CommentRepository;
import by.tut.mdcatalog.project2.repository.constant.RepositoryErrors;
import by.tut.mdcatalog.project2.repository.exception.DataBaseException;
import by.tut.mdcatalog.project2.repository.model.Article;
import by.tut.mdcatalog.project2.repository.model.Comment;
import by.tut.mdcatalog.project2.repository.model.Contact;
import by.tut.mdcatalog.project2.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl implements CommentRepository {

    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Comment> getByArticleId(Long id) {
        String hql = "from Comment as C where C.article.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
