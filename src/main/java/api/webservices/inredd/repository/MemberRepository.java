package api.webservices.inredd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.webservices.inredd.domain.model.User;
import api.webservices.inredd.domain.model.dto.MemberDTO;

public interface MemberRepository extends JpaRepository<User, Long> {

    /**
     * Retorna todos os membros do grupo identificado por :groupId
     */
    @Query(
      "SELECT new api.webservices.inredd.domain.model.dto.MemberDTO(" +
      "  u.firstName, u.lastName, u.email, u.contact, u.active," +
      "  a.title, a.institution, a.lattesId, a.abstractText" +
      ") " +
      "FROM api.webservices.inredd.domain.model.Group g " +
      " JOIN g.users u " +
      " LEFT JOIN u.academic a " +
      "WHERE g.id = :groupId"
    )
    List<MemberDTO> findMembersByGroupId(@Param("groupId") Long groupId);

    /**
     * Retorna todos os membros (projetados em MemberDTO, incluindo dados de Academic)
     * para os grupos identificados em groupIds.
     */
    @Query(
      "SELECT DISTINCT new api.webservices.inredd.domain.model.dto.MemberDTO(" +
      "  u.firstName, u.lastName, u.email, u.contact, u.active," +
      "  a.title, a.institution, a.lattesId, a.abstractText" +
      ") " +
      "FROM api.webservices.inredd.domain.model.Group g " +
      " JOIN g.users u " +
      " LEFT JOIN u.academic a " +
      "WHERE g.id IN :groupIds"
    )
    List<MemberDTO> findMembersByGroupIds(@Param("groupIds") List<Long> groupIds);
}
