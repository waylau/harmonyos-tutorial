import { wifiManager }  from '@kit.ConnectivityKit';

@Entry
@Component
struct Index {
  @State ssid: string = ''
  @State securityType: string = ''
  @State password: string = ''


  build() {
    Column() {

      Row() {
        Text('WiFi名称：')
        TextInput({ text: this.ssid })
          .width('80%')
          .onChange((value) => {
            this.ssid = value
          })
      }.margin(10)

      Row() {
        Text('密码：')
        TextInput({ text: this.password })
          .type(InputType.Password)
          .width('80%')
          .onChange((value) => {
            this.password = value
          })
      }.margin(10)

      // 操作按钮
      Row() {
        Button('连接该网络')
          .type(ButtonType.Capsule)
          .fontColor(Color.White)
          .margin(10)
          .onClick(() => {
            // 连接到WiFi
            this.connectToWifi()
          })

      }
      .width('100%')
      .height('10%')
      .justifyContent(FlexAlign.Center)
    }
    .height('100%')
    .width('100%')
    .justifyContent(FlexAlign.Center)
  }

  // 连接到WiFi
  private connectToWifi() {

    let config:wifiManager.WifiDeviceConfig = {
      ssid : this.ssid,
      preSharedKey : this.password,
      securityType : 3
    }

    console.info('config:' + JSON.stringify(config));

    // 添加候选网络配置
    wifiManager.addCandidateConfig(config).then(result => {
      // 连接至网络
      console.info('addCandidateConfig result:' + JSON.stringify(result));

      wifiManager.connectToCandidateConfig(result);
    }).catch((err: number) => {
      console.error('failed:' + JSON.stringify(err));
    });

  }
}