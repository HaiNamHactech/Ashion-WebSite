import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Instagram } from 'src/app/Core/models/instagram';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-add-instagram',
  templateUrl: './add-instagram.component.html',
  styleUrls: ['./add-instagram.component.css'],
})
export class AddInstagramComponent implements OnInit {
  public formAddInstagram!: FormGroup;
  public instaId!: any;
  public action!: any;
  public checkAction: any = 0;
  public imageFile: any = '../../../../../assets/img/up.png';
  public checkLoadding: boolean = false;
  public instagram!: Instagram;

  constructor(
    private fb: FormBuilder,
    private instagramService: InstagramService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router: Router,
    private tableService: TableService
  ) {}

  ngOnInit(): void {
    this.formAddInstagram = this.fb.group({
      displayNo: ['', [Validators.required]],
      urlImg: ['', [Validators.required]],
    });

    this.instaId = this.route.snapshot.paramMap?.get('instaId');
    this.action = this.route.snapshot.paramMap?.get('action');

    if (this.instaId != null) {
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
    this.instagramService.getById(this.instaId).subscribe(
      (respon) => {
        this.formAddInstagram.patchValue({
          displayNo: respon.displayNo,
          urlImg: respon.urlImg,
        });
        this.imageFile = respon.urlImg;
      },
      (error) => {
        this.toastr.error('không tìm thấy instagram : ' + this.instaId);
      }
    );
  }

  addInstagram(instagram: Instagram) {
    this.instagramService.add(instagram).subscribe(
      (res) => {
        this.toastr.success('thêm thành công instagram : ' + res.instaId);
        this.tableService.notifyCountValue(res.instaId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/instagram');
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('thêm lỗi instagram : ' + err.instaId);
        }, 1000);
      }
    );
  }

  updateInstagram(instagram: Instagram) {
    let pageNumber!: number;
    this.instagramService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.instagramService.update(this.instaId, instagram).subscribe(
      (res) => {
        this.toastr.success('sửa thành công instagram : ');
        this.tableService.notifyCountValue(res.instaId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/instagram/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi instagram : ' + err.instaId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    this.instagram = <Instagram>form.value;
    if (this.checkAction === 0) {
      this.addInstagram(this.instagram);
    }
    if (this.checkAction === 1) {
      this.updateInstagram(this.instagram);
    }
  }

  onchangeImage(event: any) {
    this.checkLoadding = true;
    console.log(event.target.files[0]);
    this.instagramService.upload(event.target.files[0]).subscribe(
      (res) => {
        this.imageFile = res.secure_url;
        console.log(this.imageFile);
        this.formAddInstagram.patchValue({
          urlImg: this.imageFile,
        });
        this.checkLoadding = false;
      },
      (error) => {
        this.imageFile = '../../../../../assets/img/uploadError.jpg';
      }
    );
  }
}
