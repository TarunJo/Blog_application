package com.mountblue.spring.blogApplication.repository;


import com.mountblue.spring.blogApplication.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {
}
