package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.Vehicle;
import unip.tcc.homecare.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping()
    public ResponseEntity<List<Vehicle>> list() {
        return ResponseEntity.ok(vehicleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(Long id) {
        return ResponseEntity.ok(vehicleRepository.findById(id));
    }
}
