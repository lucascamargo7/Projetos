package uati.CadastrarPIX.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "chaves")
public class chaveModel implements Serializable {

    @Getter
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "tipo_chave", nullable = false)
    @Getter @Setter
    private String tipochave;

    @Column(name = "valor_chave", nullable = false)
    @Getter @Setter
    private String valorchave;

    @Column(name = "tipo_cliente")
    @Getter @Setter
    private String tipocliente;

    @Column(name = "tipo_conta", nullable = false)
    @Getter @Setter
    private String tipoconta;

    @Column(name = "num_conta", nullable = false)
    @Getter @Setter
    private int numconta;

    @Column(name = "num_agencia", nullable = false)
    @Getter @Setter
    private int numagencia;

    @Column(name = "nome", nullable = false)
    @Getter @Setter
    private String nome;

    @Column(name = "sobrenome")
    @Getter @Setter
    private String sobrenome;

    @Column(name = "data_inclusao")
    @Getter @Setter
    private String datainclusao;

    @Column(name = "data_inativacao")
    @Getter @Setter
    private String datainativacao;


}
