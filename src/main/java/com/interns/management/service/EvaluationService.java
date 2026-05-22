package com.interns.management.service;

import com.interns.management.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvaluationService {

    private final DataSource dataSource;

    public EvaluationService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Stage> findStagesForEvaluation(String categorie) throws SQLException {
        String query = """
                SELECT stage.id, stage.titre, categorie.nom
                FROM stage INNER JOIN categorie ON stage.idcat = categorie.id
                WHERE categorie.nom = ? AND stage.etat='effectué'
                """;
        List<Stage> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stage s = new Stage();
                    s.setid(rs.getInt("stage.id"));
                    s.setitret(rs.getString("stage.titre"));
                    s.setcategorie(rs.getString("categorie.nom"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public record EvaluationModalData(List<Stagiare> stagiares, Encadreur encadreur, Stage dates) {}

    public EvaluationModalData loadModalData(int stageId) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<Stagiare> list = new ArrayList<>();
            Encadreur e = new Encadreur();
            String query = "SELECT * FROM stagiare INNER JOIN encadreur ON encadreur.id=stagiare.idencad WHERE travail_demander = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, stageId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Stagiare s = new Stagiare();
                        s.setid(rs.getInt("stagiare.id"));
                        s.setnom(rs.getString("Stagiare.nom"));
                        s.setprenom(rs.getString("Stagiare.prenom"));
                        s.settravail("" + stageId);
                        list.add(s);
                        e.setid(rs.getInt("encadreur.id"));
                        e.setnom(rs.getString("encadreur.nom"));
                        e.setprenom(rs.getString("encadreur.prenom"));
                    }
                }
            }
            Stage stage = new Stage();
            try (PreparedStatement ps = conn.prepareStatement("SELECT date_debut, date_fin FROM stage WHERE stage.id = ?")) {
                ps.setInt(1, stageId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        stage.setdateD(rs.getDate("date_debut"));
                        stage.setdateF(rs.getDate("date_fin"));
                    }
                }
            }
            return new EvaluationModalData(list, e, stage);
        }
    }

    public void submitEvaluation(String[] stagiareIds, String[] stageIds, String evaluation,
                                 MultipartFile travail) throws Exception {
        try (Connection conn = dataSource.getConnection();
             InputStream input = travail.getInputStream()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE stage SET etat='Terminer', travail_livrer=? WHERE stage.id=?")) {
                ps.setBlob(1, input);
                ps.setString(2, stageIds[0]);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT idencad FROM stagiare WHERE id = ?")) {
                ps.setString(1, stagiareIds[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String encadId = rs.getString("idencad");
                        decrementSuivi(conn, encadId);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE stagiare SET etat='fini le travail', evaluation=?, idencad=null WHERE stagiare.id=?")) {
                ps.setString(1, evaluation);
                for (String id : stagiareIds) {
                    ps.setString(2, id);
                    ps.executeUpdate();
                }
            }
        }
    }

    private void decrementSuivi(Connection conn, String encadId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT suivi FROM encadreur WHERE encadreur.id = ?")) {
            ps.setString(1, encadId);
            try (ResultSet rs2 = ps.executeQuery()) {
                if (rs2.next()) {
                    int suivi = rs2.getInt("suivi") - 1;
                    try (PreparedStatement upd = conn.prepareStatement(
                            "UPDATE encadreur SET suivi=? WHERE encadreur.id=?")) {
                        upd.setInt(1, suivi);
                        upd.setString(2, encadId);
                        upd.executeUpdate();
                    }
                }
            }
        }
    }
}
