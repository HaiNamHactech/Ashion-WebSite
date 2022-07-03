import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { HeaderComponent } from 'src/app/Share/layout/header/header.component';
import { FooterComponent } from 'src/app/Share/layout/footer/footer.component';
import { NavbarAdminComponent } from 'src/app/Share/layout/navbar-admin/navbar-admin.component';
import { PaginationComponent } from 'src/app/Share/layout/pagination/pagination.component';
// import { PaginationComponent } from 'src/app/Share/layout/pagination/pagination.component';

@NgModule({
  declarations: [FooterComponent, NavbarAdminComponent, PaginationComponent],
  imports: [CommonModule],
  exports: [FooterComponent, NavbarAdminComponent, PaginationComponent],
})
export class CoreModule {}
