import { Color } from './color';
import { ColorSizePrd } from './colorSizePrd';

export class Product {
  productId!: number;
  productName!: string;
  newProduct!: boolean;
  trendProduct!: boolean;
  featureProduct!: boolean;
  price!: number;
  discount!: number;
  thumbnail!: string;
  createDate!: Date;
  modifieDate!: Date;
  createBy!: string;
  modifieBy!: string;
  countBy!: number;
  vote!: number;
  brand!: string;
  status!: boolean;
  categoryPrdId!: number;
  categoryName!: string;
  descriptionShort!: string;
  descriptionDetail!: string;
  listColorSizeDto!: Array<ColorSizePrd>;
  urlImgs!: Array<string>;
  urlImgId!: number;
}
