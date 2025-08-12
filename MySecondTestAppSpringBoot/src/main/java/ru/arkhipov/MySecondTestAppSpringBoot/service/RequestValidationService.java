package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.arkhipov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;

@Service
public class RequestValidationService implements ValidationService{
    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException{
        if(bindingResult.hasErrors()){
            throw new
                    ValidationFailedException(bindingResult.getFieldError().getDefaultMessage());
        }
    }

    @Override
    public void validateFields(Request request) throws ValidationFailedException, UnsupportedCodeException {
        if (request.getUid() == null || request.getUid().isEmpty()) {
            throw new ValidationFailedException("Field 'uid' is required");
        }

        if (request.getOperationUid() == null || request.getOperationUid().isEmpty()) {
            throw new ValidationFailedException("Field 'operationUid' is required");
        }

        if (request.getSystemTime() == null || request.getSystemTime().isEmpty()) {
            throw new ValidationFailedException("Field 'systemTime' is required");
        }

        if("123".equals(request.getUid())){
            throw new UnsupportedCodeException("uid 123 is not allowed");
        }
    }
}
//Добавьте валидацию полей в соответствии с п 1.1 в таблице с описанием request, колонка обязательность.
