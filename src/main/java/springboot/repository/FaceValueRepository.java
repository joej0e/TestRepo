package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.model.FaceValue;

@Repository
public interface FaceValueRepository extends JpaRepository<FaceValue, Long> {
    FaceValue findByName(String faceValue);
}
