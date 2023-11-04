package ro.ubbcluj.map.domain.validators;

import ro.ubbcluj.map.domain.Utilizator;
import ro.ubbcluj.map.domain.validators.ValidationException;
import ro.ubbcluj.map.domain.validators.Validator;

import java.util.Objects;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if(entity.getId()<0)
            throw new ValidationException("Id invalid!");
        if(Objects.equals(entity.getFirstName(), ""))
            throw new ValidationException("Prenume invalid!");
        if(Objects.equals(entity.getLastName(), ""))
            throw new ValidationException("Nume invalid!");
    }
}

