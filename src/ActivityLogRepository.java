package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.model.ActivityLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityLogRepository {

    private final String filePath;

    public ActivityLogRepository(@Value("${logs.file.path}") String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ActivityLog> findAll() {
        List<ActivityLog> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    logs.add(new ActivityLog(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void save(ActivityLog log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(log.getUsername() + "," + log.getAction() + "," + log.getTimestamp());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
