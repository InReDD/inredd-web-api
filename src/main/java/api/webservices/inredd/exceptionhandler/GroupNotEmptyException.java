package api.webservices.inredd.service.exception;

public class GroupNotEmptyException extends RuntimeException {
    public GroupNotEmptyException(Long groupId) {
        super("Não é possível deletar o grupo " + groupId + " porque existem usuários associados");
    }
}