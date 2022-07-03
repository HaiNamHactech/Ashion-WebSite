import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Slider } from 'src/app/Core/models/slider';
import { SliderService } from 'src/app/Core/services/slider.service';

@Component({
  selector: 'app-sliders',
  templateUrl: './sliders.component.html',
  styleUrls: ['./sliders.component.css'],
})
export class SlidersComponent implements OnInit {
  public titleTable: string = 'Slider';
  public listSlider!: Slider[];
  public listSliderPagination!: Slider[];
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
    private sliderService: SliderService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllSlider();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getSliderPagination();
  }

  getAllSlider(): void {
    this.sliderService.getAll().subscribe((data) => {
      this.listSlider = data;
      this.listKey = Object.keys(this.listSlider[0]);
      this.keyId = this.listKey[0];
    });
  }

  getSliderPagination(): void {
    this.sliderService.getSliderPagination(this.filter).subscribe((data) => {
      this.listSliderPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(sliderId: number) {
    this.sliderService.delete(sliderId).subscribe((respon) => {
      this.toastr.success('Xóa TC slider :' + respon.sliderId);
      setTimeout(() => {
        if (this.listSliderPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getSliderPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/slider/' + 'edit/' + item.sliderId);
    this.sliderService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/slider/' + 'detail/' + item.sliderId
    );
  }
  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-slider');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/slider' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getSliderPagination();
  }
}
