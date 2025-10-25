package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.CourseProject.entity.DeliveryPrice;

public interface DeliveryPriceRepository extends JpaRepository<DeliveryPrice, Long> {
    DeliveryPrice findByCity(String city);
}
