package org.bsuir.service.Impl;

import lombok.RequiredArgsConstructor;
import org.bsuir.model.Description;
import org.bsuir.repository.DescriptionRepository;
import org.bsuir.service.DescriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DescriptionServiceImpl implements DescriptionService {

    private final DescriptionRepository descriptionRepository;

    @Override
    public Optional<Description> findById(Long id) {
        return descriptionRepository.findById(id);
    }

    @Override
    public Description save(Description entity) {
        return descriptionRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        descriptionRepository.deleteById(id);
    }

    @Override
    public List<Description> findAll() {
        return descriptionRepository.findAll();
    }
}
