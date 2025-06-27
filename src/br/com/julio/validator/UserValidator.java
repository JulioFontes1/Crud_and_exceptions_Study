package br.com.julio.validator;

import br.com.julio.exception.ValidatorException;
import br.com.julio.model.UserModel;

public class UserValidator {

    private UserValidator(){

    }

    public static void verifyModel(final UserModel model) throws ValidatorException {
        if(stringIsBlank(model.getName()))
            throw new ValidatorException("Informe um nome válido");
        if(stringIsBlank(model.getEmail()))
            throw new ValidatorException("Informe um E-mail válido");
        if(!model.getEmail().contains("@") || !model.getEmail().contains("."))
            throw new ValidatorException("Informe um E-mail válido");
    }

    private static boolean stringIsBlank(final String value){
        return value == null || value.isBlank();
    }
}
