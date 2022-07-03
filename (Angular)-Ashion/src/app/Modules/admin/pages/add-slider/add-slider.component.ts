import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Slider } from 'src/app/Core/models/slider';
import { SliderService } from 'src/app/Core/services/slider.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-slider',
  templateUrl: './add-slider.component.html',
  styleUrls: ['./add-slider.component.css'],
})
export class AddSliderComponent implements OnInit {
  public formAddSlider!: FormGroup;
  public sliderId!: any;
  public action!: any;
  public checkAction: any = 0;
  public checkLoadding: boolean = false;
  public slider!: Slider;

  constructor(
    private fb: FormBuilder,
    private sliderService: SliderService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
    this.formAddSlider = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      displayNo: ['', [Validators.required]],
    });

    this.sliderId = this.route.snapshot.paramMap?.get('sliderId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.sliderId != null) {
      if (this.action === 'edit') {
        this.loadDataToUpdate();
        this.checkAction = 1;
      }
      if (this.action === 'detail') {
        this.loadDataToUpdate();
        this.checkAction = 2;
      }
    }
  }

  loadDataToUpdate(): void {
    this.sliderService.getById(this.sliderId).subscribe(
      (respon) => {
        this.formAddSlider.patchValue({
          title: respon.title,
          content: respon.content,
          displayNo: respon.displayNo,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy màu : ' + this.sliderId);
      }
    );
  }

  addSlider(slider: Slider) {
    this.sliderService.add(slider).subscribe(
      (res) => {
        this.toastr.success('thêm thành slider : ' + res.sliderId);
        this.tableService.notifyCountValue(res.sliderId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/slider');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi slider : ' + err.sliderId);
        }, 1000);
      }
    );
  }

  updateSlider(slider: Slider) {
    let pageNumber!: number;
    this.sliderService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.sliderService.update(this.sliderId, slider).subscribe(
      (res) => {
        this.toastr.success('sửa thành công màu : ');
        this.tableService.notifyCountValue(res.sliderId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/slider/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi danh mục : ' + err.sliderId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.slider = <Slider>form.value;
    if (this.checkAction === 0) {
      this.addSlider(this.slider);
    }
    if (this.checkAction === 1) {
      this.updateSlider(this.slider);
    }
  }
}
