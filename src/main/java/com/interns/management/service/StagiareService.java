package com.interns.management.service;

import com.interns.management.model.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StagiareService {

    private final DataSource dataSource;

    public StagiareService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Stagiare> findAll() throws SQLException {
        String query = """
                SELECT stagiare.id, stagiare.nom, stagiare.prenom, stagiare.etat, direction.nom, categorie.nom
                FROM stagiare
                INNER JOIN categorie ON stagiare.idcat = categorie.id
                INNER JOIN direction ON stagiare.iddir = direction.id
                """;
        List<Stagiare> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Stagiare s = new Stagiare();
                s.setid(rs.getInt("stagiare.id"));
                s.setnom(rs.getString("stagiare.nom"));
                s.setprenom(rs.getString("stagiare.prenom"));
                s.setcategorie(rs.getString("categorie.nom"));
                s.setdirection(rs.getString("direction.nom"));
                s.setetat(rs.getString("stagiare.etat"));
                list.add(s);
            }
        }
        return list;
    }

    public Stagiare findById(int id) throws SQLException {
        String query = """
                SELECT * FROM stagiare
                INNER JOIN categorie ON categorie.id = stagiare.idcat
                INNER JOIN niveaustage ON niveaustage.id = stagiare.idniv
                INNER JOIN direction ON direction.id = stagiare.iddir
                WHERE stagiare.id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Stagiare s = mapFullStagiare(rs);
                    if (rs.getString("stagiare.travail_demander") == null) {
                        s.settravail("Rien");
                    } else {
                        loadTravailInfo(conn, id, s);
                    }
                    return s;
                }
            }
        }
        return new Stagiare();
    }

    private void loadTravailInfo(Connection conn, int id, Stagiare s) throws SQLException {
        String q = """
                SELECT stage.titre, encadreur.nom FROM stage
                INNER JOIN stagiare ON stage.id = stagiare.travail_demander
                INNER JOIN encadreur ON stagiare.idencad = encadreur.id
                WHERE stagiare.id = ?
                """;
        try (PreparedStatement ps2 = conn.prepareStatement(q)) {
            ps2.setInt(1, id);
            try (ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) {
                    s.settravail(rs2.getString("stage.titre"));
                    s.setencadreur(rs2.getString("encadreur.nom"));
                }
            }
        }
    }

    private Stagiare mapFullStagiare(ResultSet rs) throws SQLException {
        Stagiare s = new Stagiare();
        s.setid(rs.getInt("stagiare.id"));
        s.setnom(rs.getString("stagiare.nom"));
        s.setprenom(rs.getString("stagiare.prenom"));
        s.setemail(rs.getString("stagiare.email"));
        s.setcategorie(rs.getString("categorie.nom"));
        s.setniveau(rs.getString("niveaustage.nom"));
        s.setdirection(rs.getString("direction.nom"));
        s.setetabalissement(rs.getString("stagiare.etabalissement"));
        s.setetat(rs.getString("stagiare.etat"));
        s.setevaluation(rs.getString("stagiare.evaluation"));
        return s;
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM stagiare WHERE stagiare.id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public record AddFormData(Categorie categorie, Direction direction, List<Niveau> niveaux) {}

    public AddFormData loadAddForm(Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Categorie cat = loadCategorieForAdmin(conn, admin.getcategorie());
            Direction dir = loadDirectionForAdmin(conn, admin.getdirection());
            List<Niveau> niveaux = loadAllNiveaux(conn);
            return new AddFormData(cat, dir, niveaux);
        }
    }

    public void create(Stagiare stagiare, int catId, int nivId, int dirId) throws SQLException {
        String query = "INSERT INTO stagiare(nom,prenom,email,idcat,idniv,iddir,etabalissement) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, stagiare.getnom());
            ps.setString(2, stagiare.getprenom());
            ps.setString(3, stagiare.getemail());
            ps.setInt(4, catId);
            ps.setInt(5, nivId);
            ps.setInt(6, dirId);
            ps.setString(7, stagiare.getetabalissement());
            ps.executeUpdate();
        }
    }

    public record EditFormData(Stagiare stagiare, Categorie categorie, Direction direction, List<Niveau> niveaux) {}

    public EditFormData loadEditForm(int id, Admin admin) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Stagiare s = loadStagiareForEdit(conn, id);
            Categorie cat = loadCategorieForAdmin(conn, admin.getcategorie());
            Direction dir = loadDirectionForAdmin(conn, admin.getdirection());
            List<Niveau> niveaux = loadAllNiveaux(conn);
            return new EditFormData(s, cat, dir, niveaux);
        }
    }

    public void update(Stagiare stagiare, int nivId) throws SQLException {
        String query = "UPDATE stagiare SET nom=?, prenom=?, email=?, iddir=?, idniv=?, etabalissement=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, stagiare.getnom());
            ps.setString(2, stagiare.getprenom());
            ps.setString(3, stagiare.getemail());
            ps.setString(4, stagiare.getdirection());
            ps.setInt(5, nivId);
            ps.setString(6, stagiare.getetabalissement());
            ps.setInt(7, stagiare.getid());
            ps.executeUpdate();
        }
    }

    private Stagiare loadStagiareForEdit(Connection conn, int id) throws SQLException {
        String query = """
                SELECT stagiare.id, stagiare.nom, stagiare.prenom, stagiare.email, stagiare.etabalissement,
                       categorie.nom, niveaustage.nom, direction.nom
                FROM stagiare
                INNER JOIN categorie ON categorie.id = stagiare.idcat
                INNER JOIN niveaustage ON niveaustage.id = stagiare.idniv
                INNER JOIN direction ON direction.id = stagiare.iddir
                WHERE stagiare.id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Stagiare s = new Stagiare();
                if (rs.next()) {
                    s.setid(rs.getInt("stagiare.id"));
                    s.setnom(rs.getString("stagiare.nom"));
                    s.setprenom(rs.getString("stagiare.prenom"));
                    s.setemail(rs.getString("stagiare.email"));
                    s.setdirection(rs.getString("direction.nom"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    s.setniveau(rs.getString("niveaustage.nom"));
                    s.setetabalissement(rs.getString("stagiare.etabalissement"));
                }
                return s;
            }
        }
    }

    private Categorie loadCategorieForAdmin(Connection conn, String nom) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM categorie WHERE categorie.nom = ?")) {
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

    private Direction loadDirectionForAdmin(Connection conn, String nom) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM direction WHERE direction.nom = ?")) {
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

    private List<Niveau> loadAllNiveaux(Connection conn) throws SQLException {
        List<Niveau> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM niveaustage")) {
            while (rs.next()) {
                Niveau n = new Niveau();
                n.setid(rs.getInt("id"));
                n.setnom(rs.getString("nom"));
                list.add(n);
            }
        }
        return list;
    }
}
