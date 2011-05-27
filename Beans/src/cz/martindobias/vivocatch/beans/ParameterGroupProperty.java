package cz.martindobias.vivocatch.beans;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterGroupProperty {
    public String group();
}
