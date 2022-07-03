import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Instagram } from 'src/app/Core/models/instagram';
import { InstagramService } from 'src/app/Core/services/instagram.service';

@Component({
  selector: 'app-instagram',
  templateUrl: './instagram.component.html',
  styleUrls: ['./instagram.component.css'],
})
export class InstagramComponent implements OnInit {
  public titleTable: string = 'Instagram';
  public listInstagram!: Instagram[];
  public listInstagramPagination !: Instagram[];
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
    private instagramService: InstagramService,
    private toastr: ToastrService,
    private _router: Router,
    private route : ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllInstagram();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getInstagramPagination();
  }

  getInstagramPagination(): void {
    this.instagramService.getInstaPagination(this.filter).subscribe((data) => {
      this.listInstagramPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  getAllInstagram(): void {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
      this.listKey = Object.keys(this.listInstagram[0]);
      this.keyId = this.listKey[0];
    });
  }

  handleDeleteItem(instaId: any) {
    this.instagramService.delete(instaId).subscribe((respon) => {
      this.toastr.success('Xóa TC Instagram :' + respon.instaId);
      setTimeout(() => {
        if (this.listInstagramPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getInstagramPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/instagram/' + 'edit/' + item.instaId
    );
    this.instagramService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/instagram/' + 'detail/' + item.instaId
    );
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-instagram');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl(
      '/admin' + '/instagram' + '/page/' + pageNumber
    );
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getInstagramPagination();
  }
}
