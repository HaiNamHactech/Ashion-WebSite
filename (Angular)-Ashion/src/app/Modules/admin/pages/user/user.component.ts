import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/Core/models/user';
import { UserService } from 'src/app/Core/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  public titleTable: string = 'User';
  public listUser!: User[];
  public listUserPagination!: User[];
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
    userId: 'Id',
    firstName: 'FirstName',
    lastName: 'LastName',
    email: 'Email',
    createDate: 'CreateDate',
    modifieDate: 'ModifieDate',
    createBy: 'CreateBy',
    modifieBy: 'ModifieBy',
    status: 'Status',
  };

  constructor(
    private toastr: ToastrService,
    private _router: Router,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.listKey = Object.keys(this.filedTable);
    this.keyId = this.listKey[0];
    this.getUserPagination();
  }

  getUserPagination(): void {
    this.userService.getUserPagination(this.filter).subscribe((data) => {
      this.listUserPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(userId: any) {
    this.userService.delete(userId).subscribe((res) => {
      this.toastr.success('Xóa User bài viết : ' + res.name);
      setTimeout(() => {
        if (this.listUserPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getUserPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl('/admin' + '/user/' + 'edit/' + item.userId);
    this.userService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl('/admin' + '/user/' + 'detail/' + item.userId);
  }

  handleNavigate() {
    this._router.navigateByUrl('/admin' + '/add-user');
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/user' + '/page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getUserPagination();
  }
}
