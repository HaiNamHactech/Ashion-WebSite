import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Image } from 'src/app/Core/models/image';
import { Product } from 'src/app/Core/models/product';
import { ImagePrdService } from 'src/app/Core/services/image-prd.service';
import { ProductService } from 'src/app/Core/services/product.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-img-prd',
  templateUrl: './add-img-prd.component.html',
  styleUrls: ['./add-img-prd.component.css'],
})
export class AddImgPrdComponent implements OnInit {
  public formAddImgPrd!: FormGroup;
  public imageId!: any;
  public checkLoadding: boolean = false;
  public listImagePrd: Array<string> = [];
  public listProduct!: Product[];
  public checkAction: any = 0;
  public action!: any;
  public imagePrd!: Image;

  constructor(
    private fb: FormBuilder,
    private imagePrdService: ImagePrdService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute,
    private tableService: TableService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.getAllPrd();
    this.formAddImgPrd = this.fb.group({
      productId: ['', [Validators.required]],
      urlImgs: [[]],
    });

    this.imageId = this.route.snapshot.paramMap?.get('imageId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.imageId != null) {
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

  getAllPrd(): void {
    this.productService.getAllProduct().subscribe((data) => {
      this.listProduct = data;
    });
  }

  loadDataToUpdate(): void {
    this.imagePrdService.getById(this.imageId).subscribe(
      (respon) => {
        this.formAddImgPrd.patchValue({
          productId: respon.productId,
          urlImgs: respon.urlImgs,
        });
        this.listImagePrd = respon.urlImgs;
      },
      (error) => {
        this.toastr.error('không tìm thấy màu : ' + this.imageId);
      }
    );
  }

  addImagePrd(imagePrd: Image) {
    this.imagePrdService.add(imagePrd).subscribe(
      (res) => {
        this.toastr.success('thêm thành hình ảnh : ' + res.imagePrd);
        this.tableService.notifyCountValue(res.imageId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/list-img-prd');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi hình ảnh  : ' + err.imageId);
        }, 1000);
      }
    );
  }

  updateImagePrd(imagePrd: Image) {
    let pageNumber!: number;
    this.imagePrdService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.imagePrdService.update(this.imageId, imagePrd).subscribe(
      (res) => {
        this.toastr.success('sửa thành công sản phẩm : ');
        this.tableService.notifyCountValue(res.imageId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/list-img-prd/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi hình ảnh : ' + err.imageId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.imagePrd = <Image>form.value;
    console.log(this.imagePrd);
    if (this.checkAction === 0) {
      this.addImagePrd(this.imagePrd);
    }
    if (this.checkAction === 1) {
      this.updateImagePrd(this.imagePrd);
    }
  }

  onchangeImage(event: any) {
    this.listImagePrd = [];
    this.checkLoadding = true;
    console.log(typeof this.listImagePrd);
    if (event?.target?.files && event.target.files.length > 0) {
      for (let i = 0; i < event.target.files.length; i++) {
        this.imagePrdService.upload(event.target.files[i]).subscribe((res) => {
          this.listImagePrd.push(res.secure_url);
          this.checkLoadding = false;
        });
      }

      this.formAddImgPrd.patchValue({
        urlImgs: this.listImagePrd,
      });
    }
  }
}
