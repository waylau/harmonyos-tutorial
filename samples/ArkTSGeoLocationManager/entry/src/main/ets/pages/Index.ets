import { geoLocationManager } from '@kit.LocationKit';

import { BusinessError } from '@kit.BasicServicesKit';

@Entry
@Component
struct Index {
  @State message: string = 'Hello World';

  build() {
    RelativeContainer() {
      Text(this.message)
        .id('HelloWorld')
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
        .alignRules({
          center: { anchor: '__container__', align: VerticalAlign.Center },
          middle: { anchor: '__container__', align: HorizontalAlign.Center }
        })
        .onClick(() => {
          this.getLocation();
        })
    }
    .height('100%')
    .width('100%')
  }

  getLocation() {
    let startTime = new Date().getTime();
    let requestInfo: geoLocationManager.CurrentLocationRequest = {
      'priority': geoLocationManager.LocationRequestPriority.FIRST_FIX,
      'scenario': geoLocationManager.LocationRequestScenario.UNSET, 'maxAccuracy': 100
    };

    let locationChange = (err: BusinessError, location: geoLocationManager.Location): void => {
      if (err) {
        console.error('locationChanger: err=' + JSON.stringify(err));
      }
      if (location) {
        console.log('locationChanger: location=' + JSON.stringify(location));
        this.message =
          '定位信息：' + JSON.stringify(location) + '\n 花费时间：' + (new Date().getTime() - startTime) / 1000
      }
    };
    try {
      geoLocationManager.getCurrentLocation(requestInfo, locationChange);
    } catch (err) {
      console.error("errCode:" + err.code + ",errMessage:" + err.message);
    }
    ;
  }
}