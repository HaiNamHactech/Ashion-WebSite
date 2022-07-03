import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Blog } from 'src/app/Core/models/blog';
import { Instagram } from 'src/app/Core/models/instagram';
import { BlogService } from 'src/app/Core/services/blog.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css'],
})
export class BlogComponent implements OnInit {
  // public listBlogs: any = [
  //   {
  //     img: '../../../../../assets/img/blog/blog-1.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-2.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-3.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-4.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-5.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-6.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-7.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-8.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-9.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  //   {
  //     img: '../../../../../assets/img/blog/blog-10.jpg',
  //     content: 'Amf Cannes Red Carpet Celebrities Kendall Jenner, Pamela...',
  //     title: 'Ema Timahe',
  //     date: 'Seb 17, 2019',
  //   },
  // ];
  public listBlogs: Blog[] = [];
  public listInstagram: Instagram[] = [];

  public curentPage!: any;
  public page!: number;
  public pageSize!: number;
  public lastPage!: boolean;
  public firstPage!: boolean;
  public totalPage!: number;

  public filter: any = {
    page: 0,
    pageSize: 10,
    asc: true,
  };
  constructor(
    private blogService: BlogService,
    private instagramService: InstagramService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      console.log(params['page']);
      this.filter.page = params['page'] - 1;
      this.getBlogPagination();
    });
    this.getInstagram();
  }

  getInstagram() {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }

  hanldeSelectPage(page: any): void {
    this.router.navigate(['/blog'], {
      queryParams: { page: page },
    });
  }

  getBlogPagination() {
    this.blogService.getBlogPagination(this.filter).subscribe((data) => {
      console.log(data);
      this.listBlogs = data.content;
      this.page = data.pageable.pageNumber + 1;
      this.pageSize = data.pageable.pageSize;
      this.lastPage = data.last;
      this.firstPage = data.first;
      this.totalPage = data.totalPages;
    });
  }
}
