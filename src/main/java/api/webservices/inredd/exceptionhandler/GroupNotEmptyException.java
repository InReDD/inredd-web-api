package api.webservices.inredd.exceptionhandler;

public class GroupNotEmptyException extends RuntimeException {
    public GroupNotEmptyException(Long groupId) {
        super("Não é possível deletar o grupo " + groupId + " porque existem usuários associados");
    }
}