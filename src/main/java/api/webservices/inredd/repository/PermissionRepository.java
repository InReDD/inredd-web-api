package api.webservices.inredd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.webservices.inredd.domain.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
