package com.example.taskmanagement.Service;

import com.example.taskmanagement.Model.AuditLog;
import com.example.taskmanagement.Repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void log(String entityName, String action, String username, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityName(entityName);
        auditLog.setAction(action);
        auditLog.setUsername(username);
        auditLog.setDetails(details);
        auditLog.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }
}
