package api.webservices.inredd.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.repository.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Long id, Group group) {
        Group groupSaved = findGroupById(id);
        BeanUtils.copyProperties(group, groupSaved, "id");
        return groupRepository.save(groupSaved);
    }

    public Group findGroupById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public List<UserDTO> findUsersByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        return group.getUsers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
	
}
