import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
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
  selector: 'app-add-prd-size-color',
  templateUrl: './add-prd-size-color.component.html',
  styleUrls: ['./add-prd-size-color.component.css'],
})
export class AddPrdSizeColorComponent implements OnInit {
  public formAddPrdSizeColor!: FormGroup;
  public listSize!: Size[];
  public listColor!: Color[];
  public listPrd!: Product[];
  public listCSP!: ColorSizePrd[];
  public colorSizePrd!: ColorSizePrd;

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
    this.getAllSize();
    this.getAllProduct();

    this.formAddPrdSizeColor = this.fb.group({
      product: ['', [Validators.required]],
      quantity: ['', [Validators.required]],
      size: new FormArray([]),
      color: new FormArray([]),
    });
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

  onCheckboxChange(event: any) {
    const selectedSize = this.formAddPrdSizeColor.get('size') as FormArray;
    if (event.target.checked) {
      selectedSize.push(new FormControl(event.target.value));
    } else {
      const index = selectedSize.controls.findIndex(
        (x) => x.value === event.target.value
      );
      selectedSize.removeAt(index);
    }
    console.log(selectedSize);
  }

  onCheckboxChangeColor(event: any) {
    const selectedColor = this.formAddPrdSizeColor.get('color') as FormArray;
    if (event.target.checked) {
      selectedColor.push(new FormControl(event.target.value));
    } else {
      const index = selectedColor.controls.findIndex(
        (x) => x.value === event.target.value
      );
      selectedColor.removeAt(index);
    }
  }

  addProduct(form: FormGroup) {
    let lengthColor = form.value.color.length;
    let lengthSize = form.value.size.length;
    let prdId = form.value.product;
    let quantity = form.value.quantity;
    let listColorSize: ColorSizePrd[] = [];

    for (let i = 0; i < lengthColor; i++) {
      for (let j = 0; j < lengthSize; j++) {
        let colorSizeObj = {
          productId: prdId,
          quantity: quantity,
          colorId: form.value.color[i],
          sizeId: form.value.size[j],
        };
        listColorSize.push(<ColorSizePrd>colorSizeObj);
      }
    }
    console.log(listColorSize);

    this.colorSizeProductService.postCSP(listColorSize).subscribe(
      (res) => {
        this.toastr.success('thêm thành công');
        this.tableService.notifyCountValue(res.productId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/size_color_prd');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm thất bại ');
        }, 1000);
      }
    );
  }
  submitForm(form: FormGroup) {
    this.addProduct(form);
  }
}
