package test.project.onlineshop.service.producer;

import test.project.onlineshop.entity.Producer;

import java.util.List;

public interface ProducerService {

    Producer findProducerByProducerId(Integer producerId);

    List<Producer> findAll();

    Producer addNewProducer(Producer producer);

    void updateNameProducerByProducerId(Integer producerId, String nameProducer);

    void deleteProducerByProducerId(Integer producerId);
}
