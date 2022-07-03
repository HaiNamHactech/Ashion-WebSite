import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Order } from 'src/app/Core/models/order';
import { OrderService } from 'src/app/Core/services/order.service';
import { TableService } from 'src/app/Core/services/table.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css'],
})
export class EditOrderComponent implements OnInit {
  public formEditOrder!: FormGroup;
  public order!: Order;
  public action!: any;
  public checkAction!: any;
  public orderId!: any;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private tableService: TableService,
    private _router: Router,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.orderId = this.route.snapshot.paramMap?.get('orderId');
    this.formEditOrder = this.fb.group({
      firstName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      lastName: [
        '',
        [Validators.required, Validators.pattern(/^[a-z]{6,32}$/i)],
      ],
      phone: [
        '',
        [
          Validators.required,
          Validators.pattern(/((09|03|07|08|05)+([0-9]{8})\b)/g),
        ],
      ],
      address: ['', [Validators.required]],
      note: [''],
      email: [
        '',
        [
          Validators.pattern(/^[\w\.]+@([\w-]+\.)+[\w-]{2,4}/),
          Validators.required,
        ],
      ],
      status: [''],
      userId: [''],
    });
    this.loadDataToUpdate();
  }

  updateOrder(order: Order) {
    let pageNumber!: number;
    this.orderService.ValuePageNumber.subscribe((data: any) => {
      pageNumber = data;
    });
    this.orderService.updateOrder(this.orderId, order).subscribe(
      (res) => {
        this.toastr.success('sửa thành công order : ');
        this.tableService.notifyCountValue(res.orderId);
        setTimeout(() => {
          this._router.navigateByUrl('/admin/order/page/' + pageNumber);
        }, 1000);
      },
      (err) => {
        setTimeout(() => {
          this.toastr.error('sửa lỗi order : ' + err.orderId);
        }, 1000);
      }
    );
  }

  submitForm(form: FormGroup) {
    console.log(form.value);
    this.order = <Order>form.value;
    this.updateOrder(this.order);
  }

  loadDataToUpdate(): void {
    this.orderService.getById(this.orderId).subscribe(
      (respon) => {
        console.log(respon);

        this.formEditOrder.patchValue({
          firstName: respon.firstName,
          lastName: respon.lastName,
          phone: respon.phone,
          address: respon.address,
          note: respon.note,
          status: respon.status,
          email: respon.email,
          userId: respon.userId,
        });
      },
      (error) => {
        this.toastr.error('không tìm thấy bài liên hệ : ' + this.orderId);
      }
    );
  }
}
