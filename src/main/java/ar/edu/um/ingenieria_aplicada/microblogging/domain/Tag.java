package ar.edu.um.ingenieria_aplicada.microblogging.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "etiqueta")
    private String etiqueta;

    @Column(name = "ultimo_uso")
    private Instant ultimoUso;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tag_tagea",
               joinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tageas_id", referencedColumnName = "id"))
    private Set<Publicacion> tageas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Tag etiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
        return this;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Instant getUltimoUso() {
        return ultimoUso;
    }

    public Tag ultimoUso(Instant ultimoUso) {
        this.ultimoUso = ultimoUso;
        return this;
    }

    public void setUltimoUso(Instant ultimoUso) {
        this.ultimoUso = ultimoUso;
    }

    public Set<Publicacion> getTageas() {
        return tageas;
    }

    public Tag tageas(Set<Publicacion> publicacions) {
        this.tageas = publicacions;
        return this;
    }

    public Tag addTagea(Publicacion publicacion) {
        this.tageas.add(publicacion);
        return this;
    }

    public Tag removeTagea(Publicacion publicacion) {
        this.tageas.remove(publicacion);
        return this;
    }

    public void setTageas(Set<Publicacion> publicacions) {
        this.tageas = publicacions;
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
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", etiqueta='" + getEtiqueta() + "'" +
            ", ultimoUso='" + getUltimoUso() + "'" +
            "}";
    }
}
