package test.project.onlineshop.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import test.project.onlineshop.entity.Producer;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProducerRepository extends CrudRepository<Producer, Integer> {

    Optional<Producer> findProducerByProducerId(Integer producerId);

    Iterable<Producer> findAll();

    Optional<Producer> findProducerByNameProducer(String nameProducer);

    @Transactional
    @Modifying
    @Query("UPDATE Producer " +
            "SET nameProducer = :nameProducer " +
            "WHERE producerId = :producerId")
    void updateNameProducerByProducerId(@Param("producerId") Integer producerId,
                                    @Param("nameProducer") String nameProducer);

    @Transactional
    void deleteProducerByProducerId(Integer producerId);
}
