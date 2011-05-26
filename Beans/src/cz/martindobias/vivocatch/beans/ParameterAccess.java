package cz.martindobias.vivocatch.beans;

public enum ParameterAccess {
    ANONYMOUS("anonymous"),
    VIEWER("viewer"),
    ADMIN("admin");

    private final String access;

    ParameterAccess(String access) {
        this.access = access;
    }
}
