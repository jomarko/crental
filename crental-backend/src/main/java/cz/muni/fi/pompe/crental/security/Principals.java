package cz.muni.fi.pompe.crental.security;

/**
 * Principals to use in shiro
 * @author Patrik Pompe <325292@mail.muni.cz>
 */
public class Principals {
    protected Long id;
    protected String name;

    public Principals(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
