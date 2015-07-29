package com.jscomp.jba.service.annotation;

import com.jscomp.jba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername uniqueUsername) {

    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        /*to avoid exception during the InitDbService.init() run*/
        if (userRepository == null) {
            return true;
        }
        return (userRepository.findByName(userName) == null);
    }
}
