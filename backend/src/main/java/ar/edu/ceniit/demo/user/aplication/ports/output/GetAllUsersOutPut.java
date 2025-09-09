package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;

import java.util.Set;

public interface GetAllUsersOutPut {
    Set<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset);
}
