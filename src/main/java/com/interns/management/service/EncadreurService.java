package com.interns.management.service;

import com.interns.management.model.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EncadreurService {

    private final DataSource dataSource;

    public EncadreurService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Encadreur> findAll() throws SQLException {
        String query = """
                SELECT encadreur.id, encadreur.nom, encadreur.prenom, categorie.nom, direction.nom
                FROM encadreur
                INNER JOIN categorie ON encadreur.idcat = categorie.id
                INNER JOIN direction ON encadreur.iddir = direction.id
                """;
        List<Encadreur> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapListRow(rs));
            }
        }
        return list;
    }

    public record ProfileView(Encadreur encadreur, Optional<Integer> suivi) {}

    public ProfileView findProfile(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM encadreur INNER JOIN categorie ON categorie.id = encadreur.idcat " +
                             "INNER JOIN direction ON direction.id = encadreur.iddir WHERE encadreur.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Encadreur e = new Encadreur();
                Optional<Integer> suivi = Optional.empty();
                if (rs.next()) {
                    e.setid(rs.getInt("encadreur.id"));
                    e.setnom(rs.getString("encadreur.nom"));
                    e.setprenom(rs.getString("encadreur.prenom"));
                    e.setcategorie(rs.getString("categorie.nom"));
                    e.setemail(rs.getString("encadreur.email"));
                    e.setdirection(rs.getString("direction.nom"));
                    if (rs.getInt("encadreur.suivi") == 1) {
                        suivi = Optional.of(1);
                    }
                }
                return new ProfileView(e, suivi);
            }
        }
    }

    public record AddFormData(Categorie categorie, Direction direction) {}

    public AddFormData loadAddForm(Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return new AddFormData(loadCategorie(conn, admin.getcategorie()), loadDirection(conn, admin.getdirection()));
        }
    }

    public void create(Encadreur e, int catId) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO encadreur(nom,prenom,email,idcat,iddir) VALUES(?,?,?,?,?)")) {
            ps.setString(1, e.getnom());
            ps.setString(2, e.getprenom());
            ps.setString(3, e.getemail());
            ps.setInt(4, catId);
            ps.setString(5, e.getdirection());
            ps.executeUpdate();
        }
    }

    public record EditFormData(Encadreur encadreur, Categorie categorie, Direction direction) {}

    public EditFormData loadEditForm(int id, Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Encadreur e = loadEncadreur(conn, id);
            return new EditFormData(e, loadCategorie(conn, admin.getcategorie()), loadDirection(conn, admin.getdirection()));
        }
    }

    public void update(Encadreur e) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE encadreur SET nom=?, prenom=?, email=? WHERE id=?")) {
            ps.setString(1, e.getnom());
            ps.setString(2, e.getprenom());
            ps.setString(3, e.getemail());
            ps.setInt(4, e.getid());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM encadreur WHERE encadreur.id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Encadreur mapListRow(ResultSet rs) throws SQLException {
        Encadreur s = new Encadreur();
        s.setid(rs.getInt("encadreur.id"));
        s.setnom(rs.getString("encadreur.nom"));
        s.setprenom(rs.getString("encadreur.prenom"));
        s.setcategorie(rs.getString("categorie.nom"));
        s.setdirection(rs.getString("direction.nom"));
        return s;
    }

    private Encadreur loadEncadreur(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT encadreur.id, encadreur.nom, direction.nom, encadreur.prenom, encadreur.email, categorie.nom " +
                        "FROM encadreur INNER JOIN categorie ON categorie.id = encadreur.idcat " +
                        "INNER JOIN direction ON direction.id = encadreur.iddir WHERE encadreur.id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Encadreur e = new Encadreur();
                if (rs.next()) {
                    e.setid(rs.getInt("encadreur.id"));
                    e.setnom(rs.getString("encadreur.nom"));
                    e.setprenom(rs.getString("encadreur.prenom"));
                    e.setemail(rs.getString("encadreur.email"));
                    e.setcategorie(rs.getString("categorie.nom"));
                    e.setdirection(rs.getString("direction.nom"));
                }
                return e;
            }
        }
    }

    private Categorie loadCategorie(Connection conn, String nom) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM categorie WHERE nom = ?")) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                Categorie c = new Categorie();
                if (rs.next()) {
                    c.setid(rs.getInt("id"));
                    c.setnom(rs.getString("nom"));
                }
                return c;
            }
        }
    }

    private Direction loadDirection(Connection conn, String nom) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM direction WHERE nom = ?")) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                Direction d = new Direction();
                if (rs.next()) {
                    d.setid(rs.getInt("id"));
                    d.setnom(rs.getString("nom"));
                }
                return d;
            }
        }
    }
}
