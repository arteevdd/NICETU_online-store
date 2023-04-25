package test.project.onlineshop.service.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.project.onlineshop.entity.Producer;
import test.project.onlineshop.exception.ProducerNotFoundException;
import test.project.onlineshop.repository.ProducerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProducerServiceImpl implements ProducerService{

    @Autowired
    private ProducerRepository producerRepository;
    @Override
    public Producer findProducerByProducerId(Integer producerId) {
        Optional<Producer> producer = producerRepository.findProducerByProducerId(producerId);
        if (producer.isPresent()){
            return producer.get();
        }else {
            throw new ProducerNotFoundException("Producer not found!");
        }
    }

    @Override
    public List<Producer> findAll() {
        return (List<Producer>) producerRepository.findAll();
    }

    @Override
    public Producer addNewProducer(Producer producer) {
        Optional<Producer> check = producerRepository.findProducerByNameProducer(producer.getNameProducer());
        if (check.isPresent()){
            throw new RuntimeException("Producer with name: "+ check.get().getNameProducer() + "is present");
        }else {
            producerRepository.save(producer);
            return producer;
        }
    }

    @Override
    public void updateNameProducerByProducerId(Integer producerId, String nameProducer) {
        producerRepository.updateNameProducerByProducerId(producerId, nameProducer);
    }

    @Override
    public void deleteProducerByProducerId(Integer producerId) {
        producerRepository.deleteProducerByProducerId(producerId);
    }
}
