
let NextId = 0;
export class ArsData {
  id: string;
  title: string;
  content: string;

  constructor(title: string, content: string) {
    this.id = `${NextId++}`;
    this.title = title;
    this.content = content;
  }
}
