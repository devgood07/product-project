package com.musinsa.product.validation.annotation;

import com.musinsa.product.validation.ActionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ActionValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface ValidAction {
    String message() default "Invalid fields for the given action";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

