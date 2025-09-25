package intern.rikkei.warehousesystem.validation.annotation;

import intern.rikkei.warehousesystem.validation.validator.ShippingMethodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ShippingMethodValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidShippingMethod {
    String message() default "{validation.shippingMethod.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
