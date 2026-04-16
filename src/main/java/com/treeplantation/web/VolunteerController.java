package com.treeplantation.web;

import com.treeplantation.model.Volunteer;
import com.treeplantation.service.VolunteerService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class VolunteerController implements Serializable {

    @ManagedProperty("#{volunteerService}")
    private VolunteerService volunteerService;

    private Volunteer newVolunteer;
    private List<Volunteer> volunteers;

    @PostConstruct
    public void init() {
        newVolunteer = new Volunteer();
        refreshVolunteers();
    }

    public Volunteer getNewVolunteer() {
        return newVolunteer;
    }

    public void setNewVolunteer(Volunteer newVolunteer) {
        this.newVolunteer = newVolunteer;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public String saveVolunteer() {
        volunteerService.saveVolunteer(newVolunteer);
        newVolunteer = new Volunteer(); // Reset form
        refreshVolunteers();
        return "volunteers?faces-redirect=true";
    }

    public void deleteVolunteer(Long id) {
        volunteerService.deleteVolunteer(id);
        refreshVolunteers();
    }

    private void refreshVolunteers() {
        volunteers = volunteerService.getAllVolunteers();
    }

    // Setter required for Spring Dependency Injection into JSF context
    public void setVolunteerService(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }
}
