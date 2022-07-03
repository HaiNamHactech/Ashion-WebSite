import { Component, OnInit } from '@angular/core';
import { Instagram } from 'src/app/Core/models/instagram';
import { InstagramService } from 'src/app/Core/services/instagram.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  public listInstagram : Instagram[] = [];

  constructor(private instagramService : InstagramService) { }

  ngOnInit(): void {
    this.getAllInstagram();
  }

  public getAllInstagram() {
    this.instagramService.getAll().subscribe(data => {
      this.listInstagram = data;
    })
  }
}
