export interface GridColumn {
    field: string;
    header: string;
    sortable: boolean;
    filter: boolean;
    isActiveField?: string;
    customColumn: CustomColumn;
}

export interface CustomColumn {
    type?: string,
    clickFn?(rowData: any, subString: string): void;
    serverCol?: string,
    label?: string
}
