/******/ (() => { // webpackBootstrap
var __webpack_exports__ = {};
/*!****************************************************************************************************************!*\
  !*** D:\workspaceGithub\harmonyos-tutorial\samples\FatherDay\entry\src\main\ets\default\pages\index.ets?entry ***!
  \****************************************************************************************************************/
class Index extends View {
    constructor(compilerAssignedUniqueChildId, parent, params) {
        super(compilerAssignedUniqueChildId, parent);
        this.updateWithValueParams(params);
    }
    updateWithValueParams(params) {
    }
    aboutToBeDeleted() {
        SubscriberManager.Get().delete(this.id());
    }
    render() {
        Flex.create({ direction: FlexDirection.Column, alignItems: ItemAlign.Center, justifyContent: FlexAlign.Center });
        Flex.debugLine("pages/index.ets(5:5)");
        Flex.width('100%');
        Flex.height('100%');
        Text.create('爸爸在我心中就像旗帜；他教会我做人与处事的方向；在父亲节这个特别的日子里；我想对爸爸说长大以后我就要成为您');
        Text.debugLine("pages/index.ets(6:7)");
        Text.fontSize(50);
        Text.fontWeight(FontWeight.Bold);
        Text.pop();
        Flex.pop();
    }
}
loadDocument(new Index("1", undefined, {}));

/******/ })()
;