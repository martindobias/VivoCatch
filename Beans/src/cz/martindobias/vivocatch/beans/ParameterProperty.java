package cz.martindobias.vivocatch.beans;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterProperty {
    public String name();
    public ParameterAccess subdir();
    public ParameterType type();
}
