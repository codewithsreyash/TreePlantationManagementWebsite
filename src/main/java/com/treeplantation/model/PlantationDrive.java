package com.treeplantation.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "plantation_drive")
public class PlantationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(name = "drive_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date driveDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "drive_volunteers",
        joinColumns = @JoinColumn(name = "drive_id"),
        inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<Volunteer> volunteers = new java.util.HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "drive_trees",
        joinColumns = @JoinColumn(name = "drive_id"),
        inverseJoinColumns = @JoinColumn(name = "tree_id")
    )
    private Set<Tree> trees = new java.util.HashSet<>();

    public PlantationDrive() {}

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(Date driveDate) {
        this.driveDate = driveDate;
    }

    public Set<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public Set<Tree> getTrees() {
        return trees;
    }

    public void setTrees(Set<Tree> trees) {
        this.trees = trees;
    }
}
