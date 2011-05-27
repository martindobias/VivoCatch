package cz.martindobias.vivocatch.beans;

public enum ParameterAccess {
    ANONYMOUS("anonymous"),
    VIEWER("viewer"),
    ADMIN("admin");

    private final String access;

    ParameterAccess(String access) {
        this.access = access;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return this.access;
    }
}
