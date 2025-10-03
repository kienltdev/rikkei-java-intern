package intern.rikkei.warehousesystem.validation.validator;

import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> allowedValues;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.ignoreCase = constraintAnnotation.ignoreCase();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        try {
            Method getCodeMethod = enumClass.getMethod("getCode");

            allowedValues = Stream.of(enumClass.getEnumConstants())
                    .map(e -> {
                        try {
                            return String.valueOf(getCodeMethod.invoke(e));
                        } catch (Exception ex) {
                            return e.name();
                        }
                    })
                    .map(s -> ignoreCase ? s.toUpperCase() : s)
                    .collect(Collectors.toSet());
        } catch (NoSuchMethodException e) {
            allowedValues = Stream.of(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .map(s -> ignoreCase ? s.toUpperCase() : s)
                    .collect(Collectors.toSet());
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