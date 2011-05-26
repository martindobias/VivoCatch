package cz.martindobias.vivocatch.beans;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CameraProperty {
    public String name();

    public CameraSubdir subdir();

    public Class type();
}
