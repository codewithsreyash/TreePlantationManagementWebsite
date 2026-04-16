package com.treeplantation.service;

import com.treeplantation.dao.VolunteerDAO;
import com.treeplantation.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VolunteerService {

    @Autowired
    private VolunteerDAO volunteerDAO;

    public void saveVolunteer(Volunteer volunteer) {
        volunteerDAO.save(volunteer);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerDAO.findAll();
    }

    public void deleteVolunteer(Long id) {
        volunteerDAO.delete(id);
    }
}
