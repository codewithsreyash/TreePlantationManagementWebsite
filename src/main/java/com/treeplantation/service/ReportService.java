package com.treeplantation.service;

import com.treeplantation.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportDAO reportDAO;

    public int getTotalTrees() {
        return reportDAO.getTotalTrees();
    }

    public int getTotalDrives() {
        return reportDAO.getTotalDrives();
    }

    public int getTotalVolunteers() {
        return reportDAO.getTotalVolunteers();
    }

    public List<Map<String, Object>> getTopReforesters() {
        return reportDAO.getTopReforesters();
    }

    public List<Map<String, Object>> getSpeciesDiversity() {
        return reportDAO.getSpeciesDiversity();
    }
}
