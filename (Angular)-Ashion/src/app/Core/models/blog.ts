export class Blog {

  blogId!: number;
  categoryId!: number;
  titleBlog!: string;
  createBy!: string;
  modifieBy!: string;
  createDate!: Date;
  modifieDate!: Date;
  content!: string;
  urlImg!: string;
  categoryName!: string;
  totalComment!: number;

  constructor(){}
}
