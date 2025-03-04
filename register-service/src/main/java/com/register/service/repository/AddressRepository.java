package com.register.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.register.service.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
