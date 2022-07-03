import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { BlogComponent } from './pages/blog/blog.component';
import { ClientComponent } from './client.component';
import { CategoryItemComponent } from './component/category-item/category-item.component';
import { ProductItemComponent } from './component/product-item/product-item.component';
import { TrendItemComponent } from './component/trend-item/trend-item.component';
import { InstaItemComponent } from './component/insta-item/insta-item.component';
import { BlogItemComponent } from './component/blog-item/blog-item.component';
import { ProductDetailComponent } from './pages/product-detail/product-detail.component';
import { CartComponent } from './pages/cart/cart.component';
import { CartItemComponent } from './component/cart-item/cart-item.component';
import { CheckOutComponent } from './pages/check-out/check-out.component';
import { BlogDetailComponent } from './pages/blog-detail/blog-detail.component';
import { CommentBlogComponent } from './component/comment-blog/comment-blog.component';
import { ContactComponent } from './pages/contact/contact.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { Ng5SliderModule } from 'ng5-slider';
import { ScrollTopComponent } from './component/scroll-top/scroll-top.component';
import { PageSearchComponent } from './pages/page-search/page-search.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { NgwWowModule } from 'ngx-wow';
import { PaginationComponent } from './component/pagination/pagination.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes = [
  {
    path: '',
    component: ClientComponent,
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
      {
        path: 'home',
        component: HomeComponent,
      },
      {
        path: 'blog',
        component: BlogComponent,
      },
      {
        path: 'product-detail',
        component: ProductDetailComponent,
      },
      {
        path: 'shoping-cart',
        component: CartComponent,
      },
      {
        path: 'pages/check-out',
        component: CheckOutComponent,
      },
      {
        path: 'blogs/:slugBlog',
        component: BlogDetailComponent,
      },
      {
        path: 'contact',
        component: ContactComponent,
      },
      {
        path: 'product-list',
        component: ProductListComponent,
      },
      {
        path: 'products/:idPrd',
        component: ProductDetailComponent,
      },
      {
        path: 'product-list/:slugCate',
        component: ProductListComponent,
      },
      {
        path: 'product-list/:slugCate/:subSlugCate',
        component: ProductListComponent,
      },
      {
        path: 'products/:slugCate/:idPrd',
        component: ProductDetailComponent,
      },
      {
        path: 'products/:slugCate/:subSlugCate/:idPrd',
        component: ProductDetailComponent,
      },
      {
        path: 'search',
        component: PageSearchComponent,
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'pages/wishlist',
        component: WishlistComponent,
      },
    ],
  },
];

@NgModule({
  declarations: [
    HomeComponent,
    BlogComponent,
    CategoryItemComponent,
    ProductItemComponent,
    TrendItemComponent,
    InstaItemComponent,
    BlogItemComponent,
    ProductDetailComponent,
    CartComponent,
    CartItemComponent,
    CheckOutComponent,
    BlogDetailComponent,
    CommentBlogComponent,
    ContactComponent,
    ProductListComponent,
    ScrollTopComponent,
    PageSearchComponent,
    RegisterComponent,
    LoginComponent,
    WishlistComponent,
    PaginationComponent,
  ],
  imports: [
    CommonModule,
    CarouselModule,
    Ng5SliderModule,
    ReactiveFormsModule,
    FormsModule,
    NgwWowModule,
    RouterModule.forChild(routes),
  ],
})
export class ClientModule {}
