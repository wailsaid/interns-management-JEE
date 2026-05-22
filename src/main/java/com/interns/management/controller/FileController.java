package com.interns.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileController {

    private static final int BUFFER_SIZE = 14536102;
    private final DataSource dataSource;

    public FileController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/telechegertravail")
    public void download(@RequestParam("q") int id, HttpServletResponse response) throws Exception {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT travail_livrer FROM stage WHERE stage.id=?")) {
            ps.setInt(1, id);
            try (ResultSet result = ps.executeQuery()) {
                if (result.next()) {
                    Blob blob = result.getBlob("travail_livrer");
                    try (InputStream inputStream = blob.getBinaryStream();
                         OutputStream outStream = response.getOutputStream()) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }
                    }
                } else {
                    response.getWriter().print("File not found for the id: " + id);
                }
            }
        }
    }
}
