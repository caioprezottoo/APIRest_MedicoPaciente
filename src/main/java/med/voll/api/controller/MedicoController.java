package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// expor serviços via HTTP que retornam dados (JSON
@RequestMapping("medicos")
// define que todos os métodos dessa classe responderão a URLs que começam com /medicos
public class MedicoController {

    @Autowired
    // cria automaticamente uma instância de MedicoRepository para não ter que instanciar manualmente
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    // gerenciar transações do banco de dados de forma automática, se uma parte falhar, tudo é desfeito
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        // @RequestBody - mapear o corpo de uma requisição HTTP
        // @Valid - Verifica se os campos em dados obedecem às regras e se alguma regra for violada, retorna automaticamente uma resposta com HTTP 400

        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        // o metodo .findAll() nao esta definido dentro de MedicoRepository mas herda do JpaRepository
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        // @PathVariable - usado quando você quer que parte da URL seja um parâmetro dinâmico
        // repository.deleteById(id);

        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}