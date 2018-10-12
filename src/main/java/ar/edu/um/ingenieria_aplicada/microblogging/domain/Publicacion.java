package ar.edu.um.ingenieria_aplicada.microblogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Publicacion.
 */
@Entity
@Table(name = "publicacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "visibilidad")
    private Boolean visibilidad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    private String ciudad;

    @ManyToOne
    @JsonIgnoreProperties("publicas")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(unique = true)
    private Publicacion republicacionDe;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publicacion_menciona",
               joinColumns = @JoinColumn(name = "publicacions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "mencionas_id", referencedColumnName = "id"))
    private Set<Usuario> mencionas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publicacion_es_fav",
               joinColumns = @JoinColumn(name = "publicacions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "es_favs_id", referencedColumnName = "id"))
    private Set<Usuario> esFavs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Publicacion fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public Publicacion contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean isVisibilidad() {
        return visibilidad;
    }

    public Publicacion visibilidad(Boolean visibilidad) {
        this.visibilidad = visibilidad;
        return this;
    }

    public void setVisibilidad(Boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getPais() {
        return pais;
    }

    public Publicacion pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Publicacion ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Publicacion usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getRepublicacionDe() {
        return republicacionDe;
    }

    public Publicacion republicacionDe(Publicacion publicacion) {
        this.republicacionDe = publicacion;
        return this;
    }

    public void setRepublicacionDe(Publicacion publicacion) {
        this.republicacionDe = publicacion;
    }

    public Set<Usuario> getMencionas() {
        return mencionas;
    }

    public Publicacion mencionas(Set<Usuario> usuarios) {
        this.mencionas = usuarios;
        return this;
    }

    public Publicacion addMenciona(Usuario usuario) {
        this.mencionas.add(usuario);
        return this;
    }

    public Publicacion removeMenciona(Usuario usuario) {
        this.mencionas.remove(usuario);
        return this;
    }

    public void setMencionas(Set<Usuario> usuarios) {
        this.mencionas = usuarios;
    }

    public Set<Usuario> getEsFavs() {
        return esFavs;
    }

    public Publicacion esFavs(Set<Usuario> usuarios) {
        this.esFavs = usuarios;
        return this;
    }

    public Publicacion addEsFav(Usuario usuario) {
        this.esFavs.add(usuario);
        return this;
    }

    public Publicacion removeEsFav(Usuario usuario) {
        this.esFavs.remove(usuario);
        return this;
    }

    public void setEsFavs(Set<Usuario> usuarios) {
        this.esFavs = usuarios;
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
        Publicacion publicacion = (Publicacion) o;
        if (publicacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Publicacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", visibilidad='" + isVisibilidad() + "'" +
            ", pais='" + getPais() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            "}";
    }
}
