
let NextId = 0;
export class Menu {
  id: string;
  title: string;
  num: number;

  constructor(title: string, num: number) {
    this.id = `${NextId++}`;
    this.title = title;
    this.num = num;
  }
}

export class ImageItem {
  id: string;
  title: string;
  imageSrc: Resource;

  constructor(title: string, imageSrc: Resource) {
    this.id = `${NextId++}`;
    this.title = title;
    this.imageSrc = imageSrc;
  }
}