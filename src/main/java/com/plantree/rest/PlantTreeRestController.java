package com.plantree.rest;

import com.treeplantation.dao.PlantationDriveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drives")
public class PlantTreeRestController {

    @Autowired
    private PlantationDriveDAO driveDAO;

    @GetMapping(produces = "application/json")
    public List<Map<String, Object>> getAllDrives() {
        // We map to Map<String, Object> to confidently avoid Hibernate LazyInitializationExceptions
        // during Jackson serialization for the defense presentation.
        return driveDAO.findAll().stream().map(drive -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", drive.getId());
            map.put("location", drive.getLocation());
            map.put("driveDate", drive.getDriveDate() != null ? drive.getDriveDate().toString() : null);
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}/trees", produces = "application/json")
    public Map<String, Object> getTreeCount(@PathVariable("id") Long id) {
        int count = driveDAO.countTreesForDrive(id);
        Map<String, Object> response = new HashMap<>();
        response.put("driveId", id);
        response.put("treeCount", count);
        return response;
    }
}
