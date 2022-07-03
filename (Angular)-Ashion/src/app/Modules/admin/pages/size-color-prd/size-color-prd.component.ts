import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ColorSizePrd } from 'src/app/Core/models/colorSizePrd';
import { ColorSizePrdService } from 'src/app/Core/services/color-size-prd.service';
import { ProductService } from 'src/app/Core/services/product.service';

@Component({
  selector: 'app-size-color-prd',
  templateUrl: './size-color-prd.component.html',
  styleUrls: ['./size-color-prd.component.css'],
})
export class SizeColorPrdComponent implements OnInit {
  public titleTable: string = 'Table Size Color Product';
  public listColorSizePrd!: ColorSizePrd[];
  public listColorSizePrdPagination!: ColorSizePrd[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkAction: any = 0;
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
    private toastr: ToastrService,
    private _router: Router,
    private cspService: ColorSizePrdService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllCSP();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getColorSizePagination();
  }

  getAllCSP(): void {
    this.cspService.getAll().subscribe((data) => {
      this.listColorSizePrd = data;
      this.listKey = Object.keys(this.listColorSizePrd[0]);
      this.keyId = this.listKey[0];
    });
  }

  getColorSizePagination(): void {
    this.cspService.getColorSizePagination(this.filter).subscribe((data) => {
      this.listColorSizePrdPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(id: any) {
    this.cspService.deleteCSP(id).subscribe((respon) => {
      this.toastr.success('Xóa TC SCP:' + respon.id);
      setTimeout(() => {
        if (this.listColorSizePrdPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getColorSizePagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/size_color_prd/edit/' + item.id);
    this.cspService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/size_color_prd/' + 'detail/' + item.id
    );
  }
  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-prd-size-color');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/size_color_prd' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getColorSizePagination();
  }
}
