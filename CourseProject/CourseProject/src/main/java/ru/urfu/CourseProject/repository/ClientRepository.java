package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.urfu.CourseProject.entity.Client;
import ru.urfu.CourseProject.model.ClientStatus;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCreatedBy(String createdBy);

    // Найти клиентов, у которых статус не в указанном списке
    List<Client> findByStatusNotIn(List<ClientStatus> statuses);
}
