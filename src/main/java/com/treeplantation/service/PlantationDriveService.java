package com.treeplantation.service;

import com.treeplantation.dao.PlantationDriveDAO;
import com.treeplantation.dao.VolunteerDAO;
import com.treeplantation.model.PlantationDrive;
import com.treeplantation.model.Volunteer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlantationDriveService {

    @Autowired
    private PlantationDriveDAO plantationDriveDAO;

    @Autowired
    private VolunteerDAO volunteerDAO;

    @Autowired
    private SessionFactory sessionFactory;

    public void assignVolunteerToDrive(Long driveId, Long volunteerId) {
        PlantationDrive drive = plantationDriveDAO.findById(driveId);
        Volunteer volunteer = volunteerDAO.findById(volunteerId);
        
        if (drive != null && volunteer != null) {
            drive.getVolunteers().add(volunteer);
            plantationDriveDAO.save(drive);
        }
    }

    public void saveDrive(PlantationDrive drive) {
        plantationDriveDAO.save(drive);
    }

    public List<PlantationDrive> getAllDrives() {
        return plantationDriveDAO.findAll();
    }

    public List<PlantationDrive> getDrivesByLocation(String location) {
        return plantationDriveDAO.findByLocation(location);
    }

    public void logTreePlanting(Long driveId, Long treeId, int quantity) {
        if (quantity <= 0) return;
        
        // Using native SQL to perform bulk insert to the join table to accurately represent quantity
        for (int i = 0; i < quantity; i++) {
            sessionFactory.getCurrentSession()
                    .createNativeQuery("INSERT INTO drive_trees (drive_id, tree_id) VALUES (:driveId, :treeId)")
                    .setParameter("driveId", driveId)
                    .setParameter("treeId", treeId)
                    .executeUpdate();
        }
    }

    public int getPlantedTreeCount(Long driveId) {
        return plantationDriveDAO.countTreesForDrive(driveId);
    }
}
