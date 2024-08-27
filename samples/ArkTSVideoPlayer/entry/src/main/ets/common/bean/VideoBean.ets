import image from '@ohos.multimedia.image';

@Observed export class VideoBean {
  name: string;
  src: string;
  pixelMap?: image.PixelMap;

  constructor(name: string, src: string, pixelMap?: image.PixelMap) {
    this.name = name;
    this.src = src;
    this.pixelMap = pixelMap;
  }
}