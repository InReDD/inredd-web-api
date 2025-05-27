package api.webservices.inredd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.webservices.inredd.domain.model.InviteRequest;

public interface InviteRequestRepository extends JpaRepository<InviteRequest, Long> {
    Optional<InviteRequest> findByInviteToken(String token);
}
