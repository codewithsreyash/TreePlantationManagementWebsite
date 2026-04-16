package com.treeplantation.dao;

import com.treeplantation.model.Tree;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TreeSpeciesDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Tree tree) {
        sessionFactory.getCurrentSession().saveOrUpdate(tree);
    }

    @SuppressWarnings("unchecked")
    public List<Tree> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Tree")
                .list();
    }

    public Tree findById(Long id) {
        return sessionFactory.getCurrentSession().get(Tree.class, id);
    }

    public void delete(Long id) {
        Tree tree = sessionFactory.getCurrentSession().get(Tree.class, id);
        if (tree != null) {
            sessionFactory.getCurrentSession().delete(tree);
        }
    }
}
