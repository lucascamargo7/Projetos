package uati.CadastrarPIX.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.repository.chaveRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class chaveServiceUnitTest {

    @InjectMocks
    private chaveService chaveService;

    @Mock
    chaveRepository chaveRepository;

    @Test
    public void quandoIncluirUmaChaveNovaCPF(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("CPF");
        chaveModel.setValorchave("27572751865");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Fisica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        List lista = new ArrayList();
        lista.add(1);

        when(chaveRepository.findByAgenciaAndConta(anyString(),anyString())).thenReturn(lista);

        chaveService.incluirChave(chaveModel);
    }

    @Test
    public void quandoIncluirUmaChaveNovaEMAIL(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("EMAIL");
        chaveModel.setValorchave("lucas@lucas.com.br");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Fisica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        List lista = new ArrayList();
        lista.add(1);

        when(chaveRepository.findByAgenciaAndConta(anyString(),anyString())).thenReturn(lista);

        chaveService.incluirChave(chaveModel);
    }

    @Test
    public void quandoIncluirUmaChaveNovaALEATORIA(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("ALEATORIA");
        chaveModel.setValorchave("");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Fisica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        List lista = new ArrayList();
        lista.add(1);

        when(chaveRepository.findByAgenciaAndConta(anyString(),anyString())).thenReturn(lista);

        chaveService.incluirChave(chaveModel);
    }

    @Test
    public void quandoIncluirUmaChaveNovaCELULAR(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("CELULAR");
        chaveModel.setValorchave("+55015996891646");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Fisica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        List lista = new ArrayList();
        lista.add(1);

        when(chaveRepository.findByAgenciaAndConta(anyString(),anyString())).thenReturn(lista);

        chaveService.incluirChave(chaveModel);
    }

    @Test
    public void quandoIncluirUmaChaveNovaCNPJ(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("CNPJ");
        chaveModel.setValorchave("06.370.309/0001-50");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Juridica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        List lista = new ArrayList();
        lista.add(1);

        when(chaveRepository.findByAgenciaAndConta(anyString(),anyString())).thenReturn(lista);

        chaveService.incluirChave(chaveModel);
    }

    @Test
    public void quandoDeletarUmaChave(){
        chaveModel chaveModel = new chaveModel();
        when(chaveRepository.findById(any())).thenReturn(Optional.of(chaveModel));
        chaveService.deletarChave(any());
    }

    @Test
    public void quandoAlterarUmaChave(){
        chaveModel chaveModel = new chaveModel();
        chaveModel.setNome("Lucas");
        chaveModel.setSobrenome("Pereira de Camargo");
        chaveModel.setTipochave("CPF");
        chaveModel.setValorchave("27572751865");
        chaveModel.setTipoconta("Corrente");
        chaveModel.setTipocliente("Fisica");
        chaveModel.setDatainclusao("10/04/2022");
        chaveModel.setNumagencia(8072);
        chaveModel.setNumconta(312526);

        when(chaveRepository.findById(any())).thenReturn(Optional.of(chaveModel));
        chaveService.alterarChave("String",chaveModel);
    }

    @Test
    public void deveRetornarChavePorID(){
        chaveModel chaveModel = new chaveModel();
        when(chaveRepository.findById(any())).thenReturn(Optional.of(chaveModel));
        chaveService.listarPorID(any());

    }

    @Test
    public void deveListarPorTipodeChave(){
        List lista = new ArrayList();
        lista.add(1);
        when(chaveRepository.findByTipoChave(any())).thenReturn(lista);
        chaveService.listarPorTipoChave(any());
    }

    @Test
    public void develistarPorAgenciaConta(){
        List lista = new ArrayList();
        lista.add(1);
        when(chaveRepository.findByAgenciaConta(any(),any())).thenReturn(lista);
        chaveService.listarPorAgenciaConta(any(),any());
    }

    @Test
    public void develistarPorNome(){
        List lista = new ArrayList();
        lista.add(1);
        when(chaveRepository.findByNome(any())).thenReturn(lista);
        chaveService.listarPorNome(any());
    }

//    @Test
//    public void deveValidarChaveInexistente(){
//        //when(chaveRepository.findAllByvalorchave(any())).thenReturn();
//        chaveService.validaChaveExistente(any());
//    }


}
