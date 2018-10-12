package ar.edu.um.ingenieria_aplicada.microblogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Publicacion> publicas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "usuario_likea",
               joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "likeas_id", referencedColumnName = "id"))
    private Set<Publicacion> likeas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "usuario_sigue",
               joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sigues_id", referencedColumnName = "id"))
    private Set<Usuario> sigues = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "usuario_bloquea",
               joinColumns = @JoinColumn(name = "usuarios_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "bloqueas_id", referencedColumnName = "id"))
    private Set<Usuario> bloqueas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Publicacion> getPublicas() {
        return publicas;
    }

    public Usuario publicas(Set<Publicacion> publicacions) {
        this.publicas = publicacions;
        return this;
    }

    public Usuario addPublica(Publicacion publicacion) {
        this.publicas.add(publicacion);
        publicacion.setUsuario(this);
        return this;
    }

    public Usuario removePublica(Publicacion publicacion) {
        this.publicas.remove(publicacion);
        publicacion.setUsuario(null);
        return this;
    }

    public void setPublicas(Set<Publicacion> publicacions) {
        this.publicas = publicacions;
    }

    public Set<Publicacion> getLikeas() {
        return likeas;
    }

    public Usuario likeas(Set<Publicacion> publicacions) {
        this.likeas = publicacions;
        return this;
    }

    public Usuario addLikea(Publicacion publicacion) {
        this.likeas.add(publicacion);
        return this;
    }

    public Usuario removeLikea(Publicacion publicacion) {
        this.likeas.remove(publicacion);
        return this;
    }

    public void setLikeas(Set<Publicacion> publicacions) {
        this.likeas = publicacions;
    }

    public Set<Usuario> getSigues() {
        return sigues;
    }

    public Usuario sigues(Set<Usuario> usuarios) {
        this.sigues = usuarios;
        return this;
    }

    public Usuario addSigue(Usuario usuario) {
        this.sigues.add(usuario);
        return this;
    }

    public Usuario removeSigue(Usuario usuario) {
        this.sigues.remove(usuario);
        return this;
    }

    public void setSigues(Set<Usuario> usuarios) {
        this.sigues = usuarios;
    }

    public Set<Usuario> getBloqueas() {
        return bloqueas;
    }

    public Usuario bloqueas(Set<Usuario> usuarios) {
        this.bloqueas = usuarios;
        return this;
    }

    public Usuario addBloquea(Usuario usuario) {
        this.bloqueas.add(usuario);
        return this;
    }

    public Usuario removeBloquea(Usuario usuario) {
        this.bloqueas.remove(usuario);
        return this;
    }

    public void setBloqueas(Set<Usuario> usuarios) {
        this.bloqueas = usuarios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
