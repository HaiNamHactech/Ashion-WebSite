import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserRole } from 'src/app/Core/models/user-role';
import { UserRoleService } from 'src/app/Core/services/user-role.service';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.css'],
})
export class UserRoleComponent implements OnInit {
  public titleTable: string = 'User Role';
  public listUserRole!: UserRole[];
  public listUserRolePagination!: UserRole[];
  public listKey: Array<any> = [];
  public keyId: any;
  public showAction: boolean = true;
  public checkLoadding: boolean = false;

  public curentPage!: any;
  public offset!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filter: any = {
    page: 0,
    pageSize: 3,
    asc: true,
  };

  public filedTable: object = {
    id: 'Id',
    userName: 'User',
    roleName: 'Role',
  };

  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private userRoleService: UserRoleService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.listKey = Object.keys(this.filedTable);
    this.keyId = this.listKey[0];
    this.getUserRolePagination();
  }

  getUserRolePagination(): void {
    this.userRoleService
      .getUserRolePagination(this.filter)
      .subscribe((data) => {
        this.listUserRolePagination = data.content;
        this.offset = data.pageable.pageNumber + 1;
        this.pageSize = data.pageable.pageSize;
        this.lastPage = data.last;
        this.firstPage = data.first;
        this.totalPage = data.totalPages;
      });
  }

  handleDeleteItem(id: any) {
    this.userRoleService.delete(id).subscribe((res) => {
      this.toastr.success('Xóa UserRole : ' + res.id);
      setTimeout(() => {
        if (this.listUserRolePagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getUserRolePagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/user-role/' + 'edit/' + item.id);
    this.userRoleService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/user-role/' + 'detail/' + item.id);
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-user-role');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/user-role' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getUserRolePagination();
  }
}
