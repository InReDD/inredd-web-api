package api.webservices.inredd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import api.webservices.inredd.domain.model.Paper;
import api.webservices.inredd.repository.PaperRepository;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper save(Paper paper) {
        return paperRepository.save(paper);
    }

    public Paper update(Long id, Paper paper) {
        Paper saved = findById(id);
        BeanUtils.copyProperties(paper, saved, "id");
        return paperRepository.save(saved);
    }

    public Paper findById(Long id) {
        return paperRepository.findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
