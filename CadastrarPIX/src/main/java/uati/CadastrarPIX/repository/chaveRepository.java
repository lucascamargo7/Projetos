package uati.CadastrarPIX.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.projection.consultaPadrao;
import uati.CadastrarPIX.projection.consultaQTD;

import java.util.List;
import java.util.Optional;

public interface chaveRepository extends JpaRepository<chaveModel, String > {

    List<chaveModel> findAllByvalorchave(String valorchave);
    //List<chaveModel> findAllBytipochave(String tipochave);

    @Query(nativeQuery = true, value = "select id as quantidade from chaves" +
            " where num_agencia = :agencia and num_conta = :conta")
    List<consultaQTD>findByAgenciaAndConta(String agencia, String conta);


    @Query(nativeQuery = true, value = "select id, tipo_chave as tipochave, valor_chave as valorchave, tipo_conta as tipoconta, " +
            "num_agencia as numagencia, num_conta as numconta, nome, sobrenome, data_inclusao as datainclusao, data_inativacao as datainativacao" +
            " from chaves where tipo_chave = :cpf")
    List<consultaPadrao>findByTipoChave(String cpf );

    @Query(nativeQuery = true, value = "select id, tipo_chave as tipochave, valor_chave as valorchave, tipo_conta as tipoconta, " +
            "num_agencia as numagencia, num_conta as numconta, nome, sobrenome, data_inclusao as datainclusao, data_inativacao as datainativacao" +
            " from chaves where num_agencia =:agencia and num_conta = :conta")
    List<consultaPadrao>findByAgenciaConta(String agencia, String conta );

    @Query(nativeQuery = true, value = "select id, tipo_chave as tipochave, valor_chave as valorchave, tipo_conta as tipoconta, " +
            "num_agencia as numagencia, num_conta as numconta, nome, sobrenome, data_inclusao as datainclusao, data_inativacao as datainativacao" +
            " from chaves where nome = :nome")
    List<consultaPadrao>findByNome(String nome );

}
