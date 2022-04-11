package uati.CadastrarPIX.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uati.CadastrarPIX.exception.notFoundException;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.projection.consultaPadrao;
import uati.CadastrarPIX.services.chaveService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class chaveRepositoryTest {

    private MockMvc mockMvc;

    @Autowired
    private uati.CadastrarPIX.services.chaveService chaveService;

    @Autowired
    chaveRepository chaveRepository;

    @Test
    public void listarPorIDTest() throws notFoundException {
        chaveModel chave = chaveRepository.findById("749305ac-cce4-4357-b420-0e59aef0a4c4").get();
        assertEquals("749305ac-cce4-4357-b420-0e59aef0a4c4", chave.getId());
        assertNull(chave.getDatainativacao());
        assertEquals("2022-04-11T09:53:13.586238500", chave.getDatainclusao());
        assertEquals("Lucasss", chave.getNome());
        assertEquals(8071, chave.getNumagencia());
        assertEquals(312527, chave.getNumconta());
        assertEquals("Pereira de Camargo", chave.getSobrenome());
        assertEquals("CPF", chave.getTipochave());
        assertEquals("Fisica", chave.getTipocliente());
        assertEquals("Poupança", chave.getTipoconta());
        assertEquals("43605453805", chave.getValorchave());
    }

    @Test
    public void listarPorTipoChaveTest() throws notFoundException{
        List<consultaPadrao> chave = chaveRepository.findByTipoChave("CPF");
        List<String> cpf = chave.stream().map(consultaPadrao::getValorchave).collect(Collectors.toList());
        List<Integer> numagencia = chave.stream().map(consultaPadrao::getNumagencia).collect(Collectors.toList());
        List<Integer> numconta = chave.stream().map(consultaPadrao::getNumconta).collect(Collectors.toList());

        String valor_chave = (cpf.get(0));
        Integer num_agencia = (numagencia.get(0));
        Integer num_conta = (numconta.get(0));

        assertEquals("43605453805",valor_chave);
        assertEquals(String.valueOf(8071),String.valueOf(num_agencia));
        assertEquals(String.valueOf(312527),String.valueOf(num_conta));

    }

    @Test
    public void listarPorAgenciaContaTest() throws notFoundException{
        List<consultaPadrao> chave = chaveRepository.findByAgenciaConta("8071","312527");
        List<String> cpf = chave.stream().map(consultaPadrao::getValorchave).collect(Collectors.toList());
        List<Integer> numagencia = chave.stream().map(consultaPadrao::getNumagencia).collect(Collectors.toList());
        List<Integer> numconta = chave.stream().map(consultaPadrao::getNumconta).collect(Collectors.toList());

        String valor_chave = (cpf.get(0));
        Integer num_agencia = (numagencia.get(0));
        Integer num_conta = (numconta.get(0));

        assertEquals("43605453805",valor_chave);
        assertEquals(String.valueOf(8071),String.valueOf(num_agencia));
        assertEquals(String.valueOf(312527),String.valueOf(num_conta));


    }

    @Test
    public void listarPorNomeTest() throws notFoundException{
        List<consultaPadrao> chave = chaveRepository.findByNome("Lucasss");
        List<String> cpf = chave.stream().map(consultaPadrao::getValorchave).collect(Collectors.toList());
        List<Integer> numagencia = chave.stream().map(consultaPadrao::getNumagencia).collect(Collectors.toList());
        List<Integer> numconta = chave.stream().map(consultaPadrao::getNumconta).collect(Collectors.toList());

        String valor_chave = (cpf.get(0));
        Integer num_agencia = (numagencia.get(0));
        Integer num_conta = (numconta.get(0));

        assertEquals("43605453805",valor_chave);
        assertEquals(String.valueOf(8071),String.valueOf(num_agencia));
        assertEquals(String.valueOf(312527),String.valueOf(num_conta));
    }

}



