package uati.CadastrarPIX.controller;

import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uati.CadastrarPIX.models.alteracaoResponse;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.services.chaveService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = chaveController.class)
public class chaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private uati.CadastrarPIX.services.chaveService chaveService;

    @Test
    public void deveCadastrarNovaChave() throws Exception {
        chaveModel chaveEsperada = new chaveModel();
        chaveEsperada.setNome("Lucas");
        chaveEsperada.setSobrenome("Pereira de Camargo");
        chaveEsperada.setTipochave("CPF");
        chaveEsperada.setValorchave("27572751865");
        chaveEsperada.setTipoconta("Corrente");
        chaveEsperada.setTipocliente("Fisica");
        chaveEsperada.setDatainclusao("10/04/2022");
        chaveEsperada.setNumagencia(8072);
        chaveEsperada.setNumconta(312526);

        when(chaveService.incluirChave(any(chaveModel.class))).thenReturn(chaveEsperada);

        chaveModel chaveASalvar = new chaveModel();
        chaveEsperada.setNome("Lucas");
        chaveEsperada.setSobrenome("Pereira de Camargo");
        chaveEsperada.setTipochave("CPF");
        chaveEsperada.setValorchave("27572751865");
        chaveEsperada.setTipoconta("Corrente");
        chaveEsperada.setTipocliente("Fisica");
        chaveEsperada.setDatainclusao("10/04/2022");
        chaveEsperada.setNumagencia(8072);
        chaveEsperada.setNumconta(312526);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cadastroChave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(chaveASalvar)))
                .andExpect(status().isOk());

    }

    @Test
    public void deveRetornarUsuarioPorID() throws Exception{
        chaveModel chaveEsperada = new chaveModel();
        chaveEsperada.setNome("Lucas");
        chaveEsperada.setSobrenome("Pereira de Camargo");
        chaveEsperada.setTipochave("CPF");
        chaveEsperada.setValorchave("27572751865");
        chaveEsperada.setTipoconta("Corrente");
        chaveEsperada.setTipocliente("Fisica");
        chaveEsperada.setDatainclusao("10/04/2022");
        chaveEsperada.setNumagencia(8072);
        chaveEsperada.setNumconta(312526);

        when(chaveService.listarPorID(any())).thenReturn(chaveEsperada);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cadastroChave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(chaveEsperada)))
                .andExpect(status().isOk());
    }

}



