import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { ToastrService } from 'ngx-toastr';
import { Instagram } from 'src/app/Core/models/instagram';
import { Product } from 'src/app/Core/models/product';
import { CartService } from 'src/app/Core/services/cart.service';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { ProductService } from 'src/app/Core/services/product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
})
export class ProductDetailComponent implements OnInit {
  public listInstagram: Instagram[] = [];
  public product: Product = new Product();
  public listProductRelation: Product[] = [];
  public listBreadcrumbLink: Array<any> = [];
  public listColor: Array<any> = [];
  public listSize: Array<any> = [];
  public slugCategory: string = '';
  public sizeId!: number;
  public colorId!: number;
  public quanlity: any = 1;
  listVote: Array<string> = [];
  formColor!: FormGroup;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private InstaService: InstagramService,
    private categoryService: CategoryProductService,
    private toastr: ToastrService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      let idPrd = params['idPrd'].split('-')[0];
      let slugCate1 = params['slugCate'];
      if (slugCate1 != null) {
        this.listBreadcrumbLink = [];
        this.getLink(slugCate1);
        this.slugCategory = params['slugCate'];
      }
      let slugCate2 = params['subSlugCate'];
      if (slugCate2 != null) {
        this.getLink(slugCate2);
        this.slugCategory = params['slugCate'] + '/' + params['subSlugCate'];
      }
      this.getProductById(idPrd);
      this.getProductdByCategoryId();
    });
    this.getAllInstagram();
  }

  addPrdToCart(product: Product): void {
    this.toastr.success(`Thêm sản phẩm thành công` + product.productName);
    setTimeout(() => {
      this.cartService.addPrdToCart(
        product,
        this.quanlity,
        this.colorId,
        this.sizeId
      );
    }, 1000);
  }

  getProductById(idPrd: number) {
    this.productService.getProductById(idPrd).subscribe((prd: Product) => {
      this.product = prd;
      this.getListColor();
      this.getListSize();
      if (this.product?.vote > 0) {
        this.listVote = [];
        for (let i = 0; i < this.product.vote; i++) {
          this.listVote?.push('');
        }
      }
    });
  }

  getListColor() {
    this.listColor = [];
    this.listColor = this.product.listColorSizeDto.filter(
      (value, index, self) =>
        index === self.findIndex((c) => c.colorId === value.colorId)
    );
    this.colorId = this.listColor[0]?.colorId;
    this.sizeId = this.listColor[0]?.sizeId;
  }

  getListSize() {
    this.listSize = [];
    this.listSize = this.product.listColorSizeDto.filter(
      (value, index, self) =>
        index === self.findIndex((c) => c.sizeId === value.sizeId)
    );
  }

  sendValueColor(color: any) {
    this.colorId = color.colorId;
  }

  sendValueSize(size: any) {
    this.sizeId = size.sizeId;
  }

  getProductdByCategoryId() {
    this.productService.getAllProduct().subscribe((data) => {
      this.listProductRelation = data.filter((prd: Product) => {
        return prd.categoryPrdId === this.product.categoryPrdId;
      });
      console.log(this.listProductRelation);
    });
  }

  getLink(slugCate: any) {
    this.categoryService.getAllCategoryPrd().subscribe((data) => {
      let category = data.filter(function (ele: any) {
        return ele.slug === slugCate;
      });
      this.listBreadcrumbLink.push(category[0]);
    });
  }

  getAllInstagram(): void {
    this.InstaService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }

  handleChangeQuanlity(num: number) {
    this.quanlity += num;
    if (this.quanlity === 0) {
      this.quanlity = 1;
    }
  }

  customOptionsSliderDetailPrd: OwlOptions = {
    loop: true,
    margin: 0,
    nav: true,
    dots: false,
    autoplay: true,
    navText: [
      `<div class="nav-slide-next"> <i class="fal fa-angle-left"></i> </div>`,
      `<div class="nav-slide-prev"> <i class="fal fa-angle-right"></i></div>`,
    ],
    autoplayTimeout: 3000,
    responsive: {
      0: {
        items: 1,
      },
      600: {
        items: 1,
      },
      1000: {
        items: 1,
      },
    },
  };

  customOptionsPrdRelation: OwlOptions = {
    loop: true,
    margin: 10,
    nav: false,
    dots: false,
    autoplay: true,
    // stagePadding: 50,
    navText: [
      `<div class="nav-slide-next"> <i class="fal fa-angle-left"></i> </div>`,
      `<div class="nav-slide-prev"> <i class="fal fa-angle-right"></i></div>`,
    ],
    autoplayTimeout: 3000,
    responsive: {
      0: {
        items: 4,
      },
      600: {
        items: 4,
      },
      1000: {
        items: 4,
      },
    },
  };
}
