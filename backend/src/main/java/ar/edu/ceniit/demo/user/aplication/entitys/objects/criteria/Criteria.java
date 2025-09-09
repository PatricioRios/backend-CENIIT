package ar.edu.ceniit.demo.user.aplication.entitys.objects.criteria;

public interface Criteria {
    <T> T accept(CriteriaVisitor<T> visitor);
}
