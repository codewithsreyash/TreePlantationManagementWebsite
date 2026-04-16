package com.treeplantation.service;

import com.treeplantation.dao.TreeSpeciesDAO;
import com.treeplantation.model.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TreeSpeciesService {

    @Autowired
    private TreeSpeciesDAO treeSpeciesDAO;

    public void saveTree(Tree tree) {
        treeSpeciesDAO.save(tree);
    }

    public List<Tree> getAllTrees() {
        return treeSpeciesDAO.findAll();
    }

    public void deleteTree(Long id) {
        treeSpeciesDAO.delete(id);
    }
}
