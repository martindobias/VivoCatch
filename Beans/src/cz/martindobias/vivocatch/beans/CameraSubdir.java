package cz.martindobias.vivocatch.beans;

public enum CameraSubdir {
    ANONYMOUS("anonymous"),
    VIEWER("viewer"),
    ADMIN("admin");

    private final String subdir;

    CameraSubdir(String subdir) {
        this.subdir = subdir;
    }
}
