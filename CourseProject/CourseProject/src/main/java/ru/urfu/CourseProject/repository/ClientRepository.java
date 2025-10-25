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
    //List<Client> findBySurnameContainingIgnoreCase(String surname);

    //List<Client> findByPassportNumber(String passportNumber);

    @Query("SELECT c FROM Client c WHERE :username IS NULL OR c.createdBy = :username")
    List<Client> findByCreatedBy(@Param("username") String username);

    // Для поиска по статусу
    //List<Client> findByStatus(ClientStatus status);

    // Общая статистика по статусам (для ADMIN)
    //@Query("SELECT c.status, COUNT(c) FROM Client c GROUP BY c.status")
    //List<Object[]> getStatusCountOverall();
}
