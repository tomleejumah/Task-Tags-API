package com.example.taskmanagement.Service;

import com.example.taskmanagement.Model.AuditLog;
import com.example.taskmanagement.Repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * This service class is responsible for handling audit log operations.
 * It provides a method to log audit events related to different entities.
 */
@AllArgsConstructor
@Service
public class AuditLogService {

    /**
     * The repository for storing audit logs.
     */
    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Logs an audit event for a specific entity.
     *
     * @param entityName The name of the entity being audited.
     * @param action The action performed on the entity.
     * @param username The username of the user performing the action.
     * @param details Additional details about the audit event.
     */
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
