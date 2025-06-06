package med.voll.api.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    //Ao definir as interfaces ...Repository o Spring já faz automaticamente a função do DAO.
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
