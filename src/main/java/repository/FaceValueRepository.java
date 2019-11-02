package repository;

import model.FaceValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceValueRepository extends JpaRepository<FaceValue, Long> {
    FaceValue findByName(String faceValue);
}
