export class token {
  codeToken !: string;
  role !: Array<any>;
  constructor() {
    this.codeToken =localStorage.getItem('token')? JSON.parse(localStorage.getItem('token') || '{}'): '';
  }

}
