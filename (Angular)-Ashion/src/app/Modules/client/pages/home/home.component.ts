import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { NgwWowService } from 'ngx-wow';
import { CategoryPrd } from 'src/app/Core/models/categoryPrd';
import { Instagram } from 'src/app/Core/models/instagram';
import { Product } from 'src/app/Core/models/product';
import { Slider } from 'src/app/Core/models/slider';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { ProductService } from 'src/app/Core/services/product.service';
import { SliderService } from 'src/app/Core/services/slider.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  public listCategoryPrd: CategoryPrd[] = [];
  public listProduct!: Product[];
  public listSlider!: Slider[];
  public listInstagram!: Instagram[];
  public listNewProduct: Product[] = [];
  public listTrendProduct: Product[] = [];
  public listFeatureProduct: Product[] = [];
  public listBestSellerProduct: Product[] = [];
  public categoryFirst!: CategoryPrd;
  public cateId: number = 0;
  constructor(
    private categoryService: CategoryProductService,
    private sliderService: SliderService,
    private instagramService: InstagramService,
    private productService: ProductService,
    private wowService: NgwWowService
  ) {}

  ngOnInit(): void {
    this.getAllProduct();
    this.getAllCategoryPrd();
    this.getAllSlider();
    this.getAllInstagram();
    this.wowService.init();
  }
  public getAllSlider(): void {
    this.sliderService.getAll().subscribe((data) => {
      this.listSlider = data;
    });
  }

  public getAllInstagram(): void {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }

  public getAllCategoryPrd(): void {
    this.categoryService.getAllCategoryPrd().subscribe((res) => {
      res?.sort(function (a: any, b: any) {
        return a.displayNo - b.displayNo;
      });
      res.forEach((item: CategoryPrd) => {
        if (item.parentId === 0) {
          this.listCategoryPrd.push(item);
        }
      });
      this.categoryFirst = <CategoryPrd>this.listCategoryPrd[0];
    });
  }

  public getAllProduct(): void {
    this.productService.getAllProduct().subscribe((data) => {
      this.listProduct = data;
      this.getTypeProduct();
    });
  }

  public getTypeProduct() {
    this.listProduct?.forEach((item) => {
      if (item?.newProduct && item?.discount < 70) {
        this.listNewProduct?.push(item);
      } else if (item?.newProduct && item?.discount >= 70) {
        this.listBestSellerProduct?.push(item);
      } else if (item?.trendProduct) {
        this.listTrendProduct?.push(item);
      } else if (item?.featureProduct) {
        this.listFeatureProduct?.push(item);
      }
    });
    if (this.listNewProduct.length >= 8) {
      this.shuffleArray(this.listNewProduct);
      this.listNewProduct = this.listNewProduct.slice(0, 8);
    }
  }

  public getProductInCategory(categoryId: any) {
    this.listNewProduct = [];
    this.listProduct?.forEach((item) => {
      if (
        item.categoryPrdId == categoryId &&
        item?.newProduct &&
        item?.discount < 70
      ) {
        this.listNewProduct?.push(item);
      }
      if (categoryId == 0 && item?.newProduct && item?.discount < 70) {
        this.listNewProduct?.push(item);
      }
    });
    if (this.listNewProduct.length >= 8) {
      this.shuffleArray(this.listNewProduct);
      this.listNewProduct = this.listNewProduct.slice(0, 8);
    }
  }

  public shuffleArray(array: Array<any>) {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      const temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  showPrdByCate(cateId: number): void {
    this.cateId = cateId;
    this.getProductInCategory(cateId);
  }

  customOptionsBanner: OwlOptions = {
    loop: true,
    margin: 0,
    nav: false,
    dots: true,
    autoplay: true,
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
}
