import { Component, Input, OnInit } from '@angular/core';
import { RouteConfigLoadEnd, Router } from '@angular/router';
import { Blog } from 'src/app/Core/models/blog';

@Component({
  selector: 'app-blog-item',
  templateUrl: './blog-item.component.html',
  styleUrls: ['./blog-item.component.css'],
})
export class BlogItemComponent implements OnInit {
  @Input() img!: string;
  @Input() content!: string;
  @Input() title!: string;
  @Input() date!: Date;
  @Input() blogId!: number;
  @Input() blog!: any;
  @Input() customStyle!: any;
  constructor(private route: Router) {}

  ngOnInit(): void {}
  navigateDetail(blog: any) {
    console.log(blog);

    this.route.navigateByUrl('/blogs/' + blog.blogId + '-' + blog.titleBlog);
  }
}
