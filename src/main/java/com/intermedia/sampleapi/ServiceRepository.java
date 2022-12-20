package com.intermedia.sampleapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {}
