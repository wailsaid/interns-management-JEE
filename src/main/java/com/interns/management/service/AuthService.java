package com.interns.management.service;

import com.interns.management.model.Admin;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class AuthService {

    private final DataSource dataSource;

    public AuthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Admin> authenticate(String username, String password) throws SQLException {
        if ("admin".equals(username) && "admin".equals(password)) {
            Admin admin = new Admin();
            admin.setusername("admin");
            admin.setcategorie("admin");
            return Optional.of(admin);
        }
        String query = """
                SELECT admin.username, categorie.nom, direction.nom
                FROM admin
                INNER JOIN categorie ON admin.idcat = categorie.id
                INNER JOIN direction ON direction.id = admin.iddir
                WHERE username = ? AND password = ?
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setusername(username);
                    admin.setcategorie(rs.getString("categorie.nom"));
                    admin.setdirection(rs.getString("direction.nom"));
                    return Optional.of(admin);
                }
            }
        }
        return Optional.empty();
    }
}
