import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Image } from 'src/app/Core/models/image';
import { ImagePrdService } from 'src/app/Core/services/image-prd.service';

@Component({
  selector: 'app-image-prd',
  templateUrl: './image-prd.component.html',
  styleUrls: ['./image-prd.component.css'],
})
export class ImagePrdComponent implements OnInit {
  public titleTable: string = 'Image Product';
  public listImagePrd!: Image[];
  public listImagePrdPagination!: Image[];
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
    private imageService: ImagePrdService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllImagePrd();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getImagePagination();
  }

  getAllImagePrd(): void {
    this.imageService.getAll().subscribe((data) => {
      this.listImagePrd = data;
      this.listKey = Object.keys(this.listImagePrd[0]);
      this.keyId = this.listKey[0];
    });
  }

  getImagePagination(): void {
    this.imageService.getImagePagination(this.filter).subscribe((data) => {
      this.listImagePrdPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(imageId: any) {
    this.imageService.delete(imageId).subscribe((respon) => {
      this.toastr.success('Xóa TC ảnh sản phầm  :');
      setTimeout(() => {
        if (this.listImagePrdPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getImagePagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/list-img-prd/' + 'edit/' + item.imgId
    );
    this.imageService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/list-img-prd/' + 'detail/' + item.imgId
    );
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-img-prd');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/list-img-prd' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getImagePagination();
  }
}
