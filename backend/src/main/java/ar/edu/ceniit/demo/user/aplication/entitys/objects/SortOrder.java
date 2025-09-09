package ar.edu.ceniit.demo.user.aplication.entitys.objects;

public record SortOrder(User.Field fieldName, Order direction) {

    /**
     * Enum que define la dirección del ordenamiento.
     */
    public enum Order {
        /** Ordenar de menor a mayor. */
        ASCENDENTE,
        /** Ordenar de mayor a menor. */
        DESCENDENTE,
        /** Sin ordenar. */
        UNSORTED
    }

    /**
     * Método de fábrica para crear un ordenamiento ascendente.
     * @param field El nombre del campo.
     * @return Una nueva instancia de SortOrder.
     */
    public static SortOrder asc(User.Field field) {
        return new SortOrder(field, Order.ASCENDENTE);
    }

    /**
     * Método de fábrica para crear un ordenamiento descendente.
     * @param field El nombre del campo.
     * @return Una nueva instancia de SortOrder.
     */
    public static SortOrder desc(User.Field field) {
        return new SortOrder(field, Order.DESCENDENTE);
    }

    /**
     * Método de fábrica para indicar que no se requiere ordenamiento.
     * @return Una instancia de SortOrder sin configuración de orden.
     */
    public static SortOrder unsorted() {
        return new SortOrder(null, Order.UNSORTED);
    }
}
