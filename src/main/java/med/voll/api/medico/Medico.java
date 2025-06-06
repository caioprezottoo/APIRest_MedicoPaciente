package med.voll.api.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
// indicar que a classe Java é uma entidade JPA
@Getter
@NoArgsConstructor
// gera um construtor sem argumentos, para o JPA funcionar corretamente (ele precisa de um construtor vazio para instanciar entidades via reflexão)
@AllArgsConstructor
// gera um construtor com todos os atributos da classe como argumentos
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Id - indica que o campo é a chave primária da entidade
    // @GeneratedValue - diz ao JPA que o valor do ID será gerado automaticamente pelo banco
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    // usada para mapear um campo enum em uma entidade para uma coluna do banco de dados
    private Especialidade especialidade;

    @Embedded
    // quando o JPA for gerar a tabela medicos, ele vai criar as colunas do objeto Endereco dentro da tabela
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {

        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
