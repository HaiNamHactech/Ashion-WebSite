import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent implements OnInit {
  public idRowChange: any;
  public listPrdOnPage = [3, 6, 9, 12];
  public numberPerPage = this.listPrdOnPage[0];
  @Input() ListItem: Array<any> = [];
  @Input() ListKey: Array<any> = [];
  @Input() id: any;
  @Input() showDetail!: boolean;
  @Input() showAction!: boolean;
  @Input() checkLoadding!: boolean;
  @Input() titleTable!: string;
  @Input() filedColumn !: any;
  @Output() handleDeleteItem = new EventEmitter();
  @Output() handleEditItem = new EventEmitter();
  @Output() handleDetailItem = new EventEmitter();
  @Output() handleNavigate = new EventEmitter();

  @Input() totalPage!: number;
  @Input() lastPage!: boolean;
  @Input() firstPage!: boolean;
  @Input() page!: number;
  @Input() pageSize!: number;
  @Output() curentPage = new EventEmitter();
  @Output() PrdOnOnePage = new EventEmitter();

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
    this.tableService.ValueFromChild.subscribe((data) => {
      this.idRowChange = data;
    });
  }

  onHandleDeleteItem(value: any): void {
    this.handleDeleteItem.emit(value);
  }

  onHandleEditItem(value: any): void {
    this.handleEditItem.emit(value);
  }

  onHandleDetailItem(value: any): void {
    this.handleDetailItem.emit(value);
  }

  onHandleNavigate(): void {
    this.handleNavigate.emit();
  }

  hanldeSelectPage(page: any): void {
    this.curentPage.emit(page +','+ this.numberPerPage);
  }
}
