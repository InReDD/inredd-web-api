package api.webservices.inredd.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.webservices.inredd.domain.model.dto.UserDTO;
import api.webservices.inredd.domain.model.dto.GroupCreateDTO;
import api.webservices.inredd.domain.model.dto.GroupDetailDTO;
import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.Permission;
import api.webservices.inredd.domain.model.Group;
import api.webservices.inredd.repository.GroupRepository;
import api.webservices.inredd.repository.PermissionRepository;
import api.webservices.inredd.service.exception.GroupNotEmptyException;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * Retorna detalhes completos de um grupo (id, name, description + lista de membros).
    */
    public GroupDetailDTO getGroupDetail(Long groupId) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + groupId));
        return new GroupDetailDTO(group);
    }

    @Transactional
    public Group createGroup(GroupCreateDTO dto) {
        // carrega todas as permissões da lista
        List permissions = permissionRepository.findAllById(dto.getPermissionIds());
        if (permissions.size() != dto.getPermissionIds().size()) {
            throw new EntityNotFoundException("Alguma permissão não existe");
        }

        Group g = new Group();
        g.setName(dto.getName());
        g.setDescription(dto.getDescription());
        g.setPermissions(permissions);

        return groupRepository.save(g);
    }

    @Transactional
    public Group updateGroup(Long groupId, GroupCreateDTO dto) {
        Group g = groupRepository.findById(groupId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + groupId));

        // atualiza nome e descrição
        g.setName(dto.getName());
        g.setDescription(dto.getDescription());

        // carrega as novas permissões
        List<Permission> newPerms = permissionRepository.findAllById(dto.getPermissionIds());
        if (newPerms.size() != dto.getPermissionIds().size()) {
            throw new EntityNotFoundException("Alguma permissão não existe");
        }

        // sincroniza a coleção: remove as antigas e adiciona as novas
        g.getPermissions().clear();
        g.getPermissions().addAll(newPerms);

        return groupRepository.save(g);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        Group g = groupRepository.findById(groupId)
            .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado: " + groupId));
    
        if (!g.getUsers().isEmpty()) {
            throw new GroupNotEmptyException(groupId);
        }
    
        groupRepository.delete(g);
    }
	
}
