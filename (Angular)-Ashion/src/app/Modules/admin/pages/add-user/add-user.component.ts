import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  public formAddUser !: FormGroup;
  constructor(private fb : FormBuilder) { }

  ngOnInit(): void {
    this.formAddUser = this.fb.group({
      firstName : ["",[Validators.required,Validators.pattern(/^[a-z]{6,20}$/i)]],
      lastName : ["",[Validators.required,Validators.pattern(/^[a-z]{6,20}$/i)]],
      email : ["",[Validators.required,Validators.pattern(/^[\w\.]+@([\w-]+\.)+[\w-]{2,4}/)]],
      passWord : ["",[Validators.required,Validators.pattern(/^(?=.*[!@#$%^&*]+)[a-z0-9!@#$%^&*]{6,32}$/)]],
      avatar : [""],
    })
  }
  submitForm(form :FormGroup) {
    console.log(form.value)
  }



}
