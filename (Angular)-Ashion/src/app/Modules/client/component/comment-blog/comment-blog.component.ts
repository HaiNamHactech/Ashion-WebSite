import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-comment-blog',
  templateUrl: './comment-blog.component.html',
  styleUrls: ['./comment-blog.component.css']
})
export class CommentBlogComponent implements OnInit {

  @Input() customStyle !: string;
  constructor() { }

  ngOnInit(): void {
  }

}
