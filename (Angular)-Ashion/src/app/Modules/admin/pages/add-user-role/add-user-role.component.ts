import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-user-role',
  templateUrl: './add-user-role.component.html',
  styleUrls: ['./add-user-role.component.css']
})
export class AddUserRoleComponent implements OnInit {

  public formAddRoleUser  !: FormGroup;
  constructor(private fb : FormBuilder) { }

  ngOnInit(): void {
    this.formAddRoleUser = this.fb.group({
      userId : ["",[Validators.required]],
      roleId : ["",[Validators.required]]
    })
  }
  submitForm(form : FormGroup) {
    console.log(form.value)
  }

}
