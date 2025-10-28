package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.CourseProject.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
