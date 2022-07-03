import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthguardService } from './Core/services/authguard.service';

const routes: Routes = [
  {
    path: 'admin',
    canActivate: [AuthguardService],
    loadChildren: () =>
      import('../app/Modules/admin/admin.module').then((m) => m.AdminModule),
  },
  {
    path: '',
    loadChildren: () =>
      import('../app/Modules/client/client.module').then((m) => m.ClientModule),
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
