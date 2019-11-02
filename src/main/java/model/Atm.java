package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "atms")
public class Atm {

    @Column(name = "atm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "atms_face_values", joinColumns = @JoinColumn(name = "atm_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "face_value_id", referencedColumnName = "id"))
    private Set<FaceValue> faceValues = new HashSet<>();

    public Atm(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FaceValue> getFaceValues() {
        return faceValues;
    }

    public void setFaceValues(Set<FaceValue> faceValues) {
        this.faceValues = faceValues;
    }
}
