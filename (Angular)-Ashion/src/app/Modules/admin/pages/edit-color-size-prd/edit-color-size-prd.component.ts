import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Color } from 'src/app/Core/models/color';
import { ColorSizePrd } from 'src/app/Core/models/colorSizePrd';
import { Product } from 'src/app/Core/models/product';
import { Size } from 'src/app/Core/models/size';
import { ColorSizePrdService } from 'src/app/Core/services/color-size-prd.service';
import { ColorService } from 'src/app/Core/services/color.service';
import { ProductService } from 'src/app/Core/services/product.service';
import { SizeService } from 'src/app/Core/services/size.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-edit-color-size-prd',
  templateUrl: './edit-color-size-prd.component.html',
  styleUrls: ['./edit-color-size-prd.component.css'],
})
export class EditColorSizePrdComponent implements OnInit {
  public formEditPrdSizeColor!: FormGroup;
  public listSize!: Size[];
  public listColor!: Color[];
  public listPrd!: Product[];
  public listCSP!: ColorSizePrd[];
  public colorSizePrd!: ColorSizePrd;
  public checkAction!: any;
  private cspId!: any;
  private action!: any;

  constructor(
    private fb: FormBuilder,
    private colorService: ColorService,
    private sizeService: SizeService,
    private productService: ProductService,
    private colorSizeProductService: ColorSizePrdService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.getAllColor();
    this.getAllProduct();
    this.getAllSize();

    this.formEditPrdSizeColor = this.fb.group({
      productId: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
      colorId: ['', [Validators.required]],
      sizeId: ['', [Validators.required]],
    });

    this.cspId = this.route.snapshot.paramMap?.get('cspId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.cspId != null) {
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

  getAllColor(): void {
    this.colorService.getAllColor().subscribe((data) => {
      this.listColor = data;
    });
  }

  getAllSize(): void {
    this.sizeService.getAllSize().subscribe((data) => {
      this.listSize = data;
    });
  }

  getAllProduct(): void {
    this.productService.getAllProduct().subscribe((data) => {
      this.listPrd = data;
    });
  }

  loadDataToUpdate(): void {
    this.colorSizeProductService.getById(this.cspId).subscribe((res) => {
      this.formEditPrdSizeColor.patchValue({
        productId: res.productId,
        quantity: res.quantity,
        sizeId: res.sizeId,
        colorId: res.colorId,
      });
    });
  }

  updateProduct(colorSizePrd: ColorSizePrd) {
    let pageNumber!: number;
    this.colorSizeProductService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.colorSizeProductService.updateCSP(this.cspId, colorSizePrd).subscribe(
      (res) => {
        this.toastr.success('sửa thành công : ');
        this.tableService.notifyCountValue(res.cspId);
        setTimeout(() => {
          this._router.navigateByUrl(
            '/admin/size_color_prd/page/' + pageNumber
          );
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi');
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup): void {
    this.colorSizePrd = <ColorSizePrd>form.value;
    this.updateProduct(this.colorSizePrd);
  }
}
