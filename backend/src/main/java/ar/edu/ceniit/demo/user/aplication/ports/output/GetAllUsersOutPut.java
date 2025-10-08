package ar.edu.ceniit.demo.user.aplication.ports.output;

import ar.edu.ceniit.demo.user.aplication.entitys.objects.PagedResult;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.SortOrder;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.User;
import ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria.Criteria;

public interface GetAllUsersOutPut {
    PagedResult<User> getAllUsers(Criteria criteria, SortOrder sortOrder, int limit, int offset);
}
