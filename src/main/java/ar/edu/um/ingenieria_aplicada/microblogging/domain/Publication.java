package ar.edu.um.ingenieria_aplicada.microblogging.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Publication.
 */
@Entity
@Table(name = "publication")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "content")
    private String content;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @OneToOne
    @JoinColumn(unique = true)
    private Publication republish;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_mention",
               joinColumns = @JoinColumn(name = "publications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "mentions_id", referencedColumnName = "id"))
    private Set<Publisher> mentions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_faved_by",
               joinColumns = @JoinColumn(name = "publications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "faved_bies_id", referencedColumnName = "id"))
    private Set<Publisher> favedBies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_liked_by",
               joinColumns = @JoinColumn(name = "publications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "liked_bies_id", referencedColumnName = "id"))
    private Set<Publisher> likedBies = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_tag",
               joinColumns = @JoinColumn(name = "publications_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("publications")
    private Publisher publisher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Publication date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public Publication content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Publication visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getCountry() {
        return country;
    }

    public Publication country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Publication city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Publication getRepublish() {
        return republish;
    }

    public Publication republish(Publication publication) {
        this.republish = publication;
        return this;
    }

    public void setRepublish(Publication publication) {
        this.republish = publication;
    }

    public Set<Publisher> getMentions() {
        return mentions;
    }

    public Publication mentions(Set<Publisher> publishers) {
        this.mentions = publishers;
        return this;
    }

    public Publication addMention(Publisher publisher) {
        this.mentions.add(publisher);
        return this;
    }

    public Publication removeMention(Publisher publisher) {
        this.mentions.remove(publisher);
        return this;
    }

    public void setMentions(Set<Publisher> publishers) {
        this.mentions = publishers;
    }

    public Set<Publisher> getFavedBies() {
        return favedBies;
    }

    public Publication favedBies(Set<Publisher> publishers) {
        this.favedBies = publishers;
        return this;
    }

    public Publication addFavedBy(Publisher publisher) {
        this.favedBies.add(publisher);
        return this;
    }

    public Publication removeFavedBy(Publisher publisher) {
        this.favedBies.remove(publisher);
        return this;
    }

    public void setFavedBies(Set<Publisher> publishers) {
        this.favedBies = publishers;
    }

    public Set<Publisher> getLikedBies() {
        return likedBies;
    }

    public Publication likedBies(Set<Publisher> publishers) {
        this.likedBies = publishers;
        return this;
    }

    public Publication addLikedBy(Publisher publisher) {
        this.likedBies.add(publisher);
        return this;
    }

    public Publication removeLikedBy(Publisher publisher) {
        this.likedBies.remove(publisher);
        return this;
    }

    public void setLikedBies(Set<Publisher> publishers) {
        this.likedBies = publishers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Publication tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Publication addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPublications().add(this);
        return this;
    }

    public Publication removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPublications().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Publication publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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
        Publication publication = (Publication) o;
        if (publication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Publication{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", content='" + getContent() + "'" +
            ", visible='" + isVisible() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
