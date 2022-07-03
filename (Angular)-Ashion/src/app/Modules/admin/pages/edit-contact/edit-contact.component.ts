import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Contact } from 'src/app/Core/models/contact';
import { ContactService } from 'src/app/Core/services/contact.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-edit-contact',
  templateUrl: './edit-contact.component.html',
  styleUrls: ['./edit-contact.component.css'],
})
export class EditContactComponent implements OnInit {
  public formContact!: FormGroup;
  public contact!: Contact;
  public contactId!: any;
  public action!: any;
  public checkAction!: any;

  constructor(
    private fb: FormBuilder,
    private contactService: ContactService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this.formContact = this.fb.group({
      name: [''],
      email: [''],
      content: [''],
      status: [''],
      userId: [''],
    });

    this.contactId = this.route.snapshot.paramMap?.get('contactId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.contactId != null) {
      if (this.action === 'edit') {
        this.loadDataToUpdate();
        this.checkAction = 1;
      }
      if (this.action === 'detail') {
        this.loadDataToUpdate();
        this.checkAction = 2;
      }
    }
  }

  loadDataToUpdate(): void {
    this.contactService.getById(this.contactId).subscribe(
      (respon) => {
        this.formContact.patchValue({
          name: respon.name,
          email: respon.email,
          content: respon.content,
          status: respon.status,
          userId: respon.userId,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy bài liên hệ : ' + this.contactId);
      }
    );
  }

  updateInstagram(contact: Contact) {
    let pageNumber!: number;
    this.contactService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.contactService.update(this.contactId, contact).subscribe(
      (res) => {
        this.toastr.success('sửa thành công contact : ');
        this.tableService.notifyCountValue(res.instaId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/contact/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi contact : ' + err.contactId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.contact = <Contact>form.value;
    this.updateInstagram(this.contact);
  }
}
