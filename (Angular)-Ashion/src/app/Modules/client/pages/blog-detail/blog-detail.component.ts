import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Blog } from 'src/app/Core/models/blog';
import { CategoryBlog } from 'src/app/Core/models/categoryBlog';
import { Instagram } from 'src/app/Core/models/instagram';
import { BlogService } from 'src/app/Core/services/blog.service';
import { CategoryBlogService } from 'src/app/Core/services/category-blog.service';
import { InstagramService } from 'src/app/Core/services/instagram.service';

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrls: ['./blog-detail.component.css'],
})
export class BlogDetailComponent implements OnInit {
  public listFeatureBlog: any = [
    {
      img: '../../../../../assets/img/blog/sidebar/fp-1.jpg',
      title: 'Amf Cannes Red Carpet Celebrities Kend...',
      date: 'Seb 17 2019',
    },
    {
      img: '../../../../../assets/img/blog/sidebar/fp-2.jpg',
      title: 'Amf Cannes Red Carpet Celebrities Kend...',
      date: 'Seb 17 2019',
    },
    {
      img: '../../../../../assets/img/blog/sidebar/fp-3.jpg',
      title: 'Amf Cannes Red Carpet Celebrities Kend...',
      date: 'Seb 17 2019',
    },
  ];

  public listTagBlog: any = [
    {
      name: 'Fashion',
    },
    {
      name: 'Street style',
    },
    {
      name: 'Diversity',
    },
    {
      name: 'Beauty',
    },
  ];
  public listInstagram: Instagram[] = [];
  public blog!: Blog;
  public listCategoryBlog: CategoryBlog[] = [];
  public totalNumberBlog: number = 0;
  constructor(
    private route: ActivatedRoute,
    private blogService: BlogService,
    private categoryBlogService: CategoryBlogService,
    private instagramService: InstagramService
  ) {}

  ngOnInit(): void {
    this.getCategoryBlog();
    this.getInstagram();
    this.route.params.subscribe((params) => {
      let blogId = params['slugBlog'].split('-')[0];
      if (blogId !== null) {
        this.getBlogById(blogId);
      }
    });
  }

  getBlogById(blogId: any) {
    this.blogService.getById(blogId).subscribe((res) => {
      this.blog = res;
    });
  }

  getCategoryBlog() {
    this.categoryBlogService.getAllCategoryBlog().subscribe((data) => {
      this.listCategoryBlog = data;
      this.listCategoryBlog.forEach((item) => {
        this.totalNumberBlog += item.totalItem;
      });
    });
  }

  getInstagram() {
    this.instagramService.getAll().subscribe((data) => {
      this.listInstagram = data;
    });
  }
}
