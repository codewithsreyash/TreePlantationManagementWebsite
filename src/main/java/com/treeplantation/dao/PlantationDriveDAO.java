package com.treeplantation.dao;

import com.treeplantation.model.PlantationDrive;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PlantationDriveDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(PlantationDrive drive) {
        sessionFactory.getCurrentSession().saveOrUpdate(drive);
    }

    @SuppressWarnings("unchecked")
    public List<PlantationDrive> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from PlantationDrive")
                .list();
    }

    public PlantationDrive findById(Long id) {
        return sessionFactory.getCurrentSession().get(PlantationDrive.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<PlantationDrive> findByLocation(String location) {
        return sessionFactory.getCurrentSession()
                .createQuery("from PlantationDrive where location = :loc")
                .setParameter("loc", location)
                .list();
    }

    public int countTreesForDrive(Long driveId) {
        Number count = (Number) sessionFactory.getCurrentSession()
                .createNativeQuery("SELECT count(*) FROM drive_trees WHERE drive_id = :driveId")
                .setParameter("driveId", driveId)
                .getSingleResult();
        return count != null ? count.intValue() : 0;
    }
}
