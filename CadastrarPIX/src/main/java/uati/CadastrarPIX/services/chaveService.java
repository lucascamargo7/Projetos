package uati.CadastrarPIX.services;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uati.CadastrarPIX.exception.applicationExceptionHandler;
import uati.CadastrarPIX.exception.notFoundException;
import uati.CadastrarPIX.exception.unprocessableException;
import uati.CadastrarPIX.models.alteracaoResponse;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.projection.consultaPadrao;
import uati.CadastrarPIX.projection.consultaQTD;
import uati.CadastrarPIX.repository.chaveRepository;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@Component
public class chaveService {

    @Autowired
    chaveRepository chaveRepository;

    //Ok with mockito
    public chaveModel incluirChave(chaveModel chave) {
        if (chave.getTipochave().equals("ALEATORIA")) {
            chave.setValorchave(UUID.randomUUID().toString());
        }

        if (validarChave(chave)) {
            chave.setDatainclusao(LocalDateTime.now().toString());
            return chaveRepository.save(chave);
        }
        return null;

    }

    //Ok with mockito
    public alteracaoResponse alterarChave(String id, chaveModel chave) {
        chaveModel chaveAlterada = processarAlteracao(id, chave);
        chaveRepository.save(chaveAlterada);

        alteracaoResponse alteracaoResponse = new alteracaoResponse();
        BeanUtils.copyProperties(chaveAlterada, alteracaoResponse, "datainativacao");
        return alteracaoResponse;
    }

    private chaveModel processarAlteracao(String id, chaveModel chave) {
        chaveModel chaveAtual = new chaveModel();

        try
        {chaveAtual = chaveRepository.findById(id).get();}
        catch (Exception e)
        {throw new notFoundException();}


        log.info("Verificando se a chave esta inativada");
        if (!(chaveAtual.getDatainativacao() == null)) {
            throw new unprocessableException();
        }

        try {
            BeanUtils.copyProperties(chave, chaveAtual, "id", "tipochave", "valorchave", "tipocliente");
            if (validaConta(chaveAtual)) {
                chaveAtual.setDatainclusao(LocalDateTime.now().toString());
                return chaveAtual;
            }
        } catch (Exception e) {
            throw new unprocessableException();
        }
        throw new unprocessableException();
    }

    //Ok with mockito
    public boolean deletarChave(String id) {
        chaveModel chaveAtual = new chaveModel();

        try
        {
            chaveAtual = chaveRepository.findById(id).get();

            if (chaveAtual.getDatainativacao()==null){
                chaveAtual.setDatainativacao(LocalDateTime.now().toString());
                chaveRepository.save(chaveAtual);
                return true;
            }
            else
            {
                return false;
            }

        }
        catch (Exception e)
        {
            throw new notFoundException();
        }

    }

    //Ok with mockito
    public chaveModel listarPorID (String id){
        chaveModel chave = new chaveModel();
        try {
            chave = chaveRepository.findById(id).get();
            return chave;
        } catch (Exception e){
            throw new notFoundException();
        }
    }

    private boolean validarChave(chaveModel chave) {
        String tipo_chave = chave.getTipochave().toString();
        String tipo_cliente = chave.getTipocliente();

        if(tipo_chave.equals("CPF") && tipo_cliente.equals("Juridica")){
           throw new unprocessableException();
        }

        if(tipo_chave.equals("CNPJ") && tipo_cliente.equals("Fisica")){
            throw new unprocessableException();
        }

        if (!validaQTDChaves(chave)) {
            return false;
        }
        if (!validaChaveExistente(chave.getValorchave())) {
            return false;
        }
        if (!validaTipoEValor(chave)) {
            return false;
        }
        if (!validaConta(chave)) {
            return false;
        }


        return true;
    }

    private boolean validaQTDChaves(chaveModel chave) {
        final List<consultaQTD> chaveModels =
                chaveRepository.findByAgenciaAndConta(Integer.toString(chave.getNumagencia()),
                        Integer.toString(chave.getNumconta()));

        if (chaveModels.size() >= 5 && chave.getTipocliente().toUpperCase().equals("FISICA")) {
            throw new unprocessableException();
        }

        if (chaveModels.size() >= 20 && chave.getTipocliente().toUpperCase().equals("JURIDICA")) {
            throw new unprocessableException();
        }
        return true;
    }

    //Ok with mockito
    public List<consultaPadrao> listarPorTipoChave(String tipo){
        try{
            List<consultaPadrao> listarChave = chaveRepository.findByTipoChave(tipo);
            if(listarChave.size()<=0){
                throw new notFoundException();
            }
            return listarChave;
        } catch (Exception e)
        {
            throw new notFoundException();
        }

    }

