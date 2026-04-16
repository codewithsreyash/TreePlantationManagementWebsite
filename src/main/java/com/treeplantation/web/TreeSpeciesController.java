package com.treeplantation.web;

import com.treeplantation.model.Tree;
import com.treeplantation.service.TreeSpeciesService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class TreeSpeciesController implements Serializable {

    @ManagedProperty("#{treeSpeciesService}")
    private TreeSpeciesService treeSpeciesService;

    private Tree newTree;
    private List<Tree> trees;

    @PostConstruct
    public void init() {
        newTree = new Tree();
        refreshTrees();
    }

    public Tree getNewTree() {
        return newTree;
    }

    public void setNewTree(Tree newTree) {
        this.newTree = newTree;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    public String saveTree() {
        treeSpeciesService.saveTree(newTree);
        newTree = new Tree();
        refreshTrees();
        return "tree-species?faces-redirect=true";
    }

    public void deleteTree(Long id) {
        treeSpeciesService.deleteTree(id);
        refreshTrees();
    }

    private void refreshTrees() {
        trees = treeSpeciesService.getAllTrees();
    }

    public void setTreeSpeciesService(TreeSpeciesService treeSpeciesService) {
        this.treeSpeciesService = treeSpeciesService;
    }
}
