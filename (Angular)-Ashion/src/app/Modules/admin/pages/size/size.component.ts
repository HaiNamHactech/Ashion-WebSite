import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Size } from 'src/app/Core/models/size';
import { SizeService } from 'src/app/Core/services/size.service';

@Component({
  selector: 'app-size',
  templateUrl: './size.component.html',
  styleUrls: ['./size.component.css'],
})
export class SizeComponent implements OnInit {
  public titleTable: string = 'Size';
  public listSize!: Size[];
  public listSizePagination !: Size[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkLoadding: boolean = false;

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  constructor(
    private sizeService: SizeService,
    private toastr: ToastrService,
    private _router: Router,
    private route : ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.getAllSize();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getSizePagination();
  }

  getAllSize(): void {
    this.sizeService.getAllSize().subscribe((data) => {
      console.log(data);
      this.listSize = data;
      this.listKey = Object.keys(this.listSize[0]);
      this.keyId = this.listKey[0];
    });
  }

  getSizePagination(): void {
    this.sizeService.getSizePagination(this.filter).subscribe((data) => {
      this.listSizePagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(sizeId: number) {
    this.sizeService.deleteSize(sizeId).subscribe((respon) => {
      this.toastr.success('Xóa TC size :' + respon.sizeName);
      setTimeout(() => {
        if (this.listSizePagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getSizePagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/size/' + 'edit/' + item.sizeId);
    this.sizeService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/size/' + 'detail/' + item.sizeId);
  }
  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-size');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/size' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getSizePagination();
  }


}
