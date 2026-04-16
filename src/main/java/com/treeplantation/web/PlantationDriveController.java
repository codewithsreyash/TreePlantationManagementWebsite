package com.treeplantation.web;

import com.treeplantation.model.PlantationDrive;
import com.treeplantation.model.Tree;
import com.treeplantation.model.Volunteer;
import com.treeplantation.service.PlantationDriveService;
import com.treeplantation.service.TreeSpeciesService;
import com.treeplantation.service.VolunteerService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class PlantationDriveController implements Serializable {

    @ManagedProperty("#{plantationDriveService}")
    private PlantationDriveService plantationDriveService;
    
    @ManagedProperty("#{volunteerService}")
    private VolunteerService volunteerService;

    @ManagedProperty("#{treeSpeciesService}")
    private TreeSpeciesService treeSpeciesService;

    private PlantationDrive newDrive;
    private List<PlantationDrive> drives;
    
    // Assignment fields
    private Long selectedDriveId;
    private Long selectedVolunteerId;
    
    // Logging fields
    private Long selectedTreeId;
    private int quantity;

    @PostConstruct
    public void init() {
        newDrive = new PlantationDrive();
        refreshDrives();
    }

    public PlantationDrive getNewDrive() {
        return newDrive;
    }

    public void setNewDrive(PlantationDrive newDrive) {
        this.newDrive = newDrive;
    }

    public List<PlantationDrive> getDrives() {
        return drives;
    }

    public String saveDrive() {
        plantationDriveService.saveDrive(newDrive);
        newDrive = new PlantationDrive(); // Reset form
        refreshDrives();
        return "index?faces-redirect=true"; // Navigate back to the dashboard
    }

    // Assignment feature methods
    public Long getSelectedDriveId() { return selectedDriveId; }
    public void setSelectedDriveId(Long selectedDriveId) { this.selectedDriveId = selectedDriveId; }

    public Long getSelectedVolunteerId() { return selectedVolunteerId; }
    public void setSelectedVolunteerId(Long selectedVolunteerId) { this.selectedVolunteerId = selectedVolunteerId; }

    public List<Volunteer> getAvailableVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    public String assignVolunteer() {
        if (selectedDriveId != null && selectedVolunteerId != null) {
            plantationDriveService.assignVolunteerToDrive(selectedDriveId, selectedVolunteerId);
        }
        refreshDrives(); // Update grid state
        return "index?faces-redirect=true";
    }

    public Long getSelectedTreeId() { return selectedTreeId; }
    public void setSelectedTreeId(Long selectedTreeId) { this.selectedTreeId = selectedTreeId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public List<Tree> getAvailableTrees() {
        return treeSpeciesService.getAllTrees();
    }

    public String logPlanting() {
        if (selectedDriveId != null && selectedTreeId != null && quantity > 0) {
            plantationDriveService.logTreePlanting(selectedDriveId, selectedTreeId, quantity);
        }
        refreshDrives();
        return "index?faces-redirect=true";
    }

    public int getTreeCount(Long driveId) {
        return plantationDriveService.getPlantedTreeCount(driveId);
    }

    private void refreshDrives() {
        drives = plantationDriveService.getAllDrives();
    }

    // Required setter for Spring dependency injection in JSF
    public void setPlantationDriveService(PlantationDriveService plantationDriveService) {
        this.plantationDriveService = plantationDriveService;
    }

    public void setVolunteerService(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    public void setTreeSpeciesService(TreeSpeciesService treeSpeciesService) {
        this.treeSpeciesService = treeSpeciesService;
    }
}
