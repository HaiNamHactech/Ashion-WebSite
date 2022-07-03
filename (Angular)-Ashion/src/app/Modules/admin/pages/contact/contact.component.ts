import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Contact } from 'src/app/Core/models/contact';
import { BlogService } from 'src/app/Core/services/blog.service';
import { ContactService } from 'src/app/Core/services/contact.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
})
export class ContactComponent implements OnInit {
  public titleTable: string = 'Contact';
  public listContact!: Contact[];
  public listContactPagination !: Contact[];
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
    private blogService: BlogService,
    private contactService: ContactService,
    private route : ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getAllContact();
    this.curentPage = this.route.snapshot.paramMap?.get('currentPage');
    if (this.curentPage !== null) {
      this.filter.page = this.curentPage - 1;
    }
    this.getContactPagination();
  }

  getAllContact(): void {
    this.contactService.getAll().subscribe((data) => {
      this.listContact = data;
      this.listKey = Object.keys(this.listContact[0]);
      this.keyId = this.listKey[0];
    });
  }

  getContactPagination(): void {
    this.contactService.getContactPagination(this.filter).subscribe((data) => {
      this.listContactPagination = data.content;
      this.offset = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }

  handleDeleteItem(contactId: any) {
    this.contactService.delete(contactId).subscribe((res) => {
      this.toastr.success('Xóa TC bài viết : ' + res.contactId);
      setTimeout(() => {
        if (this.listContactPagination.length <= 1) {
          this.handleSelectPage(this.offset - 1 + ',' + this.pageSize);
        } else {
          this.getContactPagination();
        }
      }, 1000);
    });
  }

  handleEditItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/contact/' + 'edit/' + item.contactId
    );
    this.contactService.notifyPageNumberValue(this.offset);
  }

  handleDetailItem(item: any) {
    this._router.navigateByUrl(
      '/admin' + '/contact/' + 'detail/' + item.contactId
    );
  }

  handleSelectPage(page: any) {
    let str = page.split(',');
    const pageNumber = str[0];
    const PrdOnPage = str[1];
    this._router.navigateByUrl('/admin' + '/contact/' + 'page/' + pageNumber);
    this.filter.page = pageNumber - 1;
    this.filter.pageSize = PrdOnPage;
    this.getContactPagination();
  }
}
