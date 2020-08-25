package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.UserEndereco;

public interface EnderecoRepository extends JpaRepository<UserEndereco, Long> {
}
