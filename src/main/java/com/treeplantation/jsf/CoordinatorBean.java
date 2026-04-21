package com.treeplantation.jsf;

import com.treeplantation.dao.PlantationDriveDAO;
import com.treeplantation.dao.ReportDAO;
import com.treeplantation.model.PlantationDrive;
import com.treeplantation.service.BulkEmailNotificationService;
import com.plantree.service.WeatherSocketClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class CoordinatorBean implements Serializable {

    // Injecting Spring beans. 
    @ManagedProperty("#{plantationDriveDAO}")
    private PlantationDriveDAO driveDAO;

    @ManagedProperty("#{reportDAO}")
    private ReportDAO reportDAO;

    @ManagedProperty("#{bulkEmailNotificationService}")
    private BulkEmailNotificationService emailService;

    @ManagedProperty("#{weatherSocketClient}")
    private WeatherSocketClient weatherClient;

    private PlantationDrive newDrive;
    private List<PlantationDrive> allDrives;
    private Map<String, Integer> treeReport;
    private String weatherStatus;

    @PostConstruct
    public void init() {
        newDrive = new PlantationDrive();
        refreshDrives();
        treeReport = reportDAO.getTotalTreesPerRegion();
    }

    public void createDrive() {
        driveDAO.save(newDrive);
        
        // Fetch weather notification via socket networking for the location
        weatherStatus = weatherClient.fetchWeatherAlerts(newDrive.getLocation());
        
        // Notify volunteers asynchronously via Threadpool if there are registered participants
        if (newDrive.getVolunteers() != null && !newDrive.getVolunteers().isEmpty()) {
            emailService.notifyVolunteersDateChange(
                newDrive.getVolunteers(), 
                newDrive.getDriveDate().toString(), 
                newDrive.getLocation()
            );
        }

        newDrive = new PlantationDrive(); // Reset form model
        refreshDrives();
        // Update tree report analytics after inserts
        treeReport = reportDAO.getTotalTreesPerRegion();
    }

    private void refreshDrives() {
        allDrives = driveDAO.findAll();
    }

    // Setters for ManagedProperties (Required for Spring EL injection)
    public void setDriveDAO(PlantationDriveDAO driveDAO) { this.driveDAO = driveDAO; }
    public void setReportDAO(ReportDAO reportDAO) { this.reportDAO = reportDAO; }
    public void setEmailService(BulkEmailNotificationService emailService) { this.emailService = emailService; }
    public void setWeatherClient(WeatherSocketClient weatherClient) { this.weatherClient = weatherClient; }

    // Getters and Setter for UI fields
    public PlantationDrive getNewDrive() { return newDrive; }
    public void setNewDrive(PlantationDrive newDrive) { this.newDrive = newDrive; }
    public List<PlantationDrive> getAllDrives() { return allDrives; }
    public Map<String, Integer> getTreeReport() { return treeReport; }
    public String getWeatherStatus() { return weatherStatus; }
}
