package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
