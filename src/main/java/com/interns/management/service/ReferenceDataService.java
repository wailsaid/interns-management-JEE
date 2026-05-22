package com.interns.management.service;

import com.interns.management.model.Categorie;
import com.interns.management.model.Direction;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceDataService {

    private final DataSource dataSource;

    public ReferenceDataService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Categorie> findCategories() throws SQLException {
        List<Categorie> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM categorie")) {
            while (rs.next()) {
                Categorie d = new Categorie();
                d.setid(rs.getInt("id"));
                d.setnom(rs.getString("nom"));
                list.add(d);
            }
        }
        return list;
    }

    public void addCategory(String nom) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO categorie(nom) VALUES(?)")) {
            ps.setString(1, nom);
            ps.executeUpdate();
        }
    }

    public void deleteCategory(int id) throws SQLException {
        List<String> queries = List.of(
                "DELETE FROM admin WHERE idcat = ?",
                "DELETE FROM stagiare WHERE idcat = ?",
                "DELETE FROM stage WHERE idcat = ?",
                "DELETE FROM encadreur WHERE idcat = ?",
                "DELETE FROM categorie WHERE id = ?"
        );
        executeDeletes(queries, id);
    }

    public List<Direction> findDirections() throws SQLException {
        List<Direction> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM direction")) {
            while (rs.next()) {
                Direction d = new Direction();
                d.setid(rs.getInt("id"));
                d.setnom(rs.getString("nom"));
                list.add(d);
            }
        }
        return list;
    }

    public void addDirection(String nom) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO direction(nom) VALUES(?)")) {
            ps.setString(1, nom);
            ps.executeUpdate();
        }
    }

    public void deleteDirection(int id) throws SQLException {
        List<String> queries = List.of(
                "DELETE FROM direction WHERE id = ?",
                "DELETE FROM admin WHERE iddir = ?",
                "DELETE FROM stagiare WHERE iddir = ?",
                "DELETE FROM encadreur WHERE iddir = ?"
        );
        executeDeletes(queries, id);
    }

    private void executeDeletes(List<String> queries, int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            for (String sql : queries) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }
        }
    }
}
