# Harmonyos Tutorial.《跟老卫学HarmonyOS开发》

![](images/logo.jpg)

*Harmonyos Tutorial*, is a book about how to develop Harmonyos applications.



《跟老卫学HarmonyOS开发》是一本 HarmonyOS 应用开发的开源学习教程，主要介绍如何从0开始开发 HarmonyOS 应用。本书包括最新版本 HarmonyOS 2.0 中的新特性。图文并茂，并通过大量实例带你走近 HarmonyOS 的世界！

本书业余时间所著，水平有限、时间紧张，难免疏漏，欢迎指正，

## Summary 目录

* [HarmonyOS初探01——下载安装DevEco Studio](https://developer.huawei.com/consumer/cn/forum/topic/0201427672244370691?fid=0101303901040230869)
* [HarmonyOS初探02——开发第一个HarmonyOS应用](https://developer.huawei.com/consumer/cn/forum/topic/0201427689906950692?fid=0101303901040230869)
* [HarmonyOS初探03——DevEco Studio创建应用问题ERROR Unable to tunnel through proxy. Proxy returns HTTP1.1 403](https://developer.huawei.com/consumer/cn/forum/topic/0201428884516950010?fid=0101303901040230869)
* [HarmonyOS初探04——使用DevEco Studio时设置Gradle仓库镜像](https://developer.huawei.com/consumer/cn/forum/topic/0201428885863380011?fid=0101303901040230869)
* [HarmonyOS初探05——内网环境下使用DevEco Studio模拟器](https://developer.huawei.com/consumer/cn/forum/topic/0201428886771250012?fid=0101303901040230869)
* [HarmonyOS初探06——使用DevEco Studio模拟器端口被占用无法启动](https://developer.huawei.com/consumer/cn/forum/topic/0204428887502690016?fid=0101303901040230869)
* [HarmonyOS初探07——使用DevEco Studio预览器](https://developer.huawei.com/consumer/cn/forum/topic/0201442998449480482?fid=0101303901040230869)
* [DevEco Studio 2.0.12.201使用报错“This device type does not match the module profile.”](https://developer.huawei.com/consumer/cn/forum/topic/0201435470610010153?fid=26)
* [HarmonyOS之Ability01——AbilitySlice间导航](https://developer.huawei.com/consumer/cn/forum/topic/0202443001580950502?fid=0101303901040230869)
* [HarmonyOS之线程01——ParallelTaskDispatcher派发任务](https://developer.huawei.com/consumer/cn/forum/topic/0204460939630370009?fid=0101303901040230869)
* [HarmonyOS之线程02——EventHandler处理线程间通信](https://developer.huawei.com/consumer/cn/forum/topic/0204461048552100015?fid=0101303901040230869)
* 未完待续...

## Samples 示例

* [Hello World](samples/HelloWorld)
* [多个AbilitySlice之间的路由与导航](samples/AbilitySliceNavigation)
* [Page与AbilitySlice生命周期的例子](samples/PageAndAbilitySliceLifeCycle)
* [Service Ability生命周期的例子](samples/ServiceAbilityLifeCycle)
* [DataAbilityHelper访问文件](samples/DataAbilityHelperAccessFile)
* [DataAbilityHelper访问数据库](samples/DataAbilityHelperAccessDatabase)
* [多个Page之间的路由与导航](samples/IntentOperationWithAction)
* [分布式任务调度启动远程FA](samples/DistributedSchedulingStartRemoteFA)
* [分布式任务调度启动和关闭远程PA](samples/DistributedSchedulingStartStopRemotePA)
* [公共事件服务发布事件](samples/CommonEventPublisher)（test）
* [公共事件服务订阅事件](samples/CommonEventSubscriber)（test）
* [高级通知服务](samples/Notification)（test）
* [剪切板数据的写入](samples/SystemPasteboardSetter)
* [剪切板数据的读取](samples/SystemPasteboardGetter)
* [XML创建布局](samples/DirectionalLayoutWithXml)
* [Java创建布局](samples/DirectionalLayoutWithJava)
* [常用显示类组件——Text](samples/Text)
* [常用显示类组件——Image](samples/Image)
* [常用显示类组件——ProgressBar](samples/ProgressBar)
* [常用交互类组件——Button](samples/Button)
* [常用交互类组件——TextField](samples/TextField)
* [常用交互类组件——Checkbox](samples/Checkbox)
* [常用交互类组件——RadioButton/RadioContaine](samples/RadioButtonRadioContaine)
* [常用交互类组件——Switch](samples/Switch)
* [常用交互类组件——ScrollView](samples/ScrollView)
* [常用交互类组件——Tab/TabList](samples/TabList)
* [常用交互类组件——Picker](samples/Picker)
* [常用交互类组件——ListContainer](samples/ListContainer)
* [常用交互类组件——RoundProgressBar](samples/RoundProgressBar)
* [常用布局——DirectionalLayout](samples/DirectionalLayout)
* [常用布局——DependentLayout](samples/DependentLayout)
* [常用布局——StackLayout](samples/StackLayout)
* [常用布局——TableLayout](samples/TableLayout)
* [创建JS FA应用](samples/JsFa)
* [点赞按钮](samples/GiveLike)
* [JS FA调用PA](samples/JsFaCallPa)
* [多模输入事件](samples/MultimodalEvent)
* [线程管理示例](samples/ParallelTaskDispatcher)
* [线程间通信示例](samples/EventHandler)
* [媒体编解码能力查询](samples/CodecDescriptionList)
* [视频编解码](samples/Codec)
* [视频播放](samples/Player)
* [视频录制](samples/Recorder)
* [图像编解码](samples/ImageCodec)
* [位图操作](samples/PixelMap)
* [图像属性解码](samples/ImageSourceExifUtils)（test）
* [相机设备创建、配置、帧捕获](samples/CameraKit)
* [音频播放](samples/AudioRenderer)（test）
* [音频采集](samples/AudioCapturer)（test）
* [短音播放](samples/SoundPlayer)（test）
* [AVSession媒体框架客户端、服务端](samples/AVSession)
* [媒体元数据获取](samples/AVMetadataHelper)
* [媒体存储数据](samples/AVStorage)
* [视频与图像缩略图获取](samples/AVThumbnailUtils)
* [生成二维码](samples/QuickResponseCode)
* [通用文字识别](samples/TextDetector)
* [NfcController](samples/NfcController)（test）
* [传统蓝牙本机管理](samples/BluetoothHost)
* [传统蓝牙远端设备操作](samples/BluetoothRemoteDevice)
* [BLE扫描和广播](samples/BleCentralManager)（test）
* [WLAN基础功能](samples/WifiDevice)
* [不信任热点配置](samples/WifiDeviceUntrustedConfig)
* [WLAN消息通知](samples/WifiEventSubscriber)
* [使用当前网络打开一个URL链接](samples/NetManagerHandleURL)
* [使用当前网络进行Socket数据传输](samples/NetManagerHandleSocket)
* [流量统计](samples/DataFlowStatistics)
* [获取当前蜂窝网络信号信息](samples/RadioInfoManager)
* [观察蜂窝网络状态变化](samples/RadioStateObserver)
* [传感器示例](samples/CategoryOrientationAgent)
* [Light示例](samples/LightAgent)
* [获取设备的位置](samples/Locator)
* [（逆）地理编码转化](samples/GeoConvert)
* [Todo](samples/Todo)
* 未完待续...


## Get start 如何开始阅读

选择下面入口之一：

* <https://github.com/waylau/harmonyos-tutorial> 的 [README.md](https://github.com/waylau/harmonyos-tutorial/blob/master/README.md)
* <https://gitee.com/waylau/harmonyos-tutorial> 的 [README.md](https://gitee.com/waylau/harmonyos-tutorial/blob/master/README.md)


## Code 源码

书中所有示例源码，移步至<https://github.com/waylau/harmonyos-tutorial>的 `samples` 目录下，代码遵循《[Java 编码规范](<http://waylau.com/java-code-conventions>)》

## Issue 意见、建议

如有勘误、意见或建议欢迎拍砖 <https://github.com/waylau/harmonyos-tutorial/issues>

## Contact 联系作者

* Blog: [waylau.com](http://waylau.com)
* Gmail: [waylau521(at)gmail.com](mailto:waylau521@gmail.com)
* Weibo: [waylau521](http://weibo.com/waylau521)
* Twitter: [waylau521](https://twitter.com/waylau521)
* Github : [waylau](https://github.com/waylau)


## Support Me 请老卫喝一杯

![开源捐赠](https://waylau.com/images/showmethemoney-sm.jpg)
