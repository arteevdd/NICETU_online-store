package test.project.onlineshop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.project.onlineshop.entity.Producer;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@SpringBootTest
@DisplayName("CRUD - methods: Producer")
class ProducerRepositoryTest {

    @Autowired
    private ProducerRepository producerRepository;

    private static final Producer testProducer = new Producer("test_producer");

    private static final Integer ID = 1;

    @Test
    @DisplayName("Select producer by producer_id")
    void findProducerByProducerId() {
        Optional<Producer> producer = producerRepository.findProducerByProducerId(ID);
        producer.ifPresent(value -> assertEquals("Apple", value.getNameProducer()));
    }

    @Test
    @DisplayName("Select all producers")
    void findAll() {
        List<Producer> producers = (List<Producer>) producerRepository.findAll();
        producerRepository.save(testProducer);
        assertNotEquals(producers.size(), ((List<Producer>) producerRepository.findAll()).size());
    }

    @Test
    @DisplayName("Update name_producer by producer_id")
    void updateNameProducerByProducerId() {
        producerRepository.updateNameProducerByProducerId(ID, testProducer.getNameProducer());
        Optional<Producer> producer = producerRepository.findProducerByProducerId(ID);
        producer.ifPresent(value -> assertEquals(testProducer.getNameProducer(), value.getNameProducer()));
    }

    @Test
    @DisplayName("Remove producer by producer_id")
    void deleteProducerByProducerId() {
        producerRepository.save(testProducer);
        assertNotEquals(Optional.empty(), producerRepository.findProducerByNameProducer(testProducer.getNameProducer()));
        producerRepository.deleteProducerByProducerId(producerRepository.findProducerByNameProducer(testProducer.getNameProducer()).get().getProducerId());
        Optional<Producer> producer = producerRepository.findProducerByNameProducer(testProducer.getNameProducer());
        assertEquals(Optional.empty(), producer);
    }

    @Test
    @DisplayName("Save new producer")
    void addNewProducer() {
        Optional<Producer> emptyProducer = producerRepository.findProducerByNameProducer(testProducer.getNameProducer());
        assertEquals(Optional.empty(), emptyProducer);
        producerRepository.save(testProducer);
        Optional<Producer> newProducer = producerRepository.findProducerByNameProducer(testProducer.getNameProducer());
        newProducer.ifPresent(value -> assertEquals(testProducer.getNameProducer(), value.getNameProducer()));
    }
}