@Entry
@Component
struct Index {
  private swiperController: SwiperController = new SwiperController();
  private data: MyDataSource = new MyDataSource([]);

  aboutToAppear(): void {
    let list: number[] = [];
    for (let i = 0; i <= 20; i++) {
      list.push(i);
    }
    this.data = new MyDataSource(list);
  }

  build() {
    Column({ space: 5 }) {
      Swiper(this.swiperController) {
        LazyForEach(this.data, (item: string) => {
          Text(item.toString())
            .width('90%')
            .height(360)
            .backgroundColor(0xAFEEEE)
            .textAlign(TextAlign.Center)
            .fontSize(30)
        }, (item: string) => item)
      }
      .cachedCount(2)
      .index(1)
      .autoPlay(false)
      .interval(4000)
      .loop(false)
      .duration(1000)
      .itemSpace(5)
      .prevMargin(35)
      .nextMargin(35)
      .onChange((index: number) => {
        console.info(index.toString());
      })
      .onAnimationStart((index: number, targetIndex: number, extraInfo: SwiperAnimationEvent) => {
        console.info("index: " + index);
        console.info("targetIndex: " + targetIndex);
        console.info("current offset: " + extraInfo.currentOffset);
        console.info("target offset: " + extraInfo.targetOffset);
        console.info("velocity: " + extraInfo.velocity);
      })
      .onAnimationEnd((index: number, extraInfo: SwiperAnimationEvent) => {
        console.info("index: " + index);
        console.info("current offset: " + extraInfo.currentOffset);
      })

      Column({ space: 5 }) {
        Button('NO_ANIMATION 0')
          .onClick(() => {
            this.swiperController.changeIndex(0, SwiperAnimationMode.NO_ANIMATION);
          })
        Button('DEFAULT_ANIMATION 10')
          .onClick(() => {
            this.swiperController.changeIndex(10, SwiperAnimationMode.DEFAULT_ANIMATION);
          })
        Button('FAST_ANIMATION 20')
          .onClick(() => {
            this.swiperController.changeIndex(20, SwiperAnimationMode.FAST_ANIMATION);
          })
      }.margin(5)
    }.width('100%')
    .margin({ top: 5 })
  }
}

// 该类主要是用于提供Swiper组件的数据源。
class MyDataSource implements IDataSource {
  private list: number[] = [];

  constructor(list: number[]) {
    this.list = list;
  }

  totalCount(): number {
    return this.list.length;
  }

  getData(index: number): number {
    return this.list[index];
  }

  registerDataChangeListener(listener: DataChangeListener): void {
  }

  unregisterDataChangeListener() {
  }
}