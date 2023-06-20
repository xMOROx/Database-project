export interface SearchRequest {
    Filters: FilterRequest[];
    Sorts: SortRequest[];
    Page: number;
    Size: number;
    StartDate?: string;
    EndDate?: string;
}

interface FilterRequest {
    Key: string;
    Operator: string;
    FieldType: string;
    Value?: string;
    ValueTo?: string;
    Values?: string[];
}

interface SortRequest {
    Key: string;
    Direction: string;
}

