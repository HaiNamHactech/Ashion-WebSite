import { Component, OnInit } from '@angular/core';
import { Instagram } from 'src/app/Core/models/instagram';
import { InstagramService } from 'src/app/Core/services/instagram.service';
import { SearchService } from 'src/app/Core/services/search.service';

@Component({
  selector: 'app-page-search',
  templateUrl: './page-search.component.html',
  styleUrls: ['./page-search.component.css'],
})
export class PageSearchComponent implements OnInit {
  public listSearch: Array<any> = [];
  public listInstagram : Instagram[] = [];

  constructor(
    private searchService: SearchService,
    private instagramService: InstagramService
  ) {}

  ngOnInit(): void {
    this.getInstagram();
    this.searchService.ValuePageNumber.subscribe((res: any) => {
      this.listSearch = res;
    });
  }
  getInstagram() {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }
}
