package com.treeplantation.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Uses pure JDBC to generate a complex analytics report.
     * Example: "Total trees planted per region"
     */
    public Map<String, Integer> getTotalTreesPerRegion() {
        Map<String, Integer> report = new HashMap<>();
        // Pure JDBC query to aggregate trees by drive location
        String query = "SELECT p.location, COUNT(t.tree_id) as total_trees " +
                       "FROM plantation_drive p " +
                       "JOIN drive_trees t ON p.id = t.drive_id " +
                       "GROUP BY p.location";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             
             while (rs.next()) {
                 report.put(rs.getString("location"), rs.getInt("total_trees"));
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    public int getTotalTrees() {
        return queryForInt("SELECT COUNT(*) FROM drive_trees");
    }

    public int getTotalDrives() {
        return queryForInt("SELECT COUNT(*) FROM plantation_drive");
    }

    public int getTotalVolunteers() {
        return queryForInt("SELECT COUNT(*) FROM volunteer");
    }

    private int queryForInt(String query) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // GAP 6: Calculate CO2 offset using raw JDBC PreparedStatement
    public int getCo2OffsetEstimate() {
        int co2Offset = 0;
        String query = "SELECT COUNT(*) * 21 AS co2_offset FROM drive_trees";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
             if (rs.next()) {
                 co2Offset = rs.getInt("co2_offset");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return co2Offset;
    }

    public List<Map<String, Object>> getTopReforesters() {
        List<Map<String, Object>> results = new ArrayList<>();
        String q = "SELECT v.name, COUNT(dv.drive_id) as drive_count FROM volunteer v JOIN drive_volunteers dv ON v.id = dv.volunteer_id GROUP BY v.id ORDER BY drive_count DESC LIMIT 5";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("name", rs.getString("name"));
                 map.put("drive_count", rs.getInt("drive_count"));
                 results.add(map);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Map<String, Object>> getSpeciesDiversity() {
        List<Map<String, Object>> results = new ArrayList<>();
        String q = "SELECT t.name, COUNT(dt.drive_id) as count FROM tree_species t JOIN drive_trees dt ON t.id = dt.tree_id GROUP BY t.id ORDER BY count DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
             while (rs.next()) {
                 Map<String, Object> map = new HashMap<>();
                 map.put("name", rs.getString("name"));
                 map.put("count", rs.getInt("count"));
                 results.add(map);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
