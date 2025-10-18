package criteria

// LogicalOperator define los operadores lógicos para agrupar filtros.
type LogicalOperator string

const (
	AND LogicalOperator = "AND"
	OR  LogicalOperator = "OR"
)

// FilterOperator define los operadores para las condiciones de filtro.
type FilterOperator string

const (
	EQUALS         FilterOperator = "EQUALS"
	NOT_EQUALS     FilterOperator = "NOT_EQUALS"
	GREATER_THAN   FilterOperator = "GREATER_THAN"
	GREATER_EQUALS FilterOperator = "GREATER_EQUALS"
	LESS_THAN      FilterOperator = "LESS_THAN"
	LESS_EQUALS    FilterOperator = "LESS_EQUALS"
	CONTAINS       FilterOperator = "CONTAINS"
	NOT_CONTAINS   FilterOperator = "NOT_CONTAINS"
	ISTARTS_WITH   FilterOperator = "ISTARTS_WITH"
	GTE            FilterOperator = "GTE"
	LIKE           FilterOperator = "LIKE"
	ILIKE          FilterOperator = "ILIKE"
)

// SortDirection define la dirección del ordenamiento.
type SortDirection string

const (
	ASC  SortDirection = "ASC"
	DESC SortDirection = "DESC"
)

// Filter representa una condición de filtro individual.
type Filter struct {
	Field    string         `json:"field"`
	Operator FilterOperator `json:"operator"`
	Value    any            `json:"value"`
}

// FilterGroup representa una colección de filtros unidos por un operador lógico.
// Permite anidamiento para crear condiciones complejas.
type FilterGroup struct {
	Operator     LogicalOperator `json:"operator"`
	Filters      []Filter        `json:"filters,omitempty"`
	FilterGroups []FilterGroup   `json:"filter_groups,omitempty"`
}

// Sort define una regla de ordenamiento por un campo.
type Sort struct {
	Field     string        `json:"field"`
	Direction SortDirection `json:"direction"`
}

// Pagination define el límite y el desplazamiento para una consulta.
type Pagination struct {
	Limit  uint64
	Offset uint64
}

// Criteria es el contenedor principal para todos los parámetros de una consulta.
type Criteria struct {
	FilterGroup FilterGroup `json:"filter_group"`
	Sort        []Sort      `json:"sort,omitempty"`
	Pagination  *Pagination `json:"-"`
}

// Visitor define la interfaz para traducir Criteria a un lenguaje de consulta específico.
// La implementación construirá la consulta internamente.
type Visitor interface {
	BuildQuery(c Criteria) (string, []any, error)
}
