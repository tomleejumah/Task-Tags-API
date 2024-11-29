package com.example.taskmanagement.Repository;

import com.example.taskmanagement.Model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
