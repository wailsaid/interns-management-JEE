package com.interns.management.service;

import com.interns.management.model.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StageService {

    private final DataSource dataSource;

    public StageService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Stage> findAll() throws SQLException {
        String query = """
                SELECT stage.id, stage.titre, stage.description, stage.etat, categorie.nom, niveaustage.nom
                FROM stage
                INNER JOIN categorie ON stage.idcat = categorie.id
                INNER JOIN niveaustage ON stage.idniv = niveaustage.id
                """;
        List<Stage> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapListStage(rs));
            }
        }
        return list;
    }

    public Stage findById(int id) throws SQLException {
        String query = """
                SELECT stage.id, stage.titre, stage.description, stage.etat, categorie.nom, niveaustage.nom,
                       stage.date_debut, stage.date_fin
                FROM stage
                INNER JOIN categorie ON stage.idcat = categorie.id
                INNER JOIN niveaustage ON stage.idniv = niveaustage.id
                WHERE stage.id = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Stage profil = new Stage();
                if (rs.next()) {
                    profil.setid(rs.getInt("stage.id"));
                    profil.setitret(rs.getString("titre"));
                    profil.setcategorie(rs.getString("categorie.nom"));
                    profil.setniveau(rs.getString("niveaustage.nom"));
                    profil.setdescription(rs.getString("stage.description"));
                    profil.setetat(rs.getString("stage.etat"));
                    profil.setdateD(rs.getDate("stage.date_debut"));
                    profil.setdateF(rs.getDate("stage.date_fin"));
                }
                return profil;
            }
        }
    }

    public void validate(String id) throws SQLException {
        updateEtat(id, "Valide");
    }

    public void refuse(String id) throws SQLException {
        updateEtat(id, "Refuser");
    }

    private void updateEtat(String id, String etat) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE stage SET etat = ? WHERE id = ?")) {
            stmt.setString(1, etat);
            stmt.setString(2, id);
            stmt.executeUpdate();
        }
    }

    public record ProposeFormData(List<Categorie> categories, List<Niveau> niveaux) {}

    public ProposeFormData loadProposeForm() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return new ProposeFormData(loadCategories(conn), loadNiveaux(conn));
        }
    }

    public void propose(String titre, int catId, int nivId, String description) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO stage (titre, idcat, idniv, description) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, titre);
            stmt.setInt(2, catId);
            stmt.setInt(3, nivId);
            stmt.setString(4, description);
            stmt.executeUpdate();
        }
    }

    public record EditFormData(Stage stage, List<Categorie> categories, List<Niveau> niveaux) {}

    public EditFormData loadEditForm(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Stage s = loadStageForEdit(conn, id);
            return new EditFormData(s, loadCategories(conn), loadNiveaux(conn));
        }
    }

    public void save(int id, String titre, int nivId, String description) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE stage SET titre=?, idniv=?, description=? WHERE id=?")) {
            stmt.setString(1, titre);
            stmt.setInt(2, nivId);
            stmt.setString(3, description);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public record AffecterData(List<Stage> stages, List<Stagiare> stagiares, List<Encadreur> encadreurs) {}

    public AffecterData loadAffecterData(Admin user) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return new AffecterData(
                    loadValidStages(conn, user.getcategorie()),
                    loadWaitingStagiares(conn, user.getcategorie()),
                    loadEncadreurs(conn, user.getcategorie()));
        }
    }

    public void affect(int stageId, String[] stagiareIds, int encadId, String dated, String datef) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE stage SET etat='effectué', date_debut=?, date_fin=? WHERE stage.id=?")) {
                ps.setString(1, dated);
                ps.setString(2, datef);
                ps.setInt(3, stageId);
                ps.executeUpdate();
            }
            String stagiareQuery = "UPDATE stagiare SET etat='travailler en stage', travail_demander=?, idencad=? WHERE stagiare.id=?";
            try (PreparedStatement ps = conn.prepareStatement(stagiareQuery)) {
                for (String s : stagiareIds) {
                    ps.setInt(1, stageId);
                    ps.setInt(2, encadId);
                    ps.setString(3, s);
                    ps.executeUpdate();
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT suivi FROM encadreur WHERE encadreur.id = ?")) {
                ps.setInt(1, encadId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int suivi = rs.getInt("suivi") + 1;
                        try (PreparedStatement upd = conn.prepareStatement(
                                "UPDATE encadreur SET suivi=? WHERE encadreur.id=?")) {
                            upd.setInt(1, suivi);
                            upd.setInt(2, encadId);
                            upd.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    private Stage mapListStage(ResultSet rs) throws SQLException {
        Stage s = new Stage();
        s.setid(rs.getInt("stage.id"));
        s.setitret(rs.getString("titre"));
        s.setcategorie(rs.getString("categorie.nom"));
        s.setniveau(rs.getString("niveaustage.nom"));
        s.setdescription(rs.getString("stage.description"));
        s.setetat(rs.getString("stage.etat"));
        return s;
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

    private List<Niveau> loadNiveaux(Connection conn) throws SQLException {
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

    private Stage loadStageForEdit(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT stage.id, stage.titre, stage.description, categorie.nom, niveaustage.nom " +
                        "FROM stage INNER JOIN categorie ON stage.idcat = categorie.id " +
                        "INNER JOIN niveaustage ON stage.idniv = niveaustage.id WHERE stage.id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Stage s = new Stage();
                if (rs.next()) {
                    s.setid(rs.getInt("stage.id"));
                    s.setitret(rs.getString("stage.titre"));
                    s.setdescription(rs.getString("stage.description"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    s.setniveau(rs.getString("niveaustage.nom"));
                }
                return s;
            }
        }
    }

    private List<Stage> loadValidStages(Connection conn, String categorie) throws SQLException {
        String query = """
                SELECT stage.id, stage.titre, stage.etat, categorie.nom, niveaustage.nom
                FROM stage INNER JOIN categorie ON stage.idcat = categorie.id
                INNER JOIN niveaustage ON niveaustage.id = stage.idniv
                WHERE categorie.nom = ? AND stage.etat = 'Valide'
                """;
        List<Stage> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stage s = new Stage();
                    s.setid(rs.getInt("stage.id"));
                    s.setitret(rs.getString("titre"));
                    s.setniveau(rs.getString("niveaustage.nom"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    s.setetat(rs.getString("stage.etat"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    private List<Stagiare> loadWaitingStagiares(Connection conn, String categorie) throws SQLException {
        String query = """
                SELECT stagiare.id, stagiare.nom, stagiare.prenom, stagiare.etat, categorie.nom, niveaustage.nom
                FROM stagiare INNER JOIN categorie ON stagiare.idcat = categorie.id
                INNER JOIN niveaustage ON niveaustage.id = stagiare.idniv
                WHERE categorie.nom = ? AND stagiare.etat='attendre l affectation'
                """;
        List<Stagiare> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stagiare s = new Stagiare();
                    s.setid(rs.getInt("stagiare.id"));
                    s.setnom(rs.getString("stagiare.nom"));
                    s.setprenom(rs.getString("stagiare.prenom"));
                    s.setniveau(rs.getString("niveaustage.nom"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    s.setetat(rs.getString("stagiare.etat"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    private List<Encadreur> loadEncadreurs(Connection conn, String categorie) throws SQLException {
        String query = "SELECT encadreur.id, encadreur.nom, encadreur.prenom, categorie.nom " +
                "FROM encadreur INNER JOIN categorie ON encadreur.idcat = categorie.id WHERE categorie.nom = ?";
        List<Encadreur> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Encadreur s = new Encadreur();
                    s.setid(rs.getInt("encadreur.id"));
                    s.setnom(rs.getString("encadreur.nom"));
                    s.setprenom(rs.getString("encadreur.prenom"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    list.add(s);
                }
            }
        }
        return list;
    }
}
