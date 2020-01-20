import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { GridColumn } from '../grid-column';
import { GridService } from '../grid.service';

import * as _ from 'lodash';
import { REQUEST } from 'src/app/constants/Constants';

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.css']
})
export class GridComponent implements OnInit {

  dataList: any[];
  @Input() columns: GridColumn[];
  @Input() filters: Filter[] = [];     // Default filters which should never be removed from filters
  @Input() dataSourceURL: String;

  @Input() scrollable = false;
  @Input() scrollHeight;
  @Input() paginator = true;
  @Input() pageSize = 20;

  @Input() selectionMode;
  @Input() selection;
  @Input() selectionDataKey;

  @Input() rowClassFn;


  gridId;
  currentPage: number;
  currentSort: { column: '', type: ''} = { column: '', type: ''};

  totalRecords = 0;
  filterArray: Filter[];
  constructor(private gridService: GridService,
    private cd: ChangeDetectorRef) { }

  ngOnInit() {
    this.filterArray = _.clone(this.filters);
    this.getDataList(1, null, null);

    this.gridId = this.dataSourceURL.replace(/[/]/g, "");
  }

  onColumnClick(col: GridColumn) {

    if (!col.sortable) return;

    const field = col.field; //: Field name of the sorted column
    let order = null;
    if (!col['order']) {
      order = REQUEST.ORDER_TYPE_ASC;
    } else if (col['order'] === REQUEST.ORDER_TYPE_ASC) {
      order = REQUEST.ORDER_TYPE_DESC;
    } else {
      order = REQUEST.ORDER_TYPE_ASC;
    }

    col['order'] = order;
    // const meta = col.multisortmeta;

    if (field === this.currentSort.column && order === this.currentSort.type) return;

    this.getDataList(this.currentPage, field, order);
  }

  onPage(event) {
    const first = event['first'];
    const pageNumber = (this.pageSize+first) / this.pageSize;
    this.getDataList(pageNumber, null, null);
  }

  refresh() {
    this.cd.detectChanges();
    this.getDataList(this.currentPage, null, null);
  }

  getDataList(pageNumber, orderBy, orderType) {
    const me = this;
    this.currentPage = pageNumber;

    if (!orderBy) {
      orderBy = this.currentSort.column;
    } else {
      this.currentSort.column = orderBy;
    }

    if (!orderType) {
      orderType = this.currentSort.type;
    } else {
      this.currentSort.type = orderType;
    }

    me.dataList = [];
    this.gridService.getDataList(this.dataSourceURL, pageNumber, this.pageSize, orderBy, orderType, this.filterArray).subscribe(
      response => {
        if (response['data']) {
          me.dataList = me.dataList.concat(response['data']);
          me.totalRecords = response['count'];
        }
        me.cd.markForCheck();
        me.cd.detectChanges();
      }
    )
  }


  filterGrid(value, col) {
    const me = this;
    const index = me.filterArray.findIndex(function(item) {
      return item.FILTER_KEY === col;
    });

    if (index > -1) {

      if (!value || "" === value) {
        me.filterArray.splice(index, 1);
      } else {
        me.filterArray[index].FILTER_VALUE = value;
      }

    } else {
      const filter = new Filter();
      filter.FILTER_KEY = col;
      filter.FILTER_VALUE = value;
      filter.FILTER_TYPE = REQUEST.FILTER_TYPE_CHARS;
      me.filterArray.push(filter);
    }
    me.getDataList(me.currentPage, null, null);
  }

  debounced = _.debounce(this.filterGrid, 300, { 'leading': false, 'trailing': true });
  onFilter(value, col) {
    const me = this;
    me.debounced(value, col);
  }

  onFilterClick(event: Event) {
    event.stopPropagation();
  }

  lookupRowStyleClass(rowData) {
    return !rowData.isActive ? 'mop-inactive-rec' : '';
  }
}

export class Filter {
  FILTER_KEY;
  FILTER_VALUE;
  FILTER_TYPE;
}
