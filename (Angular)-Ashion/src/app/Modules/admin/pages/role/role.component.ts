import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Role } from 'src/app/Core/models/role';
import { RoleService } from 'src/app/Core/services/role.service';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css'],
})
export class RoleComponent implements OnInit {
  public titleTable: string = 'Role';
  public listRole!: Role[];
  public listRolePagination !: Role[];
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

  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private roleService: RoleService,
    private route : ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllRole();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getRolePagination();
  }

  getAllRole(): void {
    this.roleService.getAll().subscribe((data) => {
      this.listRole = data;
      this.listKey = Object.keys(this.listRole[0]);
      this.keyId = this.listKey[0];
    });
  }

  getRolePagination(): void {
    this.roleService.getRolePagination(this.filter).subscribe((data) => {
      this.listRolePagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(roleId: any) {
    this.roleService.delete(roleId).subscribe((res) => {
      this.toastr.success('Xóa TC bài viết : ' + res.name);
      setTimeout(() => {
        if (this.listRolePagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getRolePagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/role/' + 'edit/' + item.roleId);
    this.roleService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/role/' + 'detail/' + item.roleId);
  }
  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-role');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/role' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getRolePagination();
  }
}
