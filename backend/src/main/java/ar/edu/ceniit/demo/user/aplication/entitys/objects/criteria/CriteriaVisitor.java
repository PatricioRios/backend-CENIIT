package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.field.*;

public interface CriteriaVisitor<T> {
    T visit(AndCriteria criteria);
    T visit(OrCriteria criteria);
    T visit(UserUUIDCriteria criteria);
    T visit(UserNameCriteria criteria);
    T visit(UserLastNameCriteria userLastNameCriteria);
    T visit(UserFirstNameCriteria userFirstNameCriteria);
    T visit(UserEmailCriteria userEmailCriteria);
    T visit(UserDNICriteria userDNICriteria);
    T visit(UserCreatedAtCriteria userCreatedAtCriteria);
    T visit(UserUpdatedAtCriteria userUpdatedAtCriteria);
}
