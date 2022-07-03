import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { ToastrService } from 'ngx-toastr';
import { catchError, EMPTY, map, Observable, of } from 'rxjs';
import { Color } from 'src/app/Core/models/color';
import { Product } from 'src/app/Core/models/product';
import { Size } from 'src/app/Core/models/size';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { ColorService } from 'src/app/Core/services/color.service';
import { ImagePrdService } from 'src/app/Core/services/image-prd.service';
import { ProductService } from 'src/app/Core/services/product.service';
import { SizeService } from 'src/app/Core/services/size.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent implements OnInit {
  public formAddProduct!: FormGroup;
  public formColorSize!: FormGroup;
  public imageFile: any = '../../../../../assets/img/slider-img.png';

  public checkLoadding: boolean = false;
  public listCategoryPrd: Array<any> = [];
  public listSize!: Array<any>;
  public listColor!: Array<any>;
  public listColorSizeDto: Array<any> = [];
  public selectColor: Array<any> = [];
  public selectSize: Array<any> = [];

  public listSliderImage: Array<string> = [
    '../../../../../assets/img/slider-img.png',
    '../../../../../assets/img/slider-img.png',
    '../../../../../assets/img/slider-img.png',
  ];

  public productId!: any;
  public action!: any;
  public checkAction: any = 0;
  public product!: Product;
  public typeProduct!: string;

  public checkColor: any = {
    colorName: null,
    still: false,
  };

  public checkSize: any = {
    sizeName: null,
    still: false,
  };

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private colorService: ColorService,
    private sizeService: SizeService,
    private toastr: ToastrService,
    private _router: Router,
    private route: ActivatedRoute,
    private tableService: TableService,
    private categoryService: CategoryProductService,
    private imagePrdService: ImagePrdService
  ) {}

  ngOnInit(): void {
    this.getAllColor();
    this.getAllSize();
    this.getAllCategoryPrd();

    this.formAddProduct = this.fb.group({
      productName: [
        '',
        [Validators.required, Validators.minLength(8)],
        this.validateFromAPI.bind(this),
      ],
      categoryPrdId: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.min(1)]],
      discount: ['', [Validators.required, Validators.max(80)]],
      thumbnail: ['', [Validators.required]],
      descriptionDetail: ['', [Validators.required]],
      descriptionShort: ['', [Validators.required]],
      vote: ['', [Validators.required, Validators.max(5)]],
      brand: ['', [Validators.required]],
      typeProduct: [''],
      featureProduct: [''],
      trendProduct: [''],
      newProduct: [''],
      status: [true],
      listColorSizeDto: [[]],
      urlImgs: [[]],
      urlImgId: [null],
    });

    this.formColorSize = this.fb.group({
      size: new FormArray([]),
      color: new FormArray([]),
    });

    this.productId = this.route.snapshot.paramMap?.get('productId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.productId != null) {
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

  validateFromAPI(
    control: AbstractControl
  ): Observable<ValidationErrors | null> {
    if (this.checkAction === 2) {
      return of(null);
    }
    return this.productService.getByProductName(control.value).pipe(
      map((user) => {
        if (user.length === 0) {
          return null;
        }
        if (user.length !== 0 && this.productId == user.productId) {
          return null;
        }
        return {
          UserNameFiledDuplicated: true,
        };
      }),
      catchError(() => {
        return EMPTY;
      })
    );
  }

  getAllColor(): void {
    this.colorService.getAllColor().subscribe((data) => {
      this.listColor = data.map((item: any) => {
        Object.assign(item, { selected: false });
      });

      this.listColor = data;
    });
  }

  getAllSize(): void {
    this.sizeService.getAllSize().subscribe((data) => {
      this.listSize = data.map((item: any) => {
        Object.assign(item, { selected: false });
      });
      this.listSize = data;
    });
  }

  getAllCategoryPrd(): void {
    this.categoryService.getAllCategoryPrd().subscribe((data) => {
      this.listCategoryPrd = data;
    });
  }

  loadDataToUpdate(): void {
    let newColor: any[] = [];
    let newSize: any[] = [];
    this.productService.getProductById(this.productId).subscribe(
      (respon) => {
        this.formAddProduct.patchValue({
          productName: respon.productName,
          newProduct: respon.newProduct,
          trendProduct: respon.trendProduct,
          featureProduct: respon.featureProduct,
          price: respon.price,
          discount: respon.discount,
          thumbnail: respon.thumbnail,
          vote: respon.vote,
          brand: respon.brand,
          categoryPrdId: respon.categoryPrdId,
          descriptionShort: respon.descriptionShort,
          descriptionDetail: respon.descriptionDetail,
          typeProduct: respon.typeProduct,
          urlImgs: respon.urlImgs,
          listColorSizeDto: respon.listColorSizeDto,
          urlImgId: respon.urlImgId,
          listColorSizeId: respon.listColorSizeId,
        });
        this.imageFile = respon.thumbnail;
        this.listSliderImage = respon.urlImgs;
        this.listColorSizeDto = respon.listColorSizeDto;
        this.listColorSizeDto.filter((item: any) => {
          newColor.includes(item.colorName)
            ? ''
            : newColor.push(item.colorName);
          newSize.includes(item.sizeName) ? '' : newSize.push(item.sizeName);
        });

        this.listColor.filter((item) => {
          newColor.includes(item.colorName) ? (item.selected = true) : '';
        });

        this.listSize.filter((item) => {
          newSize.includes(item.sizeName) ? (item.selected = true) : '';
        });

        if (respon.newProduct) {
          this.typeProduct = 'newProduct';
        } else if (respon.trendProduct) {
          this.typeProduct = 'trendProduct';
        } else if (respon.featureProduct) {
          this.typeProduct = 'featureProduct';
        }
      },
      (error) => {
        this.toastr.error('không tìm sản phẩm : ' + this.productId);
      }
    );
  }

  addProduct(product: Product) {
    this.productService.postProduct(product).subscribe(
      (res) => {
        this.toastr.success('thêm thành công sản phẩm : ' + res.productName);
        this.tableService.notifyCountValue(res.productId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/product');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi sản phẩm : ' + err.productName);
        }, 1000);
      }
    );
  }

  updateProduct(product: Product) {
    let pageNumber!: number;
    this.productService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.productService.updateProduct(this.productId, product).subscribe(
      (res) => {
        this.toastr.success('sửa thành công sản phẩm : ');
        this.tableService.notifyCountValue(res.productId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/product/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi sản phẩm : ' + err.productName);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    if (form.value.typeProduct === 'newProduct') {
      this.formAddProduct.patchValue({
        featureProduct: false,
        trendProduct: false,
        newProduct: true,
      });
    }
    if (form.value.typeProduct === 'trendProduct') {
      this.formAddProduct.patchValue({
        featureProduct: false,
        trendProduct: true,
        newProduct: false,
      });
    }
    if (form.value.typeProduct === 'featureProduct') {
      this.formAddProduct.patchValue({
        featureProduct: true,
        trendProduct: false,
        newProduct: false,
      });
    }
    console.log(form.value);

    this.product = <Product>form.value;
    if (this.checkAction === 0) {
      this.addProduct(this.product);
    }
    console.log(this.product);

    if (this.checkAction === 1) {
      this.updateProduct(this.product);
    }
  }

  onchangeThumbnail(event: any) {
    this.checkLoadding = true;
    this.productService.upload(event.target.files[0]).subscribe(
      (res) => {
        this.imageFile = res.secure_url;
        console.log(this.imageFile);
        this.formAddProduct.patchValue({
          thumbnail: this.imageFile,
        });
        this.checkLoadding = false;
      },
      (error) => {
        this.imageFile = '../../../../../assets/img/uploadError.jpg';
      }
    );
  }

  onchangeSliderImage(event: any) {
    this.listSliderImage = [];
    this.checkLoadding = true;
    if (event?.target?.files && event.target.files.length > 0) {
      for (let i = 0; i < event.target.files.length; i++) {
        this.imagePrdService.upload(event.target.files[i]).subscribe((res) => {
          this.listSliderImage.push(res.secure_url);
          this.checkLoadding = false;
        });
      }
      this.formAddProduct.patchValue({
        urlImgs: this.listSliderImage,
      });
    }
  }

  customOptionsSliderImagelPrd: OwlOptions = {
    loop: true,
    margin: 10,
    nav: false,
    dots: false,
    autoplay: true,
    navText: [
      `<div class="nav-slide-next"> <i class="fal fa-angle-left"></i> </div>`,
      `<div class="nav-slide-prev"> <i class="fal fa-angle-right"></i></div>`,
    ],
    autoplayTimeout: 3000,
    responsive: {
      0: {
        items: 3,
      },
      600: {
        items: 3,
      },
      1000: {
        items: 3,
      },
    },
  };

  onCheckboxSize(event: any) {
    if (event.target.checked) {
      let size = this.listSize.filter((item) => {
        return item.sizeName === event.target.value;
      });
      size[0].selected = true;
      this.selectSize.push(event.target.value);
    }
    if (this.selectColor.length > 0) {
      this.loadColorSizeQuantity();
    }
  }

  onCheckboxColor(event: any) {
    if (event.target.checked) {
      let color = this.listColor.filter((item) => {
        return item.colorName === event.target.value;
      });
      color[0].selected = true;
      this.selectColor.push(event.target.value);
    }
    if (this.selectSize.length > 0) {
      this.loadColorSizeQuantity();
    }
  }

  loadColorSizeQuantity() {
    for (let i = 0; i < this.selectColor.length; i++) {
      for (let j = 0; j < this.selectSize.length; j++) {
        let colorSizeObj = {
          colorName: this.selectColor[i],
          sizeName: this.selectSize[j],
          quantity: 1,
        };
        this.listColorSizeDto.push(colorSizeObj);
      }
    }
    // lọc những phần tử trùng nhau trong mảng
    this.listColorSizeDto = this.listColorSizeDto.filter(
      (value, index, self) =>
        index ===
        self.findIndex(
          (t) => t.colorName == value.colorName && t.sizeName == value.sizeName
        )
    );
    this.formAddProduct.patchValue({
      listColorSizeDto: this.listColorSizeDto,
    });
    console.log(this.listColorSizeDto);
  }

  deleteRowStock(row: any) {
    let index = this.listColorSizeDto.findIndex(
      (item: any) =>
        item.colorName === row.colorName && item.sizeName === row.sizeName
    );
    this.listColorSizeDto.splice(index, 1);
    this.deleteColor(row);
    this.deleteSize(row);
    this.formAddProduct.patchValue({
      listColorSizeDto: this.listColorSizeDto,
    });
  }

  deleteColor(row: any) {
    let checkColor = this.listColorSizeDto.filter((item) => {
      return item.colorName === row.colorName;
    });
    let checkC = checkColor.length > 0 ? true : false;
    this.checkColor.colorName = row.colorName;
    this.checkColor.still = checkC;
    this.listColor.forEach((item) => {
      if (
        item.colorName === this.checkColor.colorName &&
        this.checkColor.still === false
      ) {
        item.selected = false;
        let index = this.selectColor.indexOf(item.colorName);
        this.selectColor.splice(index, 1);
      }
    });
  }

  deleteSize(row: any) {
    let checkSize = this.listColorSizeDto.filter((item) => {
      return item.sizeName === row.sizeName;
    });

    let checkS = checkSize.length > 0 ? true : false;
    this.checkSize.sizeName = row.sizeName;
    this.checkSize.still = checkS;

    this.listSize.forEach((item) => {
      if (
        item.sizeName === this.checkSize.sizeName &&
        this.checkSize.still === false
      ) {
        item.selected = false;
        let index = this.selectSize.indexOf(item.sizeName);
        this.selectSize.splice(index, 1);
      }
    });
  }

  onChangeQuantity(ev: any) {
    console.log(ev);
  }
}
