package ru.arkhipov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;

@Slf4j
@Service
public class RequestValidationService implements ValidationService{
    @Override
    public void isValid(BindingResult bindingResult, Request request) throws ValidationFailedException, UnsupportedCodeException{
        if(bindingResult.hasErrors()){
            FieldError error = bindingResult.getFieldError();
            String errorMessage = String.format("%s %s", error.getField(), error.getDefaultMessage());
            throw new
                    ValidationFailedException(errorMessage);
        }
        if("123".equals(request.getUid())){
            String errorMessage ="uid 123 недопустим";
            throw new UnsupportedCodeException(errorMessage);
        }
    }
}
//Добавьте валидацию полей в соответствии с п 1.1 в таблице с описанием request, колонка обязательность.
