package intern.rikkei.warehousesystem.validation.validator;

import intern.rikkei.warehousesystem.enums.ShippingMethod;
import intern.rikkei.warehousesystem.validation.annotation.ValidShippingMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ShippingMethodValidator implements ConstraintValidator<ValidShippingMethod, String> {
    private Set<String> allowedValues;
    @Override
    public void initialize(ValidShippingMethod constraintAnnotation) {
        allowedValues = Arrays.stream(ShippingMethod.values()).map(ShippingMethod::getCode)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }

        return allowedValues.contains(value.toUpperCase());


    }
}