    //Ok with mockito
    public List<consultaPadrao> listarPorAgenciaConta(String agencia, String conta){
        try{
            List<consultaPadrao> listarChave = chaveRepository.findByAgenciaConta(agencia, conta);
            if(listarChave.size()<=0){
                throw new notFoundException();
            }
            return listarChave;
        } catch (Exception e)
        {
            throw new notFoundException();
        }
    }

    //Ok with mockito
    public List<consultaPadrao> listarPorNome(String nome){
        try{
            List<consultaPadrao> listarChave = chaveRepository.findByNome(nome);
            if(listarChave.size()<=0){
                throw new notFoundException();
            }
            return listarChave;
        } catch (Exception e)
        {
            throw new notFoundException();
        }

    }



    //Verificar se precisa testar
    private boolean validaChaveExistente(String valorchave) {
        final List<chaveModel> chaveModels = chaveRepository.findAllByvalorchave(valorchave);
        if (chaveModels.size() <= 0) {
            return true;
        }
        throw new unprocessableException();
    }

    private boolean validaTipoEValor(chaveModel chave) {
        String tipo_chave = chave.getTipochave();
        String valor_chave = chave.getValorchave();

        switch (tipo_chave.toUpperCase()) {
            case "CPF":
                if (validaCPF(valor_chave)) {
                    return true;
                }
                break;
            case "CELULAR":
                log.info("Encontrar uma forma de validar o celular");
                if(validaCelular(valor_chave)){
                    return true;
                };
                break;
            case "EMAIL":
                if (validaEmail(valor_chave)) {
                    return true;
                }
                break;
            case "CNPJ":
                if (validaCNPJ(valor_chave)) {
                    return true;
                }
                break;
            case "ALEATORIA":
                return true;
            default:
                throw new unprocessableException();
        }
        return false;
    }

    private boolean validaCPF(String CPF) {
        CPF = CPF.replaceAll("\\D", "");
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11)) {

            System.out.println("CPF FALSO");
            throw new unprocessableException();
        }


        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return true;
            } else {
                throw new unprocessableException();
            }

        } catch (InputMismatchException erro) {
            throw new unprocessableException();
        }
    }

    private boolean validaEmail(String email) {
        boolean isEmailIdValid = false;

        if (email != null && email.length() > 0 && email.length() <= 77) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            } else {
                throw new unprocessableException();
            }
        }

        return isEmailIdValid;
    }

    private boolean validaCNPJ(String CNPJ) {
        CNPJ = CNPJ.replaceAll("\\D", "");
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            throw new unprocessableException();

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else throw new unprocessableException();
        } catch (InputMismatchException erro) {
            throw new unprocessableException();
        }
    }

    private boolean validaConta(chaveModel chave) {
        String nome = chave.getNome();
        String tipo_conta = chave.getTipoconta();
        String tipo_cliente = chave.getTipocliente();
        Integer numagencia = chave.getNumagencia();
        Integer numconta = chave.getNumconta();


        if (nome.trim().isEmpty()) {
            throw new unprocessableException();
        }

        if (tipo_conta.toUpperCase().equals("CORRENTE") || tipo_conta.toUpperCase().equals("POUPANÇA")) {
            log.info("É conta corrente ou poupança");
            if (tipo_cliente.toUpperCase().equals("FISICA") || tipo_cliente.toUpperCase().equals("JURIDICA")) {
                log.info("É cliente pessoa fisica ou juridica");
                if (numagencia > 0 && numagencia.toString().length() <= 4) {
                    log.info("Agencia > 0 e com <= 4 digitos");
                    if (numconta > 0 && numconta.toString().length() <= 8) {
                        log.info("Numero conta >0 e <= 8 digitos");
                        return true;
                    }
                }
            }
        }
        throw new unprocessableException();
    }

    private boolean validaCelular(String celular){
        boolean isNumeric =  celular.matches("[+-]?\\d*(\\.\\d+)?");
        if (isNumeric == false) throw new unprocessableException();;

        log.info(celular.substring(0,1));
        if(!(celular.substring(0,1).equals("+"))){
             throw new unprocessableException();
        }
        log.info(celular.substring(1,3));
        boolean isNumericPais = celular.substring(1,3).matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumericPais == false) throw new unprocessableException();;

        log.info(celular.substring(3,6));
        boolean isNumericDDD = celular.substring(3,6).matches("[+-]?\\d*(\\.\\d+)?");
        if(isNumericDDD == false) throw new unprocessableException();;

        try {
            boolean isNumericNumero = celular.substring(6,15).matches("[+-]?\\d*(\\.\\d+)?");
            if(celular.substring(6,15).length() == 8){
                throw new unprocessableException();
            }
            if(isNumericNumero == false) throw new unprocessableException();;

        } catch (Exception e) {
            throw new unprocessableException();
        }

        celular = celular.replaceAll("\\D", "");
        if (celular.length() > 14) throw new unprocessableException();

        return true;
    }

}





