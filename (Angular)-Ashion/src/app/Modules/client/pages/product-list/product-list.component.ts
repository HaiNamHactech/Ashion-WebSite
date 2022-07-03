import { Component, Inject, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  NavigationStart,
  Route,
  Router,
} from '@angular/router';
import { Options } from 'ng5-slider';
import { combineLatest, filter, toArray } from 'rxjs';
import { CategoryPrd } from 'src/app/Core/models/categoryPrd';
import { Color } from 'src/app/Core/models/color';
import { Instagram } from 'src/app/Core/models/instagram';
import { Product } from 'src/app/Core/models/product';
import { Size } from 'src/app/Core/models/size';
import { CategoryProductService } from 'src/app/Core/services/category-product.service';
import { ColorService } from 'src/app/Core/services/color.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { ProductService } from 'src/app/Core/services/product.service';
import { SizeService } from 'src/app/Core/services/size.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  public category!: CategoryPrd;
  public treeCategory: Array<any> = [];
  public listProduct: Product[] = [];
  public listInstagram: Instagram[] = [];
  public listColor: Color[] = [];
  public listSize: Size[] = [];
  public slugCategory: string = '';
  public listBreadcrumbLink: Array<any> = [];
  public arrColor: Array<string> = [];
  public arrSize: Array<string> = [];
  // lọc giá sản phẩm //
  minValue: number = 33;
  maxValue: number = 99;
  options: Options = {
    floor: this.minValue,
    ceil: this.maxValue,
  };

  public curentPage!: any;
  public page!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filter: any = {
    page: 0,
    pageSize: 6,
    asc: true,
    color: [],
    size: [],
    priceMin: 0,
    priceMax: 0,
    categoryPrdId: 0,
    slugCategory: null,
  };

  constructor(
    private categoryService: CategoryProductService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private InstaService: InstagramService,
    private colorService: ColorService,
    private sizeService: SizeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllCategoryPrd();
    this.getAllInstagram();
    this.getAllColor();
    this.getAllSize();
    combineLatest([this.route.paramMap, this.route.queryParams]).subscribe(
      ([pathParams, queryParams]) => {
        this.filter.priceMin = queryParams['priceMin'];
        this.filter.priceMax = queryParams['priceMax'];
        this.filter.page = queryParams['page'];
        if (this.filter.page != null) {
          this.filter.page -= 1;
        }

        if (typeof queryParams['color'] === 'string') {
          this.filter.color = [];
          this.filter.color.push(queryParams['color']);
        } else {
        this.filter.color = queryParams['color'];
        }
        console.log(this.filter.color);

        if (typeof queryParams['size'] === 'string') {
          this.filter.size = [];
          this.filter.size.push(queryParams['size']);
        } else {
          this.filter.size = queryParams['size'];
        }
        console.log(this.filter.size);

        if (pathParams.get('slugCate') != null) {
          this.listBreadcrumbLink = [];
          this.filter.slugCategory = pathParams.get('slugCate');
          this.slugCategory = pathParams.get('slugCate') + '';
          this.getLink(this.filter.slugCategory);
        }
        if (pathParams.get('subSlugCate') != null) {
          this.filter.slugCategory = pathParams.get('subSlugCate');
          this.slugCategory =
            pathParams.get('slugCate') + '/' + pathParams.get('subSlugCate');
          this.getLink(this.filter.slugCategory);
        }
        this.getProductFilter();
      }
    );
  }

  getProductFilter(): void {
    this.listProduct = [];
    this.productService.getProductPagination(this.filter).subscribe((data) => {
      this.listProduct = data.content;
      this.page = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  hanldeSelectPage(page: any): void {
    if (this.filter.slugCategory !== null) {
      this.router.navigate(['/product-list/' + this.slugCategory], {
        queryParams: { page: page },
        queryParamsHandling: 'merge',
      });
    } else {
      this.router.navigate(['/product-list'], {
        queryParams: { page: page },
        queryParamsHandling: 'merge',
      });
    }
  }

  getAllCategoryPrd(): void {
    this.categoryService.getAllCategoryPrd().subscribe((data) => {
      this.treeMenu(data, 0, this.treeCategory);
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

  // hàm đệ quy danh mục //
  treeMenu(menus: Array<any>, parentID: any, childs: Array<any>) {
    menus.forEach((cate: any) => {
      if (cate.parentId === parentID) {
        let menu = {
          cateId: cate.categoryId,
          categoryName: cate.categoryName,
          slug: cate.slug,
          showHome: cate.showHome,
          displayNo: cate.displayNo,
          breadcrumbLink: cate.breadcrumbLink,
          totalItemPrd: cate.totalItemPrd,
          parentId: cate.parentId,
          childs: [],
        };
        childs.push(menu);
        this.treeMenu(menus, cate.categoryId, menu.childs);
      }
    });
  }

  filerPrdByPrice() {
    if (this.filter.slugCategory != null) {
      this.router.navigate(['/product-list/' + this.slugCategory], {
        queryParams: {
          priceMin: this.minValue,
          priceMax: this.maxValue,
          page: 1,
        },
        queryParamsHandling: 'merge',
      });
    } else {
      this.router.navigate(['/product-list'], {
        queryParams: {
          priceMin: this.minValue,
          priceMax: this.maxValue,
          page: 1,
        },
        queryParamsHandling: 'merge',
      });
    }
  }

  sendColor(colorName: string) {
    this.arrColor.push(colorName);
    var arrColor: Array<string> = [];
    let index = -1;
    if (this.arrColor.length >= 1) {
      arrColor = this.arrColor.filter(function (item: any) {
        index = arrColor.indexOf(item);
        return arrColor.includes(item) ? '' : arrColor.push(item);
      });
    }
    if (index !== -1) {
      arrColor.splice(index, 1);
      this.arrColor = arrColor;
    }

    if (this.filter.slugCategory != null) {
      this.router.navigate(['/product-list/' + this.slugCategory], {
        queryParams: { color: this.arrColor, page: 1 },
        queryParamsHandling: 'merge',
      });
    } else {
      this.router.navigate(['/product-list'], {
        queryParams: { color: this.arrColor, page: 1 },
        queryParamsHandling: 'merge',
      });
    }
  }

  sendSize(sizeName: string) {

    this.arrSize.push(sizeName);
    var arrSize: Array<string> = [];
    let index = -1;
    if (this.arrSize.length >= 1) {
      arrSize = this.arrSize.filter(function (item: any) {
        index = arrSize.indexOf(item);
        return arrSize.includes(item) ? '' : arrSize.push(item);
      });
    }
    if (index !== -1) {
      arrSize.splice(index, 1);
      this.arrSize = arrSize;
    }
    if (this.filter.slugCategory != null) {
      this.router.navigate(['/product-list/' + this.slugCategory], {
        queryParams: { size: this.arrSize, page: 1 },
        queryParamsHandling: 'merge',
      });
    } else {
      this.router.navigate(['/product-list'], {
        queryParams: { size: this.arrSize, page: 1 },
        queryParamsHandling: 'merge',
      });
    }
  }
}
