package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
