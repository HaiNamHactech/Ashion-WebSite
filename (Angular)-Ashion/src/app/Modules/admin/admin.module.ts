import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { AddProductComponent } from './pages/add-product/add-product.component';
import { AddColorComponent } from './pages/add-color/add-color.component';
import { AddSizeComponent } from './pages/add-size/add-size.component';
import { AddPrdSizeColorComponent } from './pages/add-prd-size-color/add-prd-size-color.component';
import { AddImgPrdComponent } from './pages/add-img-prd/add-img-prd.component';
import { AddSliderComponent } from './pages/add-slider/add-slider.component';
import { AddInstagramComponent } from './pages/add-instagram/add-instagram.component';
import { AddCategoryPrdComponent } from './pages/add-category-prd/add-category-prd.component';
import { AddCategoryBlogComponent } from './pages/add-category-blog/add-category-blog.component';
import { AddBlogComponent } from './pages/add-blog/add-blog.component';
import { AddRoleComponent } from './pages/add-role/add-role.component';
import { AddUserComponent } from './pages/add-user/add-user.component';
import { AddUserRoleComponent } from './pages/add-user-role/add-user-role.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { OrderComponent } from './pages/order/order.component';
import { TableComponent } from './component/table/table.component';
import { OrderDetailComponent } from './pages/order-detail/order-detail.component';
import { ProductComponent } from './pages/product/product.component';
import { CategoryComponent } from './pages/category/category.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CategoryBlogComponent } from './pages/category-blog/category-blog.component';
import { NgxLoadingModule } from 'ngx-loading';
import { ColorComponent } from './pages/color/color.component';
import { SizeComponent } from './pages/size/size.component';
import { SizeColorPrdComponent } from './pages/size-color-prd/size-color-prd.component';
import { EditColorSizePrdComponent } from './pages/edit-color-size-prd/edit-color-size-prd.component';
import { BlogComponent } from './pages/blog/blog.component';
import { CommentBlogComponent } from './pages/comment-blog/comment-blog.component';
import { EditCommentBlogComponent } from './pages/edit-comment-blog/edit-comment-blog.component';
import { SlidersComponent } from './pages/sliders/sliders.component';
import { InstagramComponent } from './pages/instagram/instagram.component';
import { ContactComponent } from './pages/contact/contact.component';
import { EditContactComponent } from './pages/edit-contact/edit-contact.component';
import { RoleComponent } from './pages/role/role.component';
import { ImagePrdComponent } from './pages/image-prd/image-prd.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CoreModule } from 'src/app/Core/modules/core.module';
import { CarouselModule } from 'ngx-owl-carousel-o';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'order',
        component: OrderComponent,
      },
      {
        path: 'order/page/:currentPage',
        component: OrderComponent,
      },
      {
        path: 'order-detail',
        component: OrderDetailComponent,
      },
      {
        path: 'add-product',
        component: AddProductComponent,
      },
      {
        path: 'add-color',
        component: AddColorComponent,
      },
      {
        path: 'add-size',
        component: AddSizeComponent,
      },
      {
        path: 'add-prd-size-color',
        component: AddPrdSizeColorComponent,
      },
      {
        path: 'add-img-prd',
        component: AddImgPrdComponent,
      },
      {
        path: 'list-img-prd',
        component: ImagePrdComponent,
      },
      {
        path: 'list-img-prd/page/:currentPage',
        component: ImagePrdComponent,
      },
      {
        path: 'list-img-prd/:action/:imageId',
        component: AddImgPrdComponent,
      },
      {
        path: 'add-instagram',
        component: AddInstagramComponent,
      },
      {
        path: 'instagram',
        component: InstagramComponent,
      },
      {
        path: 'instagram/page/:currentPage',
        component: InstagramComponent,
      },
      {
        path: 'instagram/:action/:instaId',
        component: AddInstagramComponent,
      },
      {
        path: 'add-category-prd',
        component: AddCategoryPrdComponent,
      },
      {
        path: 'category-prd/:action/:category',
        component: AddCategoryPrdComponent,
      },
      {
        path: 'add-category-blog',
        component: AddCategoryBlogComponent,
      },
      {
        path: 'category-blog/:action/:cateId',
        component: AddCategoryBlogComponent,
      },
      {
        path: 'add-blog',
        component: AddBlogComponent,
      },
      {
        path: 'blog',
        component: BlogComponent,
      },
      {
        path: 'blog/page/:currentPage',
        component: BlogComponent,
      },
      {
        path: 'blog/:action/:blogId',
        component: AddBlogComponent,
      },
      {
        path: 'commentBlog',
        component: CommentBlogComponent,
      },
      {
        path: 'commentBlog/page/:currentPage',
        component: CommentBlogComponent,
      },
      {
        path: 'commentBlog/:action/:commentId',
        component: EditCommentBlogComponent,
      },
      {
        path: 'add-role',
        component: AddRoleComponent,
      },
      {
        path: 'role',
        component: RoleComponent,
      },
      {
        path: 'role/page/:currentPage',
        component: RoleComponent,
      },
      {
        path: 'role/:action/:roleId',
        component: AddRoleComponent,
      },
      {
        path: 'add-user',
        component: AddUserComponent,
      },
      {
        path: 'add-user-role',
        component: AddUserRoleComponent,
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
      },
      {
        path: 'product',
        component: ProductComponent,
      },
      {
        path: 'product/page/:currentPage',
        component: ProductComponent,
      },
      {
        path: 'product/:action/:productId',
        component: AddProductComponent,
      },
      {
        path: 'categoryProduct',
        component: CategoryComponent,
      },
      {
        path: 'categoryProduct/page/:currentPage',
        component: CategoryComponent,
      },
      {
        path: 'categoryBlog',
        component: CategoryBlogComponent,
      },
      {
        path: 'categoryBlog/page/:currentPage',
        component: CategoryBlogComponent,
      },
      {
        path: 'color',
        component: ColorComponent,
      },
      {
        path: 'color/page/:currentPage',
        component: ColorComponent,
      },
      {
        path: 'color/:action/:colorId',
        component: AddColorComponent,
      },
      {
        path: 'size',
        component: SizeComponent,
      },
      {
        path: 'size/page/:currentPage',
        component: SizeComponent,
      },
      {
        path: 'size/:action/:sizeId',
        component: AddSizeComponent,
      },
      {
        path: 'size_color_prd',
        component: SizeColorPrdComponent,
      },
      {
        path: 'size_color_prd/page/:currentPage',
        component: SizeColorPrdComponent,
      },
      {
        path: 'size_color_prd/:action/:cspId',
        component: EditColorSizePrdComponent,
      },
      {
        path: 'slider',
        component: SlidersComponent,
      },
      {
        path: 'slider/page/:currentPage',
        component: SlidersComponent,
      },
      {
        path: 'slider/:action/:sliderId',
        component: AddSliderComponent,
      },
      {
        path: 'add-slider',
        component: AddSliderComponent,
      },
      {
        path: 'contact',
        component: ContactComponent,
      },
      {
        path: 'contact/page/:currentPage',
        component: ContactComponent,
      },
      {
        path: 'contact/:action/:contactId',
        component: EditContactComponent,
      },
    ],
  },
];

@NgModule({
  declarations: [
    TableComponent,
    AddProductComponent,
    AddColorComponent,
    AddSizeComponent,
    AddPrdSizeColorComponent,
    AddImgPrdComponent,
    AddSliderComponent,
    AddInstagramComponent,
    AddCategoryPrdComponent,
    AddCategoryBlogComponent,
    AddBlogComponent,
    AddRoleComponent,
    AddUserComponent,
    AddUserRoleComponent,
    DashboardComponent,
    OrderComponent,
    OrderDetailComponent,
    ProductComponent,
    CategoryComponent,
    CategoryBlogComponent,
    ColorComponent,
    SizeComponent,
    SizeColorPrdComponent,
    EditColorSizePrdComponent,
    BlogComponent,
    CommentBlogComponent,
    EditCommentBlogComponent,
    SlidersComponent,
    InstagramComponent,
    ContactComponent,
    EditContactComponent,
    RoleComponent,
    ImagePrdComponent,
  ],
  imports: [
    CommonModule,
    CarouselModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    CoreModule,
    FormsModule,
    RouterModule.forChild(routes),
  ],
})
export class AdminModule {}
