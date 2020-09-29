package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.tcc.homecare.model.UserEndereco;

@Repository
public interface EnderecoRepository extends JpaRepository<UserEndereco, Long> {
}
