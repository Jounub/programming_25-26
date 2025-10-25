package ru.urfu.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.CourseProject.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
