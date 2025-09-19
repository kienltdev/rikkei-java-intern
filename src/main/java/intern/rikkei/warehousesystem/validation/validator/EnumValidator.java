package intern.rikkei.warehousesystem.validation.validator;

import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> allowedValues;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.ignoreCase = constraintAnnotation.ignoreCase();


        Stream<? extends Enum<?>> enumStream = Stream.of(constraintAnnotation.enumClass().getEnumConstants());

        if (this.ignoreCase) {
            allowedValues = enumStream.map(e -> e.name().toUpperCase()).collect(Collectors.toSet());
        } else {
            allowedValues = enumStream.map(Enum::name).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String valueToCheck = ignoreCase ? value.toUpperCase() : value;
        return allowedValues.contains(valueToCheck);
    }
}
