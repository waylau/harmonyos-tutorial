import {
  DocType,
  DocumentScanner,
  DocumentScannerConfig,
  SaveOption,
  FilterId,
  ShootingMode
} from "@kit.VisionKit"
import { hilog } from '@kit.PerformanceAnalysisKit';

const TAG: string = 'PageDocScan'


// 跳转页面入口函数
@Builder
export function PageDocScanBuilder(name: string, param: string) {
  PageDocScan();
}

@Entry
@Component
struct PageDocScan {
  @State type: string = '';
  pageInfos: NavPathStack = new NavPathStack();

  @State docImageUris: string[] = []
  private docScanConfig = new DocumentScannerConfig()

  aboutToAppear() {
    this.docScanConfig.supportType = [DocType.DOC, DocType.SHEET]
    this.docScanConfig.isGallerySupported = true
    this.docScanConfig.editTabs = []
    this.docScanConfig.maxShotCount = 3
    this.docScanConfig.defaultFilterId = FilterId.ORIGINAL
    this.docScanConfig.defaultShootingMode = ShootingMode.MANUAL
    this.docScanConfig.isShareable = true
    this.docScanConfig.originalUris = []
  }

  build() {
    NavDestination() {
      Stack({ alignContent: Alignment.Top }) {
        //展示文档扫描结果
        List() {
          ForEach(this.docImageUris, (uri: string) => {
            ListItem() {
              Image(uri)
                .objectFit(ImageFit.Contain)
                .width(100)
                .height(100)
            }
          })
        }
        .listDirection(Axis.Vertical)
        .alignListItem(ListItemAlign.Center)
        .margin({
          top: 50
        })
        .width('80%')
        .height('80%')

        //文档扫描
        DocumentScanner({
          scannerConfig: this.docScanConfig,
          onResult: (code: number, saveType: SaveOption, uris: string[]) => {
            hilog.info(0x0001, TAG, `result code: ${code}, save: ${saveType}`)
            if (code === -1) {
              this.pageInfos.pop()
            }
            uris.forEach(uriString => {
              hilog.info(0x0001, TAG, `uri: ${uriString}`)
            })
            this.docImageUris = uris
          }
        })
          .size({ width: '100%', height: '100%' })
      }
      .width('100%')
      .height('100%')
    }
    .title(this.type)
    .onReady((ctx: NavDestinationContext) => {
      // NavDestinationContext获取当前所在的导航控制器
      this.pageInfos = ctx.pathStack;

      // 获取参数
      this.type = ctx?.pathInfo?.param as string;
    })
  }
}
