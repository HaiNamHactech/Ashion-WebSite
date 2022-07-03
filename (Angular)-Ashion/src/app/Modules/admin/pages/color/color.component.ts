import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Color } from 'src/app/Core/models/color';
import { ColorService } from 'src/app/Core/services/color.service';

@Component({
  selector: 'app-color',
  templateUrl: './color.component.html',
  styleUrls: ['./color.component.css'],
})
export class ColorComponent implements OnInit {
  public titleTable: string = 'Color';
  public listColor!: Color[];
  public listColorPagination!: Color[];
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
    private colorService: ColorService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllColor();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getColorPagination();
  }

  getAllColor(): void {
    this.colorService.getAllColor().subscribe((data) => {
      this.listColor = data;
      this.listKey = Object.keys(this.listColor[0]);
      this.keyId = this.listKey[0];
    });
  }

  getColorPagination(): void {
    this.colorService.getColorPagination(this.filter).subscribe((data) => {
      this.listColorPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(colorId: any) {
    this.colorService.deleteColor(colorId).subscribe((respon) => {
      this.toastr.success('Xóa TC Danh Mục :' + respon.colorName);
      setTimeout(() => {
        if (this.listColorPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getColorPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/color/' + 'edit/' + item.colorId);
    this.colorService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/color/' + 'detail/' + item.colorId);
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-color');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/color/' + 'page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getColorPagination();
  }
}
