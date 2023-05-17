package com.company.repository;

import com.company.entity.Project;
import com.company.entity.Task;
import com.company.entity.User;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode=?1 AND t.taskStatus='COMPLETE'")
    int totalCompletedTasks(String projectCode);
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode=?1 AND t.taskStatus<>'COMPLETE'")
    int totalNonCompletedTasks(String projectCode);

    List<Task> findAllByProject(Project project); //in derived queries we can pass entities
    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User user);
    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User user);

}
