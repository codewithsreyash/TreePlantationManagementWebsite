package com.treeplantation.web;

import com.treeplantation.service.ReportService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.Map;

@ManagedBean
@RequestScoped
public class ReportController {

    @ManagedProperty("#{reportService}")
    private ReportService reportService;

    private int totalTrees;
    private int totalDrives;
    private int totalVolunteers;
    private int co2OffsetEstimate; // GAP 6

    private List<Map<String, Object>> topReforesters;
    private List<Map<String, Object>> speciesDiversity;

    @PostConstruct
    public void init() {
        totalTrees = reportService.getTotalTrees();
        totalDrives = reportService.getTotalDrives();
        totalVolunteers = reportService.getTotalVolunteers();
        co2OffsetEstimate = reportService.getCo2OffsetEstimate(); // GAP 6
        topReforesters = reportService.getTopReforesters();
        speciesDiversity = reportService.getSpeciesDiversity();
    }

    public int getTotalTrees() { return totalTrees; }
    public int getTotalDrives() { return totalDrives; }
    public int getTotalVolunteers() { return totalVolunteers; }
    public int getCo2OffsetEstimate() { return co2OffsetEstimate; } // GAP 6
    public List<Map<String, Object>> getTopReforesters() { return topReforesters; }
    public List<Map<String, Object>> getSpeciesDiversity() { return speciesDiversity; }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
