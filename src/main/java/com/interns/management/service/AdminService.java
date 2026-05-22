package com.interns.management.service;

import com.interns.management.model.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final DataSource dataSource;

    public AdminService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Admin> findAll() throws SQLException {
        String query = """
                SELECT * FROM admin
                INNER JOIN categorie ON admin.idcat = categorie.id
                INNER JOIN direction ON admin.iddir = direction.id
                """;
        List<Admin> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                list.add(mapAdmin(rs));
            }
        }
        return list;
    }

    public Admin findById(String id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM admin INNER JOIN direction ON admin.iddir=direction.id " +
                             "INNER JOIN categorie ON admin.idcat=categorie.id WHERE admin.id = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Admin ad = new Admin();
                if (rs.next()) {
                    ad.setid(rs.getInt("admin.id"));
                    ad.setnom(rs.getString("admin.nom"));
                    ad.setprenom(rs.getString("admin.prenom"));
                    ad.setcategorie(rs.getString("categorie.nom"));
                    ad.setdirection(rs.getString("direction.nom"));
                    ad.setusername(rs.getString("admin.username"));
                    ad.setpassword(rs.getString("admin.password"));
                }
                return ad;
            }
        }
    }

    public record AddFormData(List<Categorie> categories, List<Direction> directions) {}

    public AddFormData loadAddForm() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return new AddFormData(loadCategories(conn), loadDirections(conn));
        }
    }

    public void create(Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO admin(nom,prenom,username,password,idcat,iddir) VALUES(?,?,?,?,?,?)")) {
            ps.setString(1, admin.getnom());
            ps.setString(2, admin.getprenom());
            ps.setString(3, admin.getusername());
            ps.setString(4, admin.getpassword());
            ps.setInt(5, Integer.parseInt(admin.getcategorie()));
            ps.setInt(6, Integer.parseInt(admin.getdirection()));
            ps.executeUpdate();
        }
    }

    public record EditFormData(Admin admin, List<Categorie> categories, List<Direction> directions) {}

    public EditFormData loadEditForm(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Admin ad = loadAdminById(conn, id);
            return new EditFormData(ad, loadCategories(conn), loadDirections(conn));
        }
    }

    public void update(Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE admin SET nom=?, prenom=?, username=?, password=?, iddir=?, idcat=? WHERE admin.id=?")) {
            ps.setString(1, admin.getnom());
            ps.setString(2, admin.getprenom());
            ps.setString(3, admin.getusername());
            ps.setString(4, admin.getpassword());
            ps.setInt(5, Integer.parseInt(admin.getdirection()));
            ps.setInt(6, Integer.parseInt(admin.getcategorie()));
            ps.setInt(7, admin.getid());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM admin WHERE id = ?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    private Admin loadAdminById(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM admin INNER JOIN direction ON admin.iddir=direction.id " +
                        "INNER JOIN categorie ON admin.idcat=categorie.id WHERE admin.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Admin ad = new Admin();
                if (rs.next()) {
                    ad.setid(rs.getInt("admin.id"));
                    ad.setnom(rs.getString("admin.nom"));
                    ad.setprenom(rs.getString("admin.prenom"));
                    ad.setcategorie(rs.getString("categorie.nom"));
                    ad.setdirection(rs.getString("direction.nom"));
                    ad.setusername(rs.getString("admin.username"));
                    ad.setpassword(rs.getString("admin.password"));
                }
                return ad;
            }
        }
    }

    private Admin mapAdmin(ResultSet rs) throws SQLException {
        Admin a = new Admin();
        a.setid(rs.getInt("admin.id"));
        a.setnom(rs.getString("admin.nom"));
        a.setprenom(rs.getString("admin.prenom"));
        a.setcategorie(rs.getString("categorie.nom"));
        a.setdirection(rs.getString("direction.nom"));
        a.setusername(rs.getString("admin.username"));
        return a;
    }

    private List<Categorie> loadCategories(Connection conn) throws SQLException {
        List<Categorie> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categorie")) {
            while (rs.next()) {
                Categorie c = new Categorie();
                c.setid(rs.getInt("id"));
                c.setnom(rs.getString("nom"));
                list.add(c);
            }
        }
        return list;
    }

    private List<Direction> loadDirections(Connection conn) throws SQLException {
        List<Direction> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM direction")) {
            while (rs.next()) {
                Direction d = new Direction();
                d.setid(rs.getInt("id"));
                d.setnom(rs.getString("nom"));
                list.add(d);
            }
        }
        return list;
    }
}
