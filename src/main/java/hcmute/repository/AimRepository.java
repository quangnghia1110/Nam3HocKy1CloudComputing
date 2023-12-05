package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Aim;

@Repository
public interface AimRepository extends JpaRepository<Aim, Long> {
    public Aim findByYear(int year);
}