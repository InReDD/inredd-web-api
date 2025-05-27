package api.webservices.inredd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.repository.PaperRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Transactional
    public Paper save(Paper paper) {
        paper.setCreatedAt(OffsetDateTime.now());

        if (paper.getUsers() == null) {
            paper.setUsers(new ArrayList<>());
        }

        return paperRepository.save(paper);
    }

    @Transactional
    public Paper update(Long id, Paper paper) {
        Paper existingPaper = paperRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paper não encontrado com ID: " + id));

        paper.setId(id);
        paper.setCreatedAt(existingPaper.getCreatedAt());

        if (paper.getUsers() == null || paper.getUsers().isEmpty()) {
            paper.setUsers(existingPaper.getUsers());
        }

        return paperRepository.save(paper);
    }

    @Transactional
    public void delete(Long id) {
        if (!paperRepository.existsById(id)) {
            throw new EntityNotFoundException("Paper não encontrado com ID: " + id);
        }

        paperRepository.deleteById(id);
    }

    public Paper findById(Long id) {
        return paperRepository.findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}